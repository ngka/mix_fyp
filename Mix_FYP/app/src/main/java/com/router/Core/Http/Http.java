package com.router.Core.Http;


import com.router.Constant;
import com.router.MyApplication;
import com.router.utils.ActivityManager;
import com.router.utils.NetworkUtil;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 網絡請求
 */
public class Http {

    private static OkHttpClient client;
    private static HttpService httpService;
    private static volatile Retrofit retrofit;


    /**
     * @return retrofit的底層利用反射的方式, 獲取所有的api接口的類
     */
    public static HttpService getHttpService() {
        if (httpService == null) {
            httpService = getRetrofit().create(HttpService.class);
        }
        return httpService;
    }


    /**
     * 設置公共參數
     */
    private static Interceptor addQueryParameterInterceptor() {
        Interceptor addQueryParameterInterceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request originalRequest = chain.request();
                Request request;
                HttpUrl modifiedUrl = originalRequest.url().newBuilder()
                        // Provide your custom parameter here
                        .build();
                request = originalRequest.newBuilder().url(modifiedUrl).build();
                return chain.proceed(request);
            }
        };
        return addQueryParameterInterceptor;
    }

    /**
     * 設置頭
     */
    private static Interceptor addHeaderInterceptor() {
        Interceptor headerInterceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request originalRequest = chain.request();
                Request.Builder requestBuilder = originalRequest.newBuilder()
                        // Provide your custom header here
                        .header("Content-Type", "application/json")
                        .method(originalRequest.method(), originalRequest.body());
                if (MyApplication.getTokenModel() != null) {
                    //設置token
//                    requestBuilder.header("authorization", MyApplication.getTokenModel().getTokenValue());
                    requestBuilder.header(MyApplication.getTokenModel().getTokenName(), MyApplication.getTokenModel().getTokenValue());
                }
                Request request = requestBuilder.build();
                return chain.proceed(request);
            }
        };
        return headerInterceptor;
    }

    /**
     * 設置緩存
     */
    private static Interceptor addCacheInterceptor() {
        Interceptor cacheInterceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                if (!NetworkUtil.isNetworkAvailable(ActivityManager.getInstance().getAppContext())) {
                    request = request.newBuilder()
                            .cacheControl(CacheControl.FORCE_CACHE)
                            .build();
                }
                Response response = chain.proceed(request);
                if (NetworkUtil.isNetworkAvailable(ActivityManager.getInstance().getAppContext())) {
                    int maxAge = 0;
                    // 有網絡時 設置緩存超時時間0個小時 ,意思就是不讀取緩存數據,只對get有用,post沒有緩沖
                    response.newBuilder()
                            .header("Cache-Control", "public, max-age=" + maxAge)
                            .removeHeader("Retrofit")// 清除頭信息，因為服務器如果不支持，會返回壹些幹擾信息，不清除下面無法生效
                            .build();
                } else {
                    // 無網絡時，設置超時為4周  只對get有用,post沒有緩沖
                    int maxStale = 60 * 60 * 24 * 28;
                    response.newBuilder()
                            .header("Cache-Control", "public, only-if-cached, max-stale=" +
                                    maxStale)
                            .removeHeader("nyn")
                            .build();
                }
                return response;
            }
        };
        return cacheInterceptor;
    }

    public static Retrofit getRetrofit() {
        if (retrofit == null) {
            synchronized (Http.class) {
                if (retrofit == null) {
                    //添加壹個log攔截器,打印所有的log
                    HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
                    //可以設置請求過濾的水平,body,basic,headers
                    httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

                    //設置 請求的緩存的大小跟位置
                    File cacheFile = new File(ActivityManager.getInstance().getAppContext().getCacheDir(), "cache");
                    Cache cache = new Cache(cacheFile, 1024 * 1024 * 50); //50Mb 緩存的大小

                    client = new OkHttpClient
                            .Builder()
                            .retryOnConnectionFailure(true)
                            .addInterceptor(addQueryParameterInterceptor())  //參數添加
                            .addInterceptor(addHeaderInterceptor()) // token過濾
                            .addInterceptor(httpLoggingInterceptor) //日誌,所有的請求響應度看到
                            .cache(cache)  //添加緩存
                            .connectTimeout(60l, TimeUnit.SECONDS)
                            .readTimeout(60l, TimeUnit.SECONDS)
                            .writeTimeout(60l, TimeUnit.SECONDS)
                            .build();

                    // 獲取retrofit的實例
                    retrofit = new Retrofit
                            .Builder()
                            .baseUrl(Constant.BASE_URL)  //自己配置
                            .client(client)
                            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                            .addConverterFactory(GsonConverterFactory.create()) //這裏是用的fastjson的
                            .build();
                }
            }
        }
        return retrofit;
    }

}
