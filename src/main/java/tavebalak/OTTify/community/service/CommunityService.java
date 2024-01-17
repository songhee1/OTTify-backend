package tavebalak.OTTify.community.service;

import org.springframework.data.domain.Pageable;
import tavebalak.OTTify.community.dto.request.CommunitySubjectCreateDTO;
import tavebalak.OTTify.community.dto.request.CommunitySubjectEditDTO;
import tavebalak.OTTify.community.dto.response.CommunityAriclesDTO;
import tavebalak.OTTify.community.dto.response.CommunitySubjectDTO;
import tavebalak.OTTify.community.dto.response.CommunitySubjectsDTO;
import tavebalak.OTTify.community.entity.Community;

public interface CommunityService {

    Community saveSubject(CommunitySubjectCreateDTO c);

    void modifySubject(CommunitySubjectEditDTO c);

    void deleteSubject(Long subjectId);

    CommunitySubjectsDTO findAllSubjects(Pageable pageable);

    CommunityAriclesDTO getArticleOfASubject(Long subjectId);

    CommunitySubjectDTO getSubject(Long subjectId);

    CommunitySubjectsDTO findSingleProgramSubjects(Pageable pageable, Long programId);

    boolean likeSubject(Long subjectId);

    boolean likeComment(Long subjectId, Long commentId);
}
