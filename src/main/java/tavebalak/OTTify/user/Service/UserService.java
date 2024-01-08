package tavebalak.OTTify.user.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tavebalak.OTTify.community.dto.MyDiscussionDto;
import tavebalak.OTTify.community.entity.Community;
import tavebalak.OTTify.community.repository.CommunityRepository;
import tavebalak.OTTify.community.repository.ReplyRepository;
import tavebalak.OTTify.review.dto.MyReviewDto;
import tavebalak.OTTify.review.entity.Review;
import tavebalak.OTTify.review.entity.ReviewTag;
import tavebalak.OTTify.review.repository.ReviewRepository;
import tavebalak.OTTify.review.repository.ReviewReviewTagRepository;
import tavebalak.OTTify.user.repository.LikedCommunityRepository;
import tavebalak.OTTify.user.repository.LikedReplyRepository;
import tavebalak.OTTify.user.repository.LikedReviewRepository;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final ReviewRepository reviewRepository;
    private final ReviewReviewTagRepository reviewReviewTagRepository;
    private final LikedReviewRepository likedReviewRepository;
    private final CommunityRepository communityRepository;
    private final LikedCommunityRepository likedCommunityRepository;
    private final ReplyRepository replyRepository;
    private final LikedReplyRepository likedReplyRepository;

    public List<MyReviewDto> getMyReview(Long userId) {
        List<Review> reviewList = reviewRepository.findByUserIdOrderByCreatedAt(userId);

        List<MyReviewDto> reviewDtoList = new ArrayList<>();
        reviewList.stream()
                        .forEach(r -> {
                            // createdDate format 변경
                            String createdDateString = r.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy년 MM월 dd일"));

                            // 리뷰에 달린 reviewTags 가져오기
                            List<ReviewTag> reviewTags = reviewReviewTagRepository.findReviewTagNameByReviewId(r.getId());

                            reviewDtoList.add(
                                    MyReviewDto.builder()
                                            .userProfilePhoto(r.getUser().getProfilePhoto())
                                            .userNickName(r.getUser().getNickName())
                                            .createdDate(createdDateString)
                                            .programTitle(r.getProgram().getTitle())
                                            .rating(r.getRating())
                                            .reviewTags(reviewTags)
                                            .content(r.getContent())
                                            .likeCnt(r.getLikeCnt())
                                            .build()
                            );
                        });

        return reviewDtoList;
    }

    public List<MyReviewDto> getLikedReview(Long userId) {
        List<Review> reviewList = likedReviewRepository.findLikedReviewByUserId(userId);

        List<MyReviewDto> reviewDtoList = new ArrayList<>();
        reviewList.stream()
                .forEach(r -> {
                    // createdDate format 변경
                    String createdDateString = r.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy년 MM월 dd일"));

                    // 리뷰에 달린 reviewTags 가져오기
                    List<ReviewTag> reviewTags = reviewReviewTagRepository.findReviewTagNameByReviewId(r.getId());

                    reviewDtoList.add(
                            MyReviewDto.builder()
                                    .userProfilePhoto(r.getUser().getProfilePhoto())
                                    .userNickName(r.getUser().getNickName())
                                    .createdDate(createdDateString)
                                    .programTitle(r.getProgram().getTitle())
                                    .rating(r.getRating())
                                    .reviewTags(reviewTags)
                                    .content(r.getContent())
                                    .likeCnt(r.getLikeCnt())
                                    .build()
                    );
                });

        return reviewDtoList;
    }

    public List<MyDiscussionDto> getHostedDiscussion(Long userId) {
        List<Community> discussionList = communityRepository.findByUserIdOrderByCreatedAt(userId);

        List<MyDiscussionDto> discussionDtoList = new ArrayList<>();
        discussionList.stream()
                .forEach(d -> {
                    // createdDate format 변경
                    String createdDateString = d.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy년 MM월 dd일"));

                    // likeCnt 계산
                    Long likeCnt = likedCommunityRepository.countByCommunityId(d.getId());

                    // replyCnt 계산
                    Long replyCnt = likedReplyRepository.countByCommunityId(d.getId());

                    discussionDtoList.add(
                            MyDiscussionDto.builder()
                                    .discussionId(d.getId())
                                    .createdDate(createdDateString)
                                    .title(d.getTitle())
                                    .programTitle(d.getProgram().getTitle())
                                    .content(d.getContent())
                                    .img(d.getImg())
                                    .likeCnt(likeCnt)
                                    .replyCnt(replyCnt)
                                    .build()
                    );
                });

        return discussionDtoList;
    }

    public List<MyDiscussionDto> getParticipatedDiscussion(Long userId) {
        List<Community> discussionList = replyRepository.findAllCommunityByUserId(userId);

        List<MyDiscussionDto> discussionDtoList = new ArrayList<>();
        discussionList.stream()
                .forEach(d -> {
                    // createdDate format 변경
                    String createdDateString = d.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy년 MM월 dd일"));

                    // likeCnt 계산
                    Long likeCnt = likedCommunityRepository.countByCommunityId(d.getId());

                    // replyCnt 계산
                    Long replyCnt = likedReplyRepository.countByCommunityId(d.getId());

                    discussionDtoList.add(
                            MyDiscussionDto.builder()
                                    .discussionId(d.getId())
                                    .createdDate(createdDateString)
                                    .title(d.getTitle())
                                    .programTitle(d.getProgram().getTitle())
                                    .content(d.getContent())
                                    .img(d.getImg())
                                    .likeCnt(likeCnt)
                                    .replyCnt(replyCnt)
                                    .build()
                    );
                });

        return discussionDtoList;
    }
}
