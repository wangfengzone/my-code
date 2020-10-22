package com.kosmos.brushbreakfast.activities;

import android.Manifest;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kosmos.brushbreakfast.MyApplication;
import com.kosmos.brushbreakfast.R;
import com.kosmos.brushbreakfast.base.BaseMVPActivity;
import com.kosmos.brushbreakfast.beans.BindHotelWithDeviceResponseData;
import com.kosmos.brushbreakfast.beans.GetVerifyCodeResponseData;
import com.kosmos.brushbreakfast.beans.IsBindResponseData;
import com.kosmos.brushbreakfast.presenters.InitPresenter;
import com.kosmos.brushbreakfast.utils.AppUpdateTools;
import com.kosmos.brushbreakfast.utils.Constants;
import com.kosmos.brushbreakfast.utils.InfoRecord;
import com.kosmos.brushbreakfast.utils.PermissionRequestCallback;
import com.kosmos.brushbreakfast.utils.PermissionUtils;
import com.kosmos.brushbreakfast.utils.ToastUtils;
import com.kosmos.brushbreakfast.utils.UT;
import com.kosmos.brushbreakfast.views.InitView;

import java.util.List;

public class AtyInit extends BaseMVPActivity<InitPresenter> implements InitView {

    private LinearLayout ll_login, ll_bind;
    private EditText et_hotel_code, et_verify_code;
    private TextView tv_hotel_code, tv_login, tv_download_new_version;
    private CardView cd_check_version;

    @Override
    protected int createView() {
        return R.layout.aty_init;
    }

    @Override
    protected InitPresenter createPresenter() {
        return new InitPresenter();
    }

    @Override
    protected void viewCreated() {
        ll_login = findViewById(R.id.ll_login);
        ll_bind = findViewById(R.id.ll_bind);
        tv_download_new_version = findViewById(R.id.tv_download_new_version);
        et_hotel_code = findViewById(R.id.et_hotel_code);
        cd_check_version = findViewById(R.id.cd_check_version);
        et_verify_code = findViewById(R.id.et_verify_code);
        TextView tv_get_verify_code = findViewById(R.id.tv_get_verify_code);
        TextView tv_bind_device = findViewById(R.id.tv_bind_device);
        TextView tv_version_name = findViewById(R.id.tv_version_name);
        tv_hotel_code = findViewById(R.id.tv_hotel_code);
        tv_login = findViewById(R.id.tv_login);

        String versionName = UT.getLocalVersionName();
        if (versionName != null) {
            versionName = "V" + versionName;
            tv_version_name.setText(versionName);
        }

        if (!"".equals(InfoRecord.getInstance().getHotelCode())) {
            // 已绑定
            ll_login.setVisibility(View.VISIBLE);
            ll_bind.setVisibility(View.GONE);
            tv_hotel_code.setText(InfoRecord.getInstance().getHotelCode());
        } else {
            // 本地没有缓存，不知道绑定没，调接口验证一下
            mPresenter.isBind(UT.getDeviceSN2());
        }

        tv_bind_device.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String verifyCode = et_verify_code.getText().toString();
                String hotelCode = et_hotel_code.getText().toString();
                String sn2 = UT.getDeviceSN2();

                UT.loge("sn2 == " + sn2);

                if ("".equals(hotelCode)) {
                    ToastUtils.showShortToastSafe("请输入酒店编号");
                    return;
                }
                if ("".equals(verifyCode)) {
                    ToastUtils.showShortToastSafe("请输入验证码");
                    return;
                }

                mPresenter.bindHotelWithDevice(verifyCode, hotelCode, sn2);
            }
        });

        tv_get_verify_code.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String hotelCode = et_hotel_code.getText().toString();
                if ("".equals(hotelCode)) {
                    ToastUtils.showShortToastSafe("请输入酒店编号");
                    return;
                }
                mPresenter.getVerifyCode(UT.getDeviceSN2(), hotelCode);
            }
        });

        tv_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AtyVerify.actionStart(AtyInit.this);
                finish();
            }
        });

        PermissionUtils.requestAllPermissionWithRx(this, new PermissionRequestCallback() {
            @Override
            public void onPermissionGranted(List<String> data) {

            }

            @Override
            public void onPermissionRejected(List<String> data) {

            }

            @Override
            public void onPermissionRationale() {

            }
        });

        mPresenter.checkVersion(versionName);
    }

    @Override
    public void getVerifyCodeSuccess(GetVerifyCodeResponseData getVerifyCodeResponseData) {
        String result = getVerifyCodeResponseData.getResult();
        if ("true".equals(result)) {
            ToastUtils.showShortToastSafe("已发送验证码，五分钟内有效");
        } else {
            if (getVerifyCodeResponseData.getMsg() != null) {
                ToastUtils.showShortToastSafe(getVerifyCodeResponseData.getMsg());
            }
        }
    }

    @Override
    public void getDataFail(String message) {
        ToastUtils.showShortToastSafe("获取数据失败");
    }

    @Override
    public void bindHotelWithDeviceResult(BindHotelWithDeviceResponseData bindHotelWithDeviceResponseData) {
        if ("true".equals(bindHotelWithDeviceResponseData.getResult())) {
            ToastUtils.showShortToastSafe("设备绑定酒店成功");
            if (bindHotelWithDeviceResponseData.getHotelID() != null && !"".equals(bindHotelWithDeviceResponseData.getHotelID())) {
                InfoRecord.getInstance().setHotelCode(bindHotelWithDeviceResponseData.getHotelID());
            } else {
                InfoRecord.getInstance().setHotelCode(et_hotel_code.getText().toString());
            }
            ll_bind.setVisibility(View.GONE);
            ll_login.setVisibility(View.VISIBLE);
            tv_hotel_code.setText(InfoRecord.getInstance().getHotelCode());
        } else {
            if (bindHotelWithDeviceResponseData.getMsg() != null) {
                ToastUtils.showShortToastSafe(bindHotelWithDeviceResponseData.getMsg());
            }
        }
    }

    @Override
    public void isBindResult(IsBindResponseData isBindResponseData) {
        if (isBindResponseData != null) {
            if ("true".equals(isBindResponseData.getResult())) {
                String hotelCode = isBindResponseData.getHotelID();
                if (hotelCode != null && !"".equals(hotelCode)) {
                    InfoRecord.getInstance().setHotelCode(hotelCode);
                    ll_bind.setVisibility(View.GONE);
                    ll_login.setVisibility(View.VISIBLE);
                    tv_hotel_code.setText(hotelCode);
                } else {
                    ToastUtils.showShortToastSafe("没有绑定酒店数据");
                }
            } else if ("false".equals(isBindResponseData.getResult())) {
                // 没有绑定
                ll_bind.setVisibility(View.VISIBLE);
                ll_login.setVisibility(View.GONE);
            } else {
                ToastUtils.showShortToastSafe("获取绑定状态失败");
            }
        } else {
            ToastUtils.showShortToastSafe("获取绑定状态失败");
        }
    }

    @Override
    public void shouldUpdate() {
        tv_login.setBackgroundColor(0xffc6c6c6);
        tv_login.setTextColor(0xffffffff);
        tv_login.setClickable(false);
        cd_check_version.setVisibility(View.VISIBLE);

        tv_download_new_version.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (PermissionUtils.hasThePermission(Manifest.permission.WRITE_EXTERNAL_STORAGE, AtyInit.this)) {
                    if (AppUpdateTools.sIsDownloading) {
                        ToastUtils.showShortToastSafe("正在下载，请在通知栏查看下载进度");
                    } else {
                        ToastUtils.showShortToastSafe("已开始下载，请在通知栏查看下载进度");
                        AppUpdateTools appUpdateTools = new AppUpdateTools(MyApplication.getsAppContext(), Constants.updateApkURL);
                        appUpdateTools.notifyAndInstallApp();
                    }

                } else {
                    ToastUtils.showShortToastSafe("请开启读写存储权限");
                }
            }
        });
    }
}
