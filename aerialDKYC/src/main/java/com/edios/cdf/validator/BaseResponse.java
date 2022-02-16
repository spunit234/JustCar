package com.edios.cdf.validator;



import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter 
public class BaseResponse {
 
	private HttpStatus httpStatus;
    private String code;
    private String message;
 
    public BaseResponse(HttpStatus httpStatus, String code, String message) {
        super();
        this.httpStatus = httpStatus;
        this.code = code;
        this.message = message;
    }
   
}