package com.kosmos.brushbreakfast.utils;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.content.FileProvider;
import android.text.TextUtils;
import android.util.Log;

import com.kosmos.brushbreakfast.BuildConfig;
import com.kosmos.brushbreakfast.MyApplication;

import java.io.File;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class UT {
    //得不到SN号，反射，API会变，不建议使用
    public static String getDeviceSN1() {
        String serial = null;
        try {
            Class<?> c = Class.forName("android.os.SystemProperties");
            Method get = c.getMethod("get", String.class);
            serial = (String) get.invoke(c, "ro.serialnocustom");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return serial;
    }

    public static String getDeviceSN2(){

        String serialNumber = android.os.Build.SERIAL;

        return serialNumber;
    }

    public static void loge(String msg) {
        if (msg == null) {
            Log.e("MyTag", "我是null");
        } else if (msg.equals("")) {
            Log.e("MyTag", "我是\"\"");
        } else {
            Log.e("MyTag", msg);
        }
    }

    /**
     * 获取本地软件版本号
     */
    public static int getLocalVersion() {
        int localVersion = 0;
        try {
            PackageInfo packageInfo = MyApplication.getsAppContext().getApplicationContext()
                    .getPackageManager()
                    .getPackageInfo(MyApplication.getsAppContext().getPackageName(), 0);
            localVersion = packageInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return localVersion;
    }

    /**
     * 获取本地软件版本号名称
     */
    public static String getLocalVersionName() {
        String localVersion = "";
        try {
            PackageInfo packageInfo = MyApplication.getsAppContext().getApplicationContext()
                    .getPackageManager()
                    .getPackageInfo(MyApplication.getsAppContext().getPackageName(), 0);
            localVersion = packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return localVersion;
    }

    public static Map<String, String> baseParam() {
        long timestamp = new Date().getTime();
        Map<String, String> map = new HashMap<>();
        map.put("HotelID", InfoRecord.getInstance().getHotelCode());
        map.put("Time", String.valueOf(timestamp));
        map.put("Key", "5b7239348d2249a7912ad73def918fdf");
        // 明文
        String plainText = "HotelID=" + InfoRecord.getInstance().getHotelCode() + "&Time=" + timestamp + "&key=5b7239348d2249a7912ad73def918fdf";
        // 密文
        String sign = MD5Utils.md5(plainText).toUpperCase();
        map.put("Sign", sign);
        Log.e("tupobi", "明文 == " + plainText);
        Log.e("tupobi", "密文 == " + sign);
        return map;
    }


    public static void installApk(Context context, String apkPath) {
        if (context == null || TextUtils.isEmpty(apkPath)) {
            return;
        }

        File file = new File(apkPath);
        Intent intent = new Intent(Intent.ACTION_VIEW);

        //判读版本是否在7.0以上
        if (Build.VERSION.SDK_INT >= 24) {
            //provider authorities
            Uri apkUri = FileProvider.getUriForFile(context, BuildConfig.APPLICATION_ID + ".fileProvider", file);
            //Granting Temporary Permissions to a URI
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.setDataAndType(apkUri, "application/vnd.android.package-archive");
        } else {
            intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
        }

        context.startActivity(intent);

    }
}
