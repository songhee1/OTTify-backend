package tavebalak.OTTify.exception;

import lombok.Getter;

@Getter
public class ForbiddenException extends RuntimeException {

    public ForbiddenException(ErrorCode code) {
        super(code.getMessage());
    }
}
