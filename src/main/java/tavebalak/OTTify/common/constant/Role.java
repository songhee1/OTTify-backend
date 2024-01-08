package tavebalak.OTTify.common.constant;


public enum Role {
    USER, GUEST;

    public String getKey() {
        return name();
    }
}
