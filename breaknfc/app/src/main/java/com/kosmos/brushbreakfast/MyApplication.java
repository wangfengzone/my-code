package com.kosmos.brushbreakfast;

import android.app.Application;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import com.kosmos.brushbreakfast.utils.AppUtils;
import com.kosmos.brushbreakfast.utils.Constants;

/**
 * Created by Android Studio.
 * User: moujie
 * Date: 2019/11/21
 * Time: 16:05
 * Describe: global application configuration
 */
public class MyApplication extends Application {
    private static final String TAG = "crash!!";
    private static Context sAppContext;
    private boolean isDebug = true;


    public static Context getsAppContext() {
        return sAppContext;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        isDebug(false);
        sAppContext = this;
        AppUtils.init(this);
    }

    private void isDebug(boolean isDebug) {

        /**
         * TODO
         * 线上环境需要切换项：
         * 1. baseUrl
         * 2. 升级包地址
         */

        if (isDebug) {
            Constants.baseURL = "http://180.169.142.28:6067/api/";
            Constants.updateApkURL = "http://10.3.90.4:8061/BreakfastNFCTest.apk";
        } else {
            Constants.baseURL = "http://101.227.68.195:6067/api/";
            Constants.updateApkURL = "http://appcdn.greentree.cn/breakfast/BreakfastNFC.apk";
        }
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        if (true) {
            Log.e("tupobi", "MyApplication attachBaseContext");
            new Handler(getMainLooper()).post(new Runnable() {
                @Override
                public void run() {
                    while (true) {
                        try {
                            Looper.loop();
                            //try-catch主线程的所有异常；Looper.loop()内部是一个死循环，出现异常时才会退出，所以这里使用while(true)。
                        } catch (Throwable e) {
                            Toast.makeText(sAppContext, "该功能存在异常", Toast.LENGTH_SHORT).show();
                            Log.e(TAG, "主线程异常：" + e.getMessage());
                            e.printStackTrace();
                        }
                    }
                }
            });

            Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
                @Override
                public void uncaughtException(Thread t, Throwable e) {
                    //try-catch子线程的所有异常。
                    Toast.makeText(sAppContext, "该功能存在异常", Toast.LENGTH_SHORT).show();
                    Log.e(TAG, "子线程异常：" + e.getMessage());
                    e.printStackTrace();
                }
            });
        }
    }


}
