package tavebalak.OTTify.error.exception;

import lombok.Getter;
import tavebalak.OTTify.error.ErrorCode;

@Getter
public class DuplicateException extends RuntimeException{

    public DuplicateException(ErrorCode code) {
        super(code.getMessage());
    }
}
