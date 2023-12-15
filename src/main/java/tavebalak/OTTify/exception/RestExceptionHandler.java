package tavebalak.OTTify.exception;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import tavebalak.OTTify.common.ApiResponse;

import javax.servlet.http.HttpServletRequest;
import java.io.PrintWriter;
import java.io.StringWriter;

@Slf4j
@RestControllerAdvice
public class RestExceptionHandler {
    // Custom Bad Request Error
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BadRequestException.class)
    protected ApiResponse<String> handleBadRequestException(BadRequestException exception, HttpServletRequest request) {
        logInfo(request, exception.getMessage());
        return ApiResponse.error(exception.getMessage());
    }

    // Custom Unauthorized Error
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(UnauthorizedException.class)
    protected ApiResponse<String> handleUnauthorizedException(UnauthorizedException exception,
                                                              HttpServletRequest request) {
        logInfo(request, exception.getMessage());
        return ApiResponse.error(exception.getMessage());
    }

    // Custom Internal Server Error
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(InternalServerErrorException.class)
    protected ApiResponse<String> handleInternalServerErrorException(InternalServerErrorException exception,
                                                                     HttpServletRequest request) {
        logInfo(request, exception.getMessage());
        return ApiResponse.error(exception.getMessage());
    }

    // @RequestBody valid 에러
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ApiResponse<String> handleMethodArgNotValidException(MethodArgumentNotValidException exception,
                                                                   HttpServletRequest request) {
        String message = exception.getBindingResult().getAllErrors().get(0).getDefaultMessage();
        logInfo(request, message);
        return ApiResponse.error(message);
    }

    // @ModelAttribute valid 에러
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BindException.class)
    protected ApiResponse<String> handleMethodArgNotValidException(BindException exception,
                                                                   HttpServletRequest request) {
        String message = exception.getBindingResult().getAllErrors().get(0).getDefaultMessage();
        logInfo(request, message);
        return ApiResponse.error(message);
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    public ApiResponse<String> handleNotFoundException(NotFoundException exception, HttpServletRequest request) {
        logInfo(request, exception.getMessage());
        log.info("log");
        return ApiResponse.error(exception.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(DuplicateException.class)
    public ApiResponse<String> handleDuplicationException(DuplicateException exception, HttpServletRequest request) {
        logInfo(request, exception.getMessage());
        return ApiResponse.error(exception.getMessage());
    }

    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(ForbiddenException.class)
    public ApiResponse<String> handlerForbiddenException(ForbiddenException exception, HttpServletRequest request) {
        logInfo(request, exception.getMessage());
        return ApiResponse.error(exception.getMessage());
    }

    private void logInfo(HttpServletRequest request, String message) {
        log.info("{} {} : {} - {} (traceId: {})",
                request.getMethod(), request.getRequestURI(), message, getTraceId());
    }

    private void logWarn(HttpServletRequest request, Exception exception) {
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);

        exception.printStackTrace(printWriter);
        String stackTrace = stringWriter.toString();

        log.warn("{} {} (traceId: {})\n{}",
                request.getMethod(), request.getRequestURI(), getTraceId(), stackTrace);
    }

    private String getTraceId() {
        return MDC.get("traceId");
    }
}
