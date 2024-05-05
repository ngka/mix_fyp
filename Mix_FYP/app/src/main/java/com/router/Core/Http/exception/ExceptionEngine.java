package com.router.Core.Http.exception;

import android.net.ParseException;
import android.text.TextUtils;

import com.google.gson.JsonParseException;

import org.apache.http.conn.ConnectTimeoutException;
import org.json.JSONException;

import java.io.IOException;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import retrofit2.adapter.rxjava.HttpException;

/**
 * 網絡請求code
 */

public class ExceptionEngine {
    //對應HTTP的狀態碼
    private static final int FAIL = 0;
    private static final int UNAUTHORIZED = 401;
    private static final int FORBIDDEN = 403;
    private static final int NOT_FOUND = 404;
    private static final int REQUEST_TIMEOUT = 408;
    private static final int INTERNAL_SERVER_ERROR = 500;
    private static final int BAD_GATEWAY = 502;
    private static final int SERVICE_UNAVAILABLE = 503;
    private static final int GATEWAY_TIMEOUT = 504;

    public static ApiException handleException(Throwable e) {
        ApiException ex;
        if (e instanceof ServerException) {             //HTTP錯誤
            ServerException httpException = (ServerException) e;
            ex = new ApiException(e, ErrorType.HTTP_ERROR);
            if (!TextUtils.isEmpty(httpException.message)) {
                ex.message = httpException.message;
                return ex;
            }
            switch (httpException.code) {
                case FAIL:
                    ex.message = TextUtils.isEmpty(e.getMessage()) ? "userName or passWord is error!" : e.getMessage();
                    break;
                case UNAUTHORIZED:
                    ex.message = "當前請求需要用戶驗證";
                    break;
                case FORBIDDEN:
                    ex.message = "服務器已經理解請求，但是拒絕執行它";
                    break;
                case NOT_FOUND:
                    ex.message = "服務器異常，請稍後再試";
                    break;
                case REQUEST_TIMEOUT:
                    ex.message = "請求超時";
                    break;
                case GATEWAY_TIMEOUT:
                    ex.message = "作為網關或者代理工作的服務器嘗試執行請求時，未能及時從上遊服務器（URI標識出的服務器，例如HTTP、FTP、LDAP" +
                            "）或者輔助服務器（例如DNS）收到響應";
                    break;
                case INTERNAL_SERVER_ERROR:
                    ex.message = "服務器遇到了壹個未曾預料的狀況，導致了它無法完成對請求的處理";
                    break;
                case BAD_GATEWAY:
                    ex.message = "作為網關或者代理工作的服務器嘗試執行請求時，從上遊服務器接收到無效的響應";
                    break;
                case SERVICE_UNAVAILABLE:
                    ex.message = "由於臨時的服務器維護或者過載，服務器當前無法處理請求";
                    break;
                default:
                    ex.message = "網絡錯誤";  //其它均視為網絡錯誤
                    break;
            }
            return ex;
        } else if (e instanceof JsonParseException
                || e instanceof JSONException
                || e instanceof ParseException) {
            ex = new ApiException(e, ErrorType.PARSE_ERROR);
            ex.message = "解析錯誤";            //均視為解析錯誤
            return ex;
        } else if (e instanceof ConnectException || e instanceof SocketTimeoutException || e
                instanceof ConnectTimeoutException) {
            ex = new ApiException(e, ErrorType.NETWORK_ERROR);
            ex.message = "連接失敗";  //均視為網絡錯誤
            return ex;
        } else if (e instanceof HttpException) {
            if ("HTTP 404 Not Found".equals(e.getMessage())) {
                ex = new ApiException(e, ErrorType.NETWORK_ERROR);
                ex.message = "沒有連接服務器";
            } else {
                ex = new ApiException(e, ErrorType.NETWORK_ERROR);
                ex.message = e.getMessage();
            }
            return ex;

        }else if (e instanceof IOException) {
            ex = new ApiException(e, ErrorType.UNKONW);
            ex.message = e.getMessage();          //未知錯誤
            return ex;
        } else {
            ex = new ApiException(e, ErrorType.UNKONW);
            ex.message = "未知錯誤";          //未知錯誤
            return ex;
        }
    }

}

