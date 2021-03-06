package com.kosmos.brushbreakfast.presenters;

import android.util.Log;

import com.kosmos.brushbreakfast.base.BasePresenter;
import com.kosmos.brushbreakfast.beans.BindHotelWithDeviceResponseData;
import com.kosmos.brushbreakfast.beans.CheckVersionResponseModel;
import com.kosmos.brushbreakfast.beans.GetVerifyCodeResponseData;
import com.kosmos.brushbreakfast.beans.IsBindResponseData;
import com.kosmos.brushbreakfast.config.RetrofitManager;
import com.kosmos.brushbreakfast.config.RxObserver;
import com.kosmos.brushbreakfast.utils.MD5Utils;
import com.kosmos.brushbreakfast.utils.RxUtils;
import com.kosmos.brushbreakfast.utils.UT;
import com.kosmos.brushbreakfast.views.InitView;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Android Studio.
 * User: moujie
 * Date: 2019/11/21
 * Time: 16:39
 * Describe: presenter demo
 *           you'd better inherit BasePresenter, and the template is corresponding to View
 *           warning!!!!!!!!!!!!!!!!!!!!!!!! you'd better not delete this demo
 */
public class InitPresenter extends BasePresenter<InitView> {
    public void getVerifyCode(String deviceSN, String hotelCode) {
        long timestamp = new Date().getTime();
        Map<String, String> map = new HashMap<>();
        map.put("Time", String.valueOf(timestamp));
        map.put("Key", "5b7239348d2249a7912ad73def918fdf");
        // 明文
        String plainText = "HotelID=" + hotelCode + "&Time=" + timestamp + "&key=5b7239348d2249a7912ad73def918fdf";
        // 密文
        String sign = MD5Utils.md5(plainText).toUpperCase();
        map.put("Sign", sign);

        map.put("DeviceID", deviceSN);
        map.put("HotelID", hotelCode);

        RetrofitManager.request().getVerifyCode(map)
                .compose(RxUtils.applySchedulersLife())
                .subscribe(new RxObserver<GetVerifyCodeResponseData>() {
                    @Override
                    public void onNext(GetVerifyCodeResponseData getVerifyCodeResponseData) {
                        UT.loge("getVerifyCodeResponseData == " + getVerifyCodeResponseData);
                        mView.getVerifyCodeSuccess(getVerifyCodeResponseData);
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.getDataFail(e.getMessage());
                    }
                });
    }


    public void bindHotelWithDevice(String verifyCode, String hotelCode, String sn2) {
        long timestamp = new Date().getTime();
        Map<String, String> map = new HashMap<>();
        map.put("HotelID", hotelCode);
        map.put("Time", String.valueOf(timestamp));
        map.put("Key", "5b7239348d2249a7912ad73def918fdf");
        // 明文
        String plainText = "HotelID=" + hotelCode + "&Time=" + timestamp + "&key=5b7239348d2249a7912ad73def918fdf";
        // 密文1
        String sign = MD5Utils.md5(plainText).toUpperCase();
        map.put("Sign", sign);

        map.put("DeviceID", sn2);
        map.put("PIN", verifyCode);
        map.put("HotelID", hotelCode);

        RetrofitManager.request().bindHotelWithDevice(map)
                .compose(RxUtils.applySchedulersLife())
                .subscribe(new RxObserver<BindHotelWithDeviceResponseData>() {
                    @Override
                    public void onNext(BindHotelWithDeviceResponseData bindHotelWithDeviceResponseData) {
                        mView.bindHotelWithDeviceResult(bindHotelWithDeviceResponseData);
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.getDataFail(e.getMessage());
                    }
                });
    }

    public void isBind(String deviceSN2) {

        long timestamp = new Date().getTime();
        String hotelCode = "021002";
        Map<String, String> map = new HashMap<>();
        map.put("HotelID", hotelCode);
        map.put("Time", String.valueOf(timestamp));
        map.put("Key", "5b7239348d2249a7912ad73def918fdf");
        // 明文
        String plainText = "HotelID=" + hotelCode + "&Time=" + timestamp + "&key=5b7239348d2249a7912ad73def918fdf";
        // 密文
        String sign = MD5Utils.md5(plainText).toUpperCase();
        map.put("Sign", sign);

        map.put("DeviceID", deviceSN2);
        Log.e("tupobi", "DeviceID == " + deviceSN2);
        RetrofitManager.request().isBind(map)
                .compose(RxUtils.applySchedulersLife())
                .subscribe(new RxObserver<IsBindResponseData>() {
                    @Override
                    public void onNext(IsBindResponseData isBindResponseData) {
                        mView.isBindResult(isBindResponseData);
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.getDataFail(e.getMessage());
                    }
                });
    }

    public void checkVersion(String versionName) {

        long timestamp = new Date().getTime();
        String hotelCode = "021002";
        final Map<String, String> map = new HashMap<>();
        map.put("HotelID", hotelCode);
        map.put("Time", String.valueOf(timestamp));
        map.put("Key", "5b7239348d2249a7912ad73def918fdf");
        // 明文
        String plainText = "HotelID=" + hotelCode + "&Time=" + timestamp + "&key=5b7239348d2249a7912ad73def918fdf";
        // 密文
        String sign = MD5Utils.md5(plainText).toUpperCase();
        map.put("Sign", sign);

        map.put("Version", versionName);
        RetrofitManager.request().checkVersion(map)
                .compose(RxUtils.applySchedulersLife())
                .subscribe(new RxObserver<CheckVersionResponseModel>() {
                    @Override
                    public void onNext(CheckVersionResponseModel model) {
                        String result = model.getResult();
                        Log.e("tupobi", "CheckVersionResponseModel == " + model);
                        if ("false".equals(result)) {
                            // 需要升级
                            mView.shouldUpdate();
                        }
                    }
                    @Override
                    public void onError(Throwable e) {
                        mView.getDataFail(e.getMessage());
                    }
                });

    }
}
