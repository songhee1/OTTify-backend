package tavebalak.OTTify.community.service;

import org.springframework.data.domain.Pageable;
import tavebalak.OTTify.community.dto.*;
import tavebalak.OTTify.community.entity.Community;
import tavebalak.OTTify.exception.NotFoundException;

public interface CommunityService {
    public Community saveSubject(CommunitySubjectCreateDTO c);
    public void modifySubject(CommunitySubjectEditDTO c) throws NotFoundException;
    public void deleteSubject(Long subjectId) throws NotFoundException;
    public CommunitySubjectsDTO findAllSubjects(Pageable pageable);
    public CommunityAriclesDTO getArticleOfASubject(Long subjectId) throws NotFoundException;
    public CommunitySubjectDTO getSubject(Long subjectId);
    public CommunitySubjectsDTO findSingleProgramSubjects(Pageable pageable, Long programId);
    boolean likeSubject(Long subjectId);
    boolean likeComment(Long subjectId, Long commentId);
}
