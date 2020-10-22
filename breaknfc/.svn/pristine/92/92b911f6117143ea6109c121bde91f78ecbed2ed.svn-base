package com.kosmos.brushbreakfast.config;

import android.util.Log;

import com.kosmos.brushbreakfast.MyApplication;
import com.kosmos.brushbreakfast.utils.Constants;
import com.kosmos.brushbreakfast.utils.NetUtil;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Android Studio.
 * User: moujie
 * Date: 2019/11/21
 * Time: 15:53
 * Describe: network configuration
 */
final class RetrofitHelper {
    private static Retrofit retrofit = null;
    private static volatile OkHttpClient sOkHttpClient;
    private static HttpLoggingInterceptor httpLoggingInterceptor;
    private static String baseUrl;

    public static <K> K createApi(Class<K> cls) {
        RetrofitHelper.baseUrl = Constants.baseURL;
        return getRetrofit().create(cls);
    }

    public static <K> K createApi(Class<K> cls, String baseUrl) {
        RetrofitHelper.baseUrl = baseUrl;
        return getRetrofit().create(cls);
    }

    private static Retrofit getRetrofit() {
        if (retrofit == null) {
            synchronized (RetrofitHelper.class) {
                if (retrofit == null) {
                    httpLoggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
                        @Override
                        public void log(String message) {
                            Log.e("MyTag", "Json数据：" + message);
                        }
                    });
                    httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
                    retrofit = new Retrofit.Builder()
                            .baseUrl(baseUrl)//基础URL 建议以 / 结尾
                            .client(getOkHttpClient())//打印json数据
                            .addConverterFactory(GsonConverterFactory.create())//设置 Json 转换器
                            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())//RxJava 适配器
                            .build();
                }
            }
        }
        return retrofit;
    }

    private static OkHttpClient getOkHttpClient() {
        if (sOkHttpClient == null) {
            synchronized (RetrofitHelper.class) {
                Cache cache = new Cache(new File(MyApplication.getsAppContext().getCacheDir(), "HttpCache"), 1024 * 1024 * 100);
                if (sOkHttpClient == null) {
                    sOkHttpClient = new OkHttpClient.Builder().cache(cache)
                            .connectTimeout(30, TimeUnit.SECONDS)
                            .readTimeout(30, TimeUnit.SECONDS)
                            .writeTimeout(30, TimeUnit.SECONDS)
                            .addInterceptor(new Interceptor() {
                                @Override
                                public Response intercept(Chain chain) throws IOException {
                                    if (NetUtil.isNetworkAvailable()) {
                                        Request request = chain.request()
                                                .newBuilder()
                                                //.addHeader("这里添加头文件", "这里添加头文件")
                                                .build();
                                        return chain.proceed(request);
                                    }
                                    return null;
                                }
                            })
                            .addInterceptor(httpLoggingInterceptor).build();
                }
            }
        }
        return sOkHttpClient;
    }
}
