package tavebalak.OTTify.common;

import lombok.Getter;

@Getter
public class BaseResponse<T>{
    private T data;

    public BaseResponse(T data) {
        this.data = data;
    }

    public static <T> BaseResponse<T> success(T data) {
        return new BaseResponse<>(data);
    }
}
