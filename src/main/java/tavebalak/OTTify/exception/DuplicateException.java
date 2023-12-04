package tavebalak.OTTify.exception;

import lombok.Getter;

@Getter
public class DuplicateException extends RuntimeException{

    public DuplicateException(ErrorCode code) {
        super(code.getMessage());
    }
}
