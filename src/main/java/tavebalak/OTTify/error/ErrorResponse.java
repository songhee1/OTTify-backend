package tavebalak.OTTify.error;

import lombok.Getter;
import tavebalak.OTTify.common.BaseResponse;

@Getter
public class ErrorResponse<T> {
    private String message;

    public ErrorResponse(String message) {
        this.message = message;
    }

    public static BaseResponse error(String message) {
        return new ErrorResponse(message);
    }
}
