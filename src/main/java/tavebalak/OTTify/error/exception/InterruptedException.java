package tavebalak.OTTify.error.exception;

import lombok.Getter;
import tavebalak.OTTify.error.ErrorCode;

@Getter
public class InterruptedException extends RuntimeException {

    public InterruptedException(ErrorCode code) {
        super(code.getMessage());
    }
}
