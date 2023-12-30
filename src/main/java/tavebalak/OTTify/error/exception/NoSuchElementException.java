package tavebalak.OTTify.error.exception;

import lombok.Getter;
import tavebalak.OTTify.error.ErrorCode;

@Getter
public class NoSuchElementException extends RuntimeException{
    public NoSuchElementException(ErrorCode code) {
        super(code.getMessage());
    }
}
