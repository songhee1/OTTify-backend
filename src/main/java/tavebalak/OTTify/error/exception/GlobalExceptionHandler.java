package tavebalak.OTTify.error.exception;

import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import tavebalak.OTTify.common.BaseResponse;
import tavebalak.OTTify.error.ErrorResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ErrorResponse dtoValidation(final MethodArgumentNotValidException e){
        return ErrorResponse.error(e.getBindingResult().getAllErrors().get(0).getDefaultMessage());
    }
}
