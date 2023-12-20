package tavebalak.OTTify.error.exception;

import lombok.Getter;
import tavebalak.OTTify.error.ErrorCode;

@Getter
public class NotFoundException extends RuntimeException {
    public NotFoundException(ErrorCode code) {
        super(code.getMessage());
    }
}
