package com.router.Core.Http.exception;

/**
 * 自定義的異常,處理解析網絡時的錯誤
 */
public class ApiException extends RuntimeException {

    public int code;
    public String message;

    public ApiException(Throwable throwable, int code) {
        super(throwable);
        this.code = code;
    }
}
