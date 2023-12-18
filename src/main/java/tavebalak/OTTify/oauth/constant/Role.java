package tavebalak.OTTify.oauth.constant;


public enum Role {
    USER, GUEST;

    public String getKey() {
        return name();
    }
}
