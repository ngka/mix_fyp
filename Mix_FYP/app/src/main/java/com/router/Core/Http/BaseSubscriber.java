package com.router.Core.Http;




import com.router.Core.Http.exception.ApiException;

import rx.Subscriber;


/**
 * BaseSubscriber
 */
public abstract class BaseSubscriber<T> extends Subscriber<T> {

    @Override
    public void onError(Throwable e) {
        ApiException apiException = (ApiException) e;
        onError(apiException);
    }


    /**
     * @param e 錯誤的壹個回調
     */
    protected abstract void onError(ApiException e);

}
