package tavebalak.OTTify.common;

import lombok.Getter;

@Getter
public class ApiResponse<T> {
    private T data;
    private String message;

    public ApiResponse(T data) {
        this.data = data;
    }

    public ApiResponse(String message) {
        this.message = message;
    }

    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>(data);
    }

    public static ApiResponse<String> error(String message) {
        return new ApiResponse(message);
    }

}
