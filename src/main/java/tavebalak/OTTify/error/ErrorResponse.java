package tavebalak.OTTify.error;

import lombok.Getter;

@Getter
public class ErrorResponse<T> {
    private String message;

    public ErrorResponse(String message) {
        this.message = message;
    }

    public static ErrorResponse<String> error(String message) {
        return new ErrorResponse(message);
    }
}
