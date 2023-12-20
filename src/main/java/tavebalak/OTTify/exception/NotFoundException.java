package tavebalak.OTTify.exception;

import lombok.Getter;

@Getter
public class NotFoundException extends RuntimeException {
    public NotFoundException(ErrorCode code) {
        super(code.getMessage());
    }
}
