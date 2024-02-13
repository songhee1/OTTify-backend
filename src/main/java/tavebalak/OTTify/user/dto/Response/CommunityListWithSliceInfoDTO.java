package tavebalak.OTTify.user.dto.Response;

import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import tavebalak.OTTify.community.dto.response.MyDiscussionDto;

@Getter
public class CommunityListWithSliceInfoDTO {

    private List<MyDiscussionDto> discussionList = new ArrayList<>();
    private boolean isLastPage;

    public CommunityListWithSliceInfoDTO(List<MyDiscussionDto> discussionList, boolean isLastPage) {
        this.discussionList = discussionList;
        this.isLastPage = isLastPage;
    }
}
