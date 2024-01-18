package tavebalak.OTTify.user.dto.Request;

import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;
import org.springframework.web.multipart.MultipartFile;

@Getter
@AllArgsConstructor
public class UserProfileUpdateDTO {

    @ApiModelProperty(value = "새로 업데이트 하고자 하는 닉네임", required = true)
    @NotNull(message = "닉네임을 입력해주세요.")
    @Pattern(regexp = "^[ㄱ-ㅎ가-힣a-z0-9-_]{2,10}$", message = "닉네임은 특수문자를 제외한 2 ~ 10자리여야 합니다.")
    @Length(min = 2, max = 10, message = "닉네임은 2자 이상, 10자 이하로 입력해주세요")
    private String nickName;

    @ApiModelProperty(value = "새로 업로드 하고자 하는 프로필 사진")
    private MultipartFile profilePhoto;
}
