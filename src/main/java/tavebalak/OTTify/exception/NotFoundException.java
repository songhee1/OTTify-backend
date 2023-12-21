package tavebalak.OTTify.exception;

import tavebalak.OTTify.error.ErrorCode;

public class NotFoundException extends Throwable {
    public NotFoundException(ErrorCode code) {
        super(code.getMessage());
    }
}
