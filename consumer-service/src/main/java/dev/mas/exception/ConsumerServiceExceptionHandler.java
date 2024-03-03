package dev.mas.exception;

import jakarta.validation.ValidationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RestControllerAdvice
@Slf4j
public class ConsumerServiceExceptionHandler {

    private static final String BAD_REQUEST_REASON = "Incorrectly made request.";
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @ExceptionHandler
    @ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleException(Exception e) {
        log.warn("[HTTP STATUS 500] {} ", e.getMessage(), e);
        return makeErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, e, HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
    }

    @ExceptionHandler
    @ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleRuntimeException(final RuntimeException e) {
        log.warn("[HTTP STATUS 500] {} ", e.getMessage(), e);
        return makeErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, e, HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
    }

    @ExceptionHandler
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public ErrorResponse handleValidationException(final ValidationException e) {
        log.warn("[HTTP STATUS 400] {} ", e.getMessage(), e);
        return makeErrorResponse(HttpStatus.BAD_REQUEST, e, BAD_REQUEST_REASON);
    }

    @ExceptionHandler
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public ErrorResponse handleHttpMessageNotReadableException(final HttpMessageNotReadableException e) {
        log.warn("[HTTP STATUS 400] {} ", e.getMessage(), e);
        return makeErrorResponse(HttpStatus.BAD_REQUEST, e, BAD_REQUEST_REASON);
    }

    @ExceptionHandler
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public ErrorResponse handleMissingServletRequestParameterException(final MissingServletRequestParameterException e) {
        log.warn("[HTTP STATUS 400] {} ", e.getMessage(), e);
        return makeErrorResponse(HttpStatus.BAD_REQUEST, e, BAD_REQUEST_REASON);
    }

    @ExceptionHandler
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public ErrorResponse handleMethodArgumentNotValidException(final MethodArgumentNotValidException e) {
        log.warn("[HTTP STATUS 400] {} ", e.getMessage(), e);
        return makeErrorResponse(HttpStatus.BAD_REQUEST, e, BAD_REQUEST_REASON);
    }


    @ExceptionHandler
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public ErrorResponse handleMethodArgumentTypeMismatchException(final MethodArgumentTypeMismatchException e) {
        log.warn("[HTTP STATUS 400] {} ", e.getMessage(), e);
        return makeErrorResponse(HttpStatus.BAD_REQUEST, e, BAD_REQUEST_REASON);
    }


    private ErrorResponse makeErrorResponse(HttpStatus httpStatus, Throwable e, String reason) {

        return new ErrorResponse(
                httpStatus,
                reason,
                e.getLocalizedMessage(),
                LocalDateTime.now().format(formatter)
        );
    }
}
