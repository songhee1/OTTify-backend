package tavebalak.OTTify.review.dto.reviewrequest;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ReviewTagIdDto {

    @ApiModelProperty(value = "리뷰 태그 ID값을 입력해주세요", example = "3")
    private Long id;

}
