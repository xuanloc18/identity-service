package com.cxl.identity_service.exception;

import com.cxl.identity_service.dto.request.APIResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GobalExceptionHander {
    @ExceptionHandler(value=Exception.class)
    ResponseEntity<APIResponse> handlingRuntimeException(RuntimeException exception){
        APIResponse apiResponse=new APIResponse();
        apiResponse.setCode(ErrorCode.UNCATEGORIZED_EXCEPTION.getCode());
        apiResponse.setMesage(ErrorCode.UNCATEGORIZED_EXCEPTION.getMessage());
        return  ResponseEntity.badRequest().body(apiResponse);
    }
    @ExceptionHandler(value= MethodArgumentNotValidException.class)
    ResponseEntity<APIResponse> handlingMethodException(MethodArgumentNotValidException exception){
        String enumkey=exception.getFieldError().getDefaultMessage();//get key exception
        ErrorCode errorCode=ErrorCode.valueOf(enumkey);
        APIResponse apiResponse=new APIResponse();
        apiResponse.setCode(errorCode.getCode());
        apiResponse.setMesage(errorCode.getMessage());
        return  ResponseEntity.badRequest().body(apiResponse);
    }

    @ExceptionHandler(value=AppException.class)
    ResponseEntity<APIResponse> handlingRuntimeException(AppException exception){
        ErrorCode errorCode=exception.getErrorCode();
        APIResponse apiResponse=new APIResponse();
        apiResponse.setCode(errorCode.getCode());
        apiResponse.setMesage(errorCode.getMessage());
        return  ResponseEntity.badRequest().body(apiResponse);
    }



}
