package com.kosmos.brushbreakfast.utils;

import io.reactivex.Observable;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Android Studio.
 * User: moujie
 * Date: 2019/11/21
 * Time: 17:03
 * Describe: network load data logic
 */
public final class RxUtils {
    public static ObservableTransformer applySchedulersLife() {
        return new ObservableTransformer() {
            @Override
            public Observable apply(Observable upstream) {
                return upstream.subscribeOn(Schedulers.io())//IO线程加载数据
                        .observeOn(AndroidSchedulers.mainThread());//主线程显示数据
            }
        };
    }
}
