package me.plurg.plurg.exception;

import lombok.Data;

@Data
public class TrendException extends RuntimeException{

    private String errorCode;
    private int status;

    public TrendException(String message, String errorCode, int status) {
        super(message);
        this.errorCode = errorCode;
        this.status = status;
    }
}
