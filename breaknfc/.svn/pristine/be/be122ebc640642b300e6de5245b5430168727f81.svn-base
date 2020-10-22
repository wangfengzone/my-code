package com.kosmos.brushbreakfast.config;

/**
 * Created by Android Studio.
 * User: moujie
 * Date: 2019/11/21
 * Time: 18:57
 * Describe: operation packaging
 */
public final class RetrofitManager {
    //全局变量，防止多次生成
    private final static MyApi MY_API_NORMAL = RetrofitHelper.createApi(MyApi.class);
    private static MyApi MY_API_BY_URL;

    public static MyApi request() {
        return MY_API_NORMAL;
    }

    public static MyApi request(String baseUrl) {
        MY_API_BY_URL = RetrofitHelper.createApi(MyApi.class, baseUrl);
        return MY_API_BY_URL;
    }
}
