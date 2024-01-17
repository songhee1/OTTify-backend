package tavebalak.OTTify.review.dto.reviewrequest;

import io.swagger.annotations.ApiModelProperty;
import java.util.List;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import tavebalak.OTTify.error.reviewCheck.ReviewRatingCheck;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ReviewUpdateDto {

    @NotBlank(message = "contents 내용을 입력해 주세요")
    @Length(min = 8, max = 255, message = "최소 8자 이상 255자 이하로 작성해 주세요. ")
    @ApiModelProperty(value = "리뷰 내용을 입력해 주세요", example = "핵꿀잼이였어영 완전완전")
    private String contents;

    @ReviewRatingCheck
    @ApiModelProperty(value = "리뷰 평점 입력", example = "3.5")
    private double rating;

    @Size(min = 0, max = 3, message = "리뷰 태그 갯수는 0~3이여야 합니다")
    private List<Long> reviewTagIdDtoList;
}
