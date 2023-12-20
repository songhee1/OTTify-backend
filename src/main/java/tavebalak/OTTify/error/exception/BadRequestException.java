package tavebalak.OTTify.error.exception;

import lombok.Getter;
import tavebalak.OTTify.error.ErrorCode;

@Getter
public class BadRequestException extends RuntimeException{
    public BadRequestException(ErrorCode code) {
        super(code.getMessage());
    }
}
