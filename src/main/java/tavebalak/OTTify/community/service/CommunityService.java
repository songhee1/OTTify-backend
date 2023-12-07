package tavebalak.OTTify.community.service;

import tavebalak.OTTify.community.dto.CommunitySubjectCreateDTO;
import tavebalak.OTTify.community.dto.CommunitySubjectEditDTO;
import tavebalak.OTTify.exception.NotFoundException;

public interface CommunityService {
    public void saveSubject(CommunitySubjectCreateDTO c);
    public void modifySubject(CommunitySubjectEditDTO c) throws NotFoundException;
}
