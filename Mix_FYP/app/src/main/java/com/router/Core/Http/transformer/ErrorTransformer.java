package com.router.Core.Http.transformer;


import android.text.TextUtils;

import com.google.gson.Gson;
import com.router.Core.Http.BaseHttpResult;
import com.router.Core.Http.exception.ErrorType;
import com.router.Core.Http.exception.ExceptionEngine;
import com.router.Core.Http.exception.ServerException;
import com.router.utils.LogUtils;

import retrofit2.adapter.rxjava.HttpException;
import rx.Observable;
import rx.functions.Func1;

/**
 * 異常Transformer
 */

public class ErrorTransformer<T> implements Observable.Transformer<BaseHttpResult<T>, T> {

    private static ErrorTransformer errorTransformer = null;
    private static final String TAG = "ErrorTransformer";

    @Override
    public Observable<T> call(Observable<BaseHttpResult<T>> responseObservable) {

        return responseObservable.map(new Func1<BaseHttpResult<T>, T>() {
            @Override
            public T call(BaseHttpResult<T> httpResult) {

                if (httpResult == null)
                    throw new ServerException(ErrorType.EMPTY_BEAN, "解析對象為空");

                LogUtils.e(TAG, httpResult.toString());
                if (httpResult.getCode() != 200) {
                    throw new ServerException(httpResult.getCode(), httpResult.getMessage() != null && !TextUtils.isEmpty(httpResult.getMessage()) ? httpResult.getMessage() : httpResult.getData().toString());
                }
                return httpResult.getData();
            }
        }).onErrorResumeNext(new Func1<Throwable, Observable<? extends T>>() {
            @Override
            public Observable<? extends T> call(Throwable throwable) {
                //ExceptionEngine為處理異常的驅動器throwable
                if (throwable instanceof HttpException) {
                    try {
                        String errorJson = ((HttpException) throwable).response().errorBody().string();
                        BaseHttpResult httpResult = new Gson().fromJson(errorJson, BaseHttpResult.class);
                        if (httpResult.getCode() != 200) {
                            throw new ServerException(httpResult.getCode(), httpResult.getMessage() != null && !TextUtils.isEmpty(httpResult.getMessage()) ? httpResult.getMessage() : httpResult.getData().toString());
                        } else {
                            if (!httpResult.isStatus()) {
                                throw new ServerException(httpResult.getCode(), httpResult.getMessage() != null && !TextUtils.isEmpty(httpResult.getMessage()) ? httpResult.getMessage() : httpResult.getData().toString());
                            }
                        }
                    } catch (Exception err) {
                        return Observable.error(ExceptionEngine.handleException(err));
                    }
                }
                throwable.printStackTrace();
                return Observable.error(ExceptionEngine.handleException(throwable));
            }
        });

    }

    /**
     * @return 線程安全, 雙層校驗
     */
    public static <T> ErrorTransformer<T> getInstance() {

        if (errorTransformer == null) {
            synchronized (ErrorTransformer.class) {
                if (errorTransformer == null) {
                    errorTransformer = new ErrorTransformer<>();
                }
            }
        }
        return errorTransformer;

    }
}
