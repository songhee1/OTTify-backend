package tavebalak.OTTify.error.exception;

import lombok.Getter;
import tavebalak.OTTify.error.ErrorCode;

@Getter
public class UnauthorizedException extends RuntimeException{

    public UnauthorizedException(ErrorCode code) {
        super(code.getMessage());
    }
}
