package tavebalak.OTTify.review.dto.reviewrequest;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ReviewUpdateDto {
    @NotBlank(message = "contents 내용을 입력해 주세요")
    @Length(min = 8, max = 255, message = "최소 8자 이상 255자 이하로 작성해 주세요. ")
    private String contents;

    @PositiveOrZero(message = "값은 0 이상이어야 합니다.")
    private double rating;

    private List<ReviewTagIdDto> reviewTagIdDtoList;
}
