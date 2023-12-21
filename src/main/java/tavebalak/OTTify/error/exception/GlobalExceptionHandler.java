package tavebalak.OTTify.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import tavebalak.OTTify.common.ApiResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ApiResponse<String> dtoValidation(final MethodArgumentNotValidException e){
        return ApiResponse.error(e.getBindingResult().getAllErrors().get(0).getDefaultMessage());
    }
}
