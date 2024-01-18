package tavebalak.OTTify.user.dto.Request;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;
import org.springframework.web.multipart.MultipartFile;

@Getter
@AllArgsConstructor
public class UserProfileUpdateDTO {

    @NotNull(message = "닉네임을 입력해주세요.")
    @Pattern(regexp = "^[ㄱ-ㅎ가-힣a-z0-9-_]{2,10}$", message = "닉네임은 특수문자를 제외한 2 ~ 10자리여야 합니다.")
    @Length(min = 2, max = 10, message = "닉네임은 2자 이상, 10자 이하로 입력해주세요")
    private String nickName;

    @NotNull(message = "사진을 업로드해주세요.")
    private MultipartFile profilePhoto;
}
