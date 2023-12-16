package tavebalak.OTTify.oauth.userinfo;

import java.util.Map;

public class NaverOAuth2UserInfo extends OAuth2UserInfo{
    public NaverOAuth2UserInfo(Map<String, Object> attributes) {
        super(attributes);
    }

    @Override
    public String getId() {
        return String.valueOf(getResponse().get("sub"));
    }

    @Override
    public String getEmail() {
        return String.valueOf(getResponse().get("email"));
    }

    @Override
    public String getNickname() {
        return String.valueOf(getResponse().get("nickname"));
    }

    @Override
    public String getImageUrl() {
        return String.valueOf(getResponse().get("profile_image"));
    }

    private Map<String, Object> getResponse() {
        return (Map<String, Object>) attributes.get("response");
    }
}
