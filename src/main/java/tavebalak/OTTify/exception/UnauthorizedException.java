package tavebalak.OTTify.exception;

import lombok.Getter;

@Getter
public class UnauthorizedException extends RuntimeException{

    public UnauthorizedException(ErrorCode code) {
        super(code.getMessage());
    }
}
