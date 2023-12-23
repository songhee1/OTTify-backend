package tavebalak.OTTify.error.exception;

import lombok.Getter;
import tavebalak.OTTify.error.ErrorCode;

@Getter
public class InternalServerErrorException extends RuntimeException {

    public InternalServerErrorException(ErrorCode code) {
        super(code.getMessage());
    }
}
