package tavebalak.OTTify.error.exception;

import lombok.Getter;
import tavebalak.OTTify.error.ErrorCode;

@Getter
public class ForbiddenException extends RuntimeException {

    public ForbiddenException(ErrorCode code) {
        super(code.getMessage());
    }
}
