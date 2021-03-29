package com.tutorat.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class BusinessResourceException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private Long resourceId;
    private String errorCode;
    private HttpStatus status;

    public BusinessResourceException(String message) {
        super(message);
    }

    public BusinessResourceException(Long resourceId, String message) {
        super(message);
        this.resourceId = resourceId;
    }
    public BusinessResourceException(Long resourceId, String errorCode, String message) {
        super(message);
        this.resourceId = resourceId;
        this.errorCode = errorCode;
    }

    public BusinessResourceException(String errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    public BusinessResourceException(String errorCode, String message, HttpStatus status) {
        super(message);
        this.errorCode = errorCode;
        this.status = status;
    }

}