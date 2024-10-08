package com.cxl.identity_service.exception;

import com.cxl.identity_service.dto.request.APIResponse;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintViolation;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Log4j2
@ControllerAdvice
public class GobalExceptionHander {

    private static final  String MIN_ATRIBUTE="min";

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
        Map<String,Object> atributes;
        // Lấy đối tượng ConstraintViolation từ danh sách các lỗi ràng buộc vi phạm
        // 'exception' có thể là một ngoại lệ như MethodArgumentNotValidException, được sinh ra khi có lỗi xác thực dữ liệu.
        // getBindingResult() trả về đối tượng BindingResult chứa thông tin về các lỗi xác thực.
        // getAllErrors() trả về danh sách tất cả các lỗi trong quá trình kiểm tra tính hợp lệ.
        // getFirst() lấy lỗi đầu tiên trong danh sách lỗi (giả định nó là một phương thức tùy chỉnh để lấy phần tử đầu).
        // unwrap(ConstraintViolation.class) chuyển đối tượng lỗi từ kiểu tổng quát sang kiểu cụ thể ConstraintViolation.
        var constraintViolation = exception.getBindingResult().getAllErrors().getFirst().unwrap(ConstraintViolation.class);

        // Từ đối tượng ConstraintViolation, lấy ra mô tả ràng buộc (ConstraintDescriptor),
        // chứa thông tin về ràng buộc vi phạm như các tham số ràng buộc, thông điệp lỗi, v.v.
        // getAttributes() trả về một Map<String, Object> chứa các thuộc tính của ràng buộc (ví dụ như min, max, message, v.v.).
        atributes = constraintViolation.getConstraintDescriptor().getAttributes();
        APIResponse apiResponse=new APIResponse();
        apiResponse.setCode(errorCode.getCode());
        apiResponse.setMesage(Objects.nonNull(atributes)?
                mapAtribute(errorCode.getMessage(), atributes)
                :errorCode.getMessage());
        return  ResponseEntity.badRequest().body(apiResponse);
    }

    @ExceptionHandler(value=AppException.class)
    ResponseEntity<APIResponse> handlingRuntimeException(AppException exception){
        ErrorCode errorCode=exception.getErrorCode();
        APIResponse apiResponse=new APIResponse();
        apiResponse.setCode(errorCode.getCode());
        apiResponse.setMesage(errorCode.getMessage());
        return  ResponseEntity
                .status(errorCode.getStatusCode())
        .body(apiResponse);
    }

    @ExceptionHandler(value = AccessDeniedException.class)
    ResponseEntity<APIResponse> handResponseResponseEntity(AccessDeniedException accessDeniedException){
        ErrorCode errorCode=ErrorCode.UNAUTHORIZED;
        return  ResponseEntity.status(errorCode.getStatusCode())
                .body(APIResponse.builder()
                        .code(errorCode.getCode())
                        .mesage(errorCode.getMessage())
                        .build());

    }

    private  String mapAtribute(String message, Map<String,Object> atribute){
        String minvalue=String.valueOf(atribute.get(MIN_ATRIBUTE));
        return message.replace("{"+MIN_ATRIBUTE+"}",minvalue);
    }



}
