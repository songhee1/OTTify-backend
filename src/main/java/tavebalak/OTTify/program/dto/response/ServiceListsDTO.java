package tavebalak.OTTify.program.dto.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@ApiModel(value = "ServiceListsDTO(프로그램 정보)", description = "프로그램 id, 프로그램 제목, 포스터 url, 평균 펼점 내용을 가진 Domain Class")
public class ServiceListsDTO {

    @ApiModelProperty(value = "프로그램 id")
    private Long programId;
    @ApiModelProperty(value = "프로그램 제목")
    private String title;
    @ApiModelProperty(value = "포스터 url")
    private String posterPath;
    @ApiModelProperty(value = "평균 펼점")
    private double averageRating;
    @ApiModelProperty(value = "개봉년도")
    private String createdYear;

    @Builder
    public ServiceListsDTO(Long programId, String title, String posterPath, double averageRating,
        String createdYear) {
        this.programId = programId;
        this.title = title;
        this.posterPath = posterPath;
        this.averageRating = averageRating;
        this.createdYear = createdYear;
    }
}
