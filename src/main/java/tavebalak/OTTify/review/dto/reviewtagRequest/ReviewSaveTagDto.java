package tavebalak.OTTify.review.dto.reviewtagRequest;

import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ReviewSaveTagDto {

    @NotBlank(message = "태그 이름을 입력해주세요.")
    @ApiModelProperty(value = "태그 이름", example = "꿀잼이였어영!")
    private String name;
}