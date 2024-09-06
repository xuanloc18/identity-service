package com.cxl.identity_service.exception;

public enum ErrorCode {

    USER_EXIT(1002,"user exited"),
    USER_NOT_EXIT(0000,"user not exited"),
    UNAUTHENTICATION_EXCEPTION(1006," UNAUTHENTICATION_EXCEPTION"),
    UNCATEGORIZED_EXCEPTION(9999," UNCATEGORIZED_EXCEPTION"),
    PASSWORD_EXCEPION(1002,"password must be at least 8 characters")
    ;

    ErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    private int code;
    private String message;

    public String getMessage() {
        return message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    @Override
    public String toString() {
        return "ErrorCode{" +
                "code=" + code +
                ", message='" + message + '\'' +
                '}';
    }
}
