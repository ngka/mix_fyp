package com.router.Core.Http.exception;

/**
 * 異常類型
 */

public interface ErrorType {
    /**
     * 請求成功，但數據錯誤
     */
    int ERROR = 0;
    /**
     * 請求成功
     */
    int SUCCESS = 1;
    /**
     * 未知錯誤
     */
    int UNKONW = 1000;

    /**
     * 解析錯誤
     */
    int PARSE_ERROR = 1001;
    /**
     * 網絡錯誤
     */
    int NETWORK_ERROR = 1002;

    /**
     * 解析對象為空
     */
    int EMPTY_BEAN = 1004;


    /**
     *
     */
    int HTTP_ERROR = 1003;
}
