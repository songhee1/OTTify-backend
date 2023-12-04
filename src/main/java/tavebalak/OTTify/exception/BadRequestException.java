package tavebalak.OTTify.exception;

import lombok.Getter;

@Getter
public class BadRequestException extends RuntimeException{
    public BadRequestException(ErrorCode code) {
        super(code.getMessage());
    }
}
