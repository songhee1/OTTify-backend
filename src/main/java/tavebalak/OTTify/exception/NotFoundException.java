package tavebalak.OTTify.exception;

public class NotFoundException extends Throwable {
    public NotFoundException(ErrorCode code) {
        super(code.getMessage());
    }
}
