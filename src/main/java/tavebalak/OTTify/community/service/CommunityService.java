package tavebalak.OTTify.community.service;

import org.springframework.data.domain.Pageable;
import tavebalak.OTTify.community.dto.CommunityAriclesDTO;
import tavebalak.OTTify.community.dto.CommunitySubjectCreateDTO;
import tavebalak.OTTify.community.dto.CommunitySubjectEditDTO;
import tavebalak.OTTify.community.dto.CommunitySubjectsDTO;
import tavebalak.OTTify.exception.NotFoundException;

import java.util.List;

public interface CommunityService {
    public void saveSubject(CommunitySubjectCreateDTO c);
    public void modifySubject(CommunitySubjectEditDTO c) throws NotFoundException;
    public void deleteSubject(Long subjectId) throws NotFoundException;
    public CommunitySubjectsDTO findAllSubjects(Pageable pageable);
    public CommunityAriclesDTO getArticles(Long subjectId) throws NotFoundException;
}
