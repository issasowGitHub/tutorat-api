package com.tutorat.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class BusinessResourceExceptionDTO {
    private String errorCode;
    private String errorMessage;
    private String requestURL;
    private HttpStatus status;
    private String apiDestintaire;
}
