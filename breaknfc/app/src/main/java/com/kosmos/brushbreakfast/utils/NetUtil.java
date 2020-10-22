package com.kosmos.brushbreakfast.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.kosmos.brushbreakfast.utils.AppUtils;

/**
 * Created by Android Studio.
 * User: moujie
 * Date: 2019/11/21
 * Time: 16:09
 * Describe: network tools
 */
public final class NetUtil {
    public static boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) AppUtils.getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null) {
            NetworkInfo info = connectivityManager.getActiveNetworkInfo();
            if (info != null && info.isConnected()) {
                if (info.getState() == NetworkInfo.State.CONNECTED) {
                    return true;
                }
            }
        }
        return false;
    }
}
