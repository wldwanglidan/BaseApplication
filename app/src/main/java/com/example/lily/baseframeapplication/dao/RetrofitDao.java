package com.example.lily.baseframeapplication.dao;

import android.util.Log;
import com.example.lily.baseframeapplication.dao.retrofit.convert.MyGsonConverterFactory;
import com.example.lily.baseframeapplication.dao.retrofit.convert.NullStringToEmptyAdapterFactory;
import com.google.gson.GsonBuilder;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

/**
 * Created by lily on 2017/11/9.
 */

public class RetrofitDao {
    private static RetrofitDao ourInstance;
    private static final String TAG = RetrofitDao.class.getSimpleName();

    private Retrofit mRetrofit;

    private OkHttpClient okHttpClient;

    private static ApiService mApiService;


    // 单例写法---双重检查锁模式
    public static RetrofitDao getInstance() {
        if (ourInstance == null) {
            synchronized (RetrofitDao.class) {
                if (ourInstance == null) ourInstance = new RetrofitDao();
            }
        }
        return ourInstance;
    }


    private RetrofitDao() {
        if (com.example.lily.baseframeapplication.dao.NetworkConfig.getBaseUrl() == null ||
                com.example.lily.baseframeapplication.dao.NetworkConfig.getBaseUrl().trim().equals("")) {
            throw new RuntimeException(
                    "网络模块必须设置在Application处调用 请求的地址 调用方法：NetworkConfig.setBaseUrl(String url)");
        }

        if (mRetrofit == null) {
            okHttpClient = new OkHttpClient.Builder().connectTimeout(10,
                    TimeUnit.SECONDS)
                                                     .writeTimeout(10,
                                                             TimeUnit.SECONDS)
                                                     .readTimeout(30,
                                                             TimeUnit.SECONDS)
                                                     //.addNetworkInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                                                     .addInterceptor(
                                                             new HttpLoggingInterceptor()
                                                                     .setLevel(
                                                                             HttpLoggingInterceptor.Level.BODY))
                                                     .retryOnConnectionFailure(
                                                             true)
                                                     .addInterceptor(
                                                             chain -> interceptResponse(
                                                                     chain))
                                                     //.readTimeout(CONNECT_TIMEOUT_TIME, TimeUnit.SECONDS)
                                                     .cache(com.example.lily.baseframeapplication.dao.NetworkConfig
                                                             .getCache())
                                                     .build();

            mRetrofit = new Retrofit.Builder().addConverterFactory(
                    MyGsonConverterFactory.create(
                            new GsonBuilder().registerTypeAdapterFactory(
                                    new NullStringToEmptyAdapterFactory())
                                             .create()))
                                              .addCallAdapterFactory(
                                                      RxJava2CallAdapterFactory.create())
                                              .client(okHttpClient)
                                              .baseUrl(
                                                      com.example.lily.baseframeapplication.dao.NetworkConfig
                                                              .getBaseUrl())
                                              .build();

            mApiService=mRetrofit.create(ApiService.class);
        }
    }


    public ApiService getApiService() {
        return mApiService;
    }


    private Response interceptResponse(Interceptor.Chain chain)
            throws IOException {
        //获得url上面的请求参数
        Request original = chain.request();
        //    RequestBody body = original.body();
        Request.Builder requestBuilder = original.newBuilder();

        Request request = requestBuilder.build();
        Log.d(TAG, "url=" + request.url().toString());
        Response response = chain.proceed(request);
        return response;
    }
}
