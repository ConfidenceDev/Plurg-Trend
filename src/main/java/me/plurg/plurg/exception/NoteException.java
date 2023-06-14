package me.plurg.plurg.exception;

import lombok.Data;

@Data
public class NoteException extends RuntimeException{

    private String errorCode;
    private int status;

    public NoteException(String message, String errorCode, int status) {
        super(message);
        this.errorCode = errorCode;
        this.status = status;
    }
}
