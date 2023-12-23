package tavebalak.OTTify.oauth;

import lombok.*;

@NoArgsConstructor
@Getter
@ToString
@AllArgsConstructor
@Builder
public class SecurityUserDto {
    private String email;
    private String nickname;
    private String profilePhoto;
    private String role;
    private Long userId;

}