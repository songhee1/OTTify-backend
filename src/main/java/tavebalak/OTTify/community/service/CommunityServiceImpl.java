package tavebalak.OTTify.community.service;


import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import tavebalak.OTTify.common.s3.AWSS3Service;
import tavebalak.OTTify.community.dto.request.CommunitySubjectCreateDTO;
import tavebalak.OTTify.community.dto.request.CommunitySubjectEditDTO;
import tavebalak.OTTify.community.dto.response.CommentListsDTO;
import tavebalak.OTTify.community.dto.response.CommunityAriclesDTO;
import tavebalak.OTTify.community.dto.response.CommunitySubjectDTO;
import tavebalak.OTTify.community.dto.response.CommunitySubjectEditorDTO;
import tavebalak.OTTify.community.dto.response.CommunitySubjectImageEditorDTO;
import tavebalak.OTTify.community.dto.response.CommunitySubjectsDTO;
import tavebalak.OTTify.community.dto.response.CommunitySubjectsListDTO;
import tavebalak.OTTify.community.dto.response.ReplyListsDTO;
import tavebalak.OTTify.community.entity.Community;
import tavebalak.OTTify.community.entity.Reply;
import tavebalak.OTTify.community.repository.CommunityRepository;
import tavebalak.OTTify.community.repository.ReplyRepository;
import tavebalak.OTTify.error.ErrorCode;
import tavebalak.OTTify.error.exception.BadRequestException;
import tavebalak.OTTify.error.exception.ForbiddenException;
import tavebalak.OTTify.error.exception.NotFoundException;
import tavebalak.OTTify.error.exception.UnauthorizedException;
import tavebalak.OTTify.oauth.jwt.SecurityUtil;
import tavebalak.OTTify.program.entity.Program;
import tavebalak.OTTify.program.repository.ProgramRepository;
import tavebalak.OTTify.user.entity.LikedCommunity;
import tavebalak.OTTify.user.entity.LikedReply;
import tavebalak.OTTify.user.entity.User;
import tavebalak.OTTify.user.repository.LikedCommunityRepository;
import tavebalak.OTTify.user.repository.LikedReplyRepository;
import tavebalak.OTTify.user.repository.UserRepository;

@Service
@Transactional
@RequiredArgsConstructor
public class CommunityServiceImpl implements CommunityService {

    private final CommunityRepository communityRepository;
    private final ProgramRepository programRepository;
    private final ReplyRepository replyRepository;
    private final UserRepository userRepository;
    private final LikedCommunityRepository likedCommunityRepository;
    private final LikedReplyRepository likedReplyRepository;
    private final AWSS3Service awss3Service;
    private static final String AWS_S3_DISCUSSION_DIR_NAME = "discussion-images";

    @Override
    public Community saveSubject(CommunitySubjectCreateDTO c, MultipartFile image) {

        String imageUrl = null;
        if (image != null) {
            imageUrl = awss3Service.upload(image, AWS_S3_DISCUSSION_DIR_NAME);
        }

        Program program = isPresent(c);
        Community community = Community.builder()
            .title(c.getSubjectName())
            .content(c.getContent())
            .user(getUser())
            .program(program)
            .imageUrl(imageUrl)
            .build();

        return communityRepository.save(community);

    }

    public Community save(CommunitySubjectCreateDTO c) {
        Program program = isPresent(c);

        Community community = Community.builder()
            .title(c.getSubjectName())
            .content(c.getContent())
            .program(program)
            .build();

        return communityRepository.save(community);
    }

    private Program isPresent(CommunitySubjectCreateDTO c) {
        Optional<Program> optionalProgram = programRepository.findById(c.getProgramId());
        return optionalProgram.orElseThrow(
            () -> new NotFoundException(ErrorCode.SAVED_PROGRAM_NOT_FOUND));
    }


    @Override
    public void modifySubject(CommunitySubjectEditDTO c, MultipartFile image) {
        Community community = communityRepository.findById(c.getSubjectId())
            .orElseThrow(() -> new NotFoundException(ErrorCode.COMMUNITY_NOT_FOUND));

        if (!Objects.equals(community.getUser().getId(), getUser().getId())) {
            throw new BadRequestException(ErrorCode.CAN_NOT_UPDATE_OTHER_SUBJECT_REQUEST);
        }

        CommunitySubjectImageEditorDTO communitySubjectEditorDTOBuilder = community.toImageEdior();

        String communityImgUrl = null;
        String imgPath = c.getImageUrl();

        if (!"".equals(imgPath) && imgPath != null) {
            if (awss3Service.isExist(imgPath)) {
                communityImgUrl = imgPath;
            }
        } else {
            if (image != null && !image.isEmpty()) {
                deleteCommunityS3Image(community);
                communityImgUrl = awss3Service.upload(image, AWS_S3_DISCUSSION_DIR_NAME);
            } else {
                deleteCommunityS3Image(community);
            }
        }

        CommunitySubjectImageEditorDTO communitySubjectImageEditorDTO = communitySubjectEditorDTOBuilder.changeTitleContentImage(
            c.getSubjectName(), c.getContent(), communityImgUrl);
        community.editImage(communitySubjectImageEditorDTO);
    }

    public void deleteCommunityS3Image(Community community) {
        if (community.getImageUrl() != null) {
            awss3Service.delete(community.getImageUrl());
        }
    }

    public Community modify(CommunitySubjectEditDTO c, User user) {
        Community community = communityRepository.findById(c.getSubjectId())
            .orElseThrow(() -> new NotFoundException(ErrorCode.COMMUNITY_NOT_FOUND));

        if (!Objects.equals(community.getUser().getId(), user.getId())) {
            throw new BadRequestException(ErrorCode.CAN_NOT_UPDATE_OTHER_SUBJECT_REQUEST);
        }

        CommunitySubjectEditorDTO communitySubjectEditorDTOBuilder = community.toEditor();
        CommunitySubjectEditorDTO communitySubjectEditorDTO = communitySubjectEditorDTOBuilder.changeTitleContentProgram(
            c.getSubjectName(), c.getContent());

        community.edit(communitySubjectEditorDTO);

        return community;
    }

    @Override
    public void deleteSubject(Long subjectId) {
        Community community = communityRepository.findById(subjectId)
            .orElseThrow(() -> new NotFoundException(ErrorCode.COMMUNITY_NOT_FOUND));
        if (!Objects.equals(community.getUser().getId(), getUser().getId())) {
            throw new ForbiddenException(ErrorCode.CAN_NOT_DELETE_OTHER_SUBJECT_REQUEST);
        }
        communityRepository.delete(community);
        if (community.getImageUrl() != null) {
            awss3Service.delete(community.getImageUrl());
        }
    }

    public void delete(Long subjectId, User user) {
        Community community = communityRepository.findById(subjectId)
            .orElseThrow(() -> new NotFoundException(ErrorCode.COMMUNITY_NOT_FOUND));
        if (!Objects.equals(community.getUser().getId(), user.getId())) {
            throw new ForbiddenException(ErrorCode.CAN_NOT_DELETE_OTHER_SUBJECT_REQUEST);
        }
        communityRepository.delete(community);
    }

    @Override
    public CommunitySubjectsListDTO findAllSubjects(Pageable pageable) {
        Slice<Community> communities = communityRepository.findCommunitiesBy(pageable);
        List<CommunitySubjectsDTO> listDTO = communities.stream().map(
            community -> CommunitySubjectsDTO
                .builder()
                .createdAt(community.getCreatedAt())
                .updatedAt(community.getUpdatedAt())
                .title(community.getTitle())
                .nickName(community.getUser().getNickName())
                .programId(community.getProgram().getId())
                .subjectId(community.getId())
                .likeCount(community.getLikeCount())
                .imageUrl(community.getImageUrl())
                .programName(community.getProgram().getTitle())
                .content(community.getContent())
                .commentCount(community.getCommentCount())
                .build()
        ).collect(Collectors.toList());
        return CommunitySubjectsListDTO.builder().subjectAmount(communities.getNumberOfElements())
            .list(listDTO).hasNext(communities.hasNext()).build();
    }

    @Override
    public CommunitySubjectsListDTO findSingleProgramSubjects(Pageable pageable, Long programId) {
        Slice<Community> communities = communityRepository.findCommunitiesByProgramId(pageable,
            programId);
        List<CommunitySubjectsDTO> list = communities.stream().map(
            community -> CommunitySubjectsDTO
                .builder()
                .createdAt(community.getCreatedAt())
                .updatedAt(community.getUpdatedAt())
                .title(community.getTitle())
                .nickName(community.getUser().getNickName())
                .subjectId(community.getId())
                .likeCount(community.getLikeCount())
                .programId(programId)
                .imageUrl(community.getImageUrl())
                .programName(community.getProgram().getTitle())
                .content(community.getContent())
                .commentCount(community.getCommentCount())
                .build()
        ).collect(Collectors.toList());

        return CommunitySubjectsListDTO.builder().subjectAmount(communities.getNumberOfElements())
            .list(list).hasNext(communities.hasNext()).build();
    }

    @Override
    public boolean likeSubject(Long subjectId) {
        AtomicBoolean flag = new AtomicBoolean(false);
        User savedUser = getUser();
        Community findCommunity = communityRepository.findById(subjectId).orElseThrow(() ->
            new NotFoundException(ErrorCode.COMMUNITY_NOT_FOUND));

        likedCommunityRepository.findByCommunityIdAndUserId(findCommunity.getId(),
            savedUser.getId()).ifPresentOrElse(
            likedCommunityRepository::delete,
            () -> {
                likedCommunityRepository.save(
                    LikedCommunity.builder()
                        .user(savedUser)
                        .community(findCommunity)
                        .build()
                );
                flag.set(true);
            }
        );

        if (flag.get()) {
            findCommunity.increaseLikeCount();
        } else {
            findCommunity.decreaseLikeCount();
        }

        return flag.get();
    }

    public boolean likeSub(User user, Community community, Long id) {
        AtomicBoolean flag = new AtomicBoolean(false);

        likedCommunityRepository.findByCommunityIdAndUserId(community.getId(), id).ifPresentOrElse(
            likedCommunityRepository::delete,
            () -> {
                likedCommunityRepository.save(
                    LikedCommunity.builder()
                        .user(user)
                        .community(community)
                        .build()
                );
                flag.set(true);
            }
        );

        return flag.get();
    }


    @Override
    public boolean likeComment(Long subjectId, Long commentId) {
        //only-write object
        AtomicBoolean flag = new AtomicBoolean(false);
        User savedUser = getUser();
        Community community = communityRepository.findById(subjectId)
            .orElseThrow(() -> new NotFoundException(ErrorCode.COMMUNITY_NOT_FOUND));
        Reply findReply = replyRepository.findById(commentId)
            .orElseThrow(() -> new NotFoundException(ErrorCode.REPLY_NOT_FOUND));

        likedReplyRepository.findByUserIdAndReplyIdAndCommunityId(savedUser.getId(),
            findReply.getId(), community.getId()).ifPresentOrElse(
            likedReplyRepository::delete,
            () -> {
                likedReplyRepository.save(
                    LikedReply.builder()
                        .user(savedUser)
                        .community(community)
                        .reply(findReply)
                        .build()
                );
                flag.set(true);
            }
        );

        if (flag.get()) {
            findReply.increaseLikeCount();
        } else {
            findReply.decreaseLikeCount();
        }

        return flag.get();
    }

    @Override
    public CommunityAriclesDTO getArticleOfASubject(Long subjectId) {
        Community community = communityRepository.findCommunityBySubjectId(subjectId).orElseThrow(
            () -> new NotFoundException(ErrorCode.COMMUNITY_NOT_FOUND)
        );

        List<Reply> replyList = replyRepository.findByCommunityIdAndParentId(community.getId(),
            null);
        List<CommentListsDTO> commentListsDTOList = new ArrayList<>();
        for (Reply comment : replyList) {
            List<Reply> byCommunityIdAndParentId = replyRepository.findByCommunityIdAndParentId(
                community.getId(), comment.getId());
            List<ReplyListsDTO> collect = byCommunityIdAndParentId.stream().map(listone ->
                ReplyListsDTO.builder()
                    .recommentId(listone.getId())
                    .content(listone.getContent())
                    .nickName(listone.getUser().getNickName())
                    .userId(listone.getUser().getId())
                    .createdAt(listone.getCreatedAt())
                    .likeCount(listone.getLikeCount())
                    .build()
            ).collect(Collectors.toList());

            CommentListsDTO build = CommentListsDTO.builder()
                .content(comment.getContent())
                .nickName(comment.getUser().getNickName())
                .profileUrl(comment.getUser().getProfilePhoto())
                .createdAt(comment.getCreatedAt())
                .userId(comment.getUser().getId())
                .replyListsDTOList(collect)
                .likeCount(comment.getLikeCount())
                .commentId(comment.getId())
                .build();
            commentListsDTOList.add(build);
        }

        return CommunityAriclesDTO.builder()
            .title(community.getTitle())
            .writer(community.getUser().getNickName())
            .content(community.getContent())
            .createdAt(community.getCreatedAt())
            .updatedAt(community.getUpdatedAt())
            .commentAmount(community.getCommentCount())
            .commentListsDTOList(commentListsDTOList)
            .userId(getUser().getId())
            .likeCount(community.getLikeCount())
            .subjectId(community.getId())
            .programTitle(community.getProgram().getTitle())
            .imageUrl(community.getImageUrl())
            .build();
    }

    @Override
    public CommunitySubjectDTO getSubject(Long subjectId) {
        Community community = communityRepository.findById(subjectId)
            .orElseThrow(() -> new NotFoundException(ErrorCode.COMMUNITY_NOT_FOUND));

        return CommunitySubjectDTO.builder()
            .subjectId(subjectId)
            .title(community.getTitle())
            .content(community.getContent())
            .programId(community.getProgram().getId())
            .programTitle(community.getProgram().getTitle())
            .posterPath(community.getProgram().getPosterPath())
            .build();
    }

    public User getUser() {
        return userRepository.findByEmail(
                SecurityUtil.getCurrentEmail().get())
            .orElseThrow(() -> new UnauthorizedException(ErrorCode.UNAUTHORIZED)
            );
    }

}
