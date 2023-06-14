package me.plurg.plurg.exception;

import me.plurg.plurg.model.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class APIExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(TrendException.class)
    public ResponseEntity<ErrorResponse> handleCreatorException(TrendException trendException){
        return new ResponseEntity<>(ErrorResponse.builder()
                .errorMessage(trendException.getMessage())
                .errorCode(trendException.getErrorCode())
                .build(),
                HttpStatus.valueOf(trendException.getStatus()));
    }
}
