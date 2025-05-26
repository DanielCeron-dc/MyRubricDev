// BusinessException.java - Custom exception for business logic errors
package co.edu.unicauca.distribuidos.core.errores;

import org.springframework.http.HttpStatus;

import co.edu.unicauca.distribuidos.core.errores.modelos.ErrorCode;

public class BusinessException extends RuntimeException {
    private final ErrorCode errorCode;
    private final HttpStatus httpStatus;
    private final String details;

    public BusinessException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
        this.httpStatus = HttpStatus.BAD_REQUEST;
        this.details = null;
    }

    public BusinessException(ErrorCode errorCode, String details) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
        this.httpStatus = HttpStatus.BAD_REQUEST;
        this.details = details;
    }

    public BusinessException(ErrorCode errorCode, HttpStatus httpStatus, String details) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
        this.httpStatus = httpStatus;
        this.details = details;
    }

    public ErrorCode getErrorCode() { return errorCode; }
    public HttpStatus getHttpStatus() { return httpStatus; }
    public String getDetails() { return details; }
}