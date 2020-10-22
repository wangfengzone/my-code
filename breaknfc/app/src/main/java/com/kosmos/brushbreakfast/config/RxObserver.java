package com.kosmos.brushbreakfast.config;

import android.net.ParseException;

import com.google.gson.JsonIOException;
import com.google.gson.JsonParseException;
import com.kosmos.brushbreakfast.utils.NetUtil;
import com.kosmos.brushbreakfast.utils.ToastUtils;

import org.json.JSONException;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import retrofit2.HttpException;

/**
 * Created by Android Studio.
 * User: moujie
 * Date: 2019/11/21
 * Time: 17:06
 * Describe: network error information processing
 */
public class RxObserver<T> implements Observer<T> {

    @Override
    public void onSubscribe(@NonNull Disposable d) {
    }

    @Override
    public void onNext(@NonNull T t) {

    }

    @Override
    public void onError(@NonNull Throwable e) {
        if (!NetUtil.isNetworkAvailable()) {
            return;
        }
    }

    public void getErrorMessage(@NonNull Throwable e) {
        String msg = "";
        if (e instanceof ConnectException) {
            msg = "网络不可用";
        } else if (e instanceof UnknownHostException) {
            msg = "未知主机错误";
            //  msg = "网络不可用";
        } else if (e instanceof SocketTimeoutException) {
            msg = "请求网络超时";
        } else if (e instanceof HttpException) {
            HttpException httpException = (HttpException) e;
            msg = convertStatusCode(httpException);
        } else if (e instanceof JsonParseException || e instanceof ParseException || e instanceof JSONException || e instanceof JsonIOException) {
            msg = "数据解析错误";
        }

        if (!msg.isEmpty()) {
            ToastUtils.showShortToastSafe(msg);
        }
    }

    @Override
    public void onComplete() {

    }

    private String convertStatusCode(HttpException httpException) {
        String msg;
        if (httpException.code() == 500) {
            msg = "服务器发生错误";
        } else if (httpException.code() == 404) {
            msg = "请求地址不存在";
        } else if (httpException.code() == 403) {
            msg = "请求被服务器拒绝";
        } else if (httpException.code() == 307) {
            msg = "请求被重定向到其他页面";
        } else {
            msg = httpException.message();
        }
        return msg;
    }
}
