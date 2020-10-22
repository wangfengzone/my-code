package com.kosmos.brushbreakfast.config;

import com.kosmos.brushbreakfast.beans.VerifyResultResponseModel;
import com.kosmos.brushbreakfast.beans.BindHotelWithDeviceResponseData;
import com.kosmos.brushbreakfast.beans.CheckVersionResponseModel;
import com.kosmos.brushbreakfast.beans.GetVerifyCodeResponseData;
import com.kosmos.brushbreakfast.beans.IsBindResponseData;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by Android Studio.
 * User: moujie
 * Date: 2019/11/21
 * Time: 15:48
 * Describe: interface address
 */
public interface MyApi {
    @FormUrlEncoded
    @POST("IsBound")
    Observable<IsBindResponseData> isBind(@FieldMap Map<String, String> paramMap);

    @FormUrlEncoded
    @POST("InitAPP")
    Observable<GetVerifyCodeResponseData> getVerifyCode(@FieldMap Map<String, String> paramMap);

    @FormUrlEncoded
    @POST("RegistAPP")
    Observable<BindHotelWithDeviceResponseData> bindHotelWithDevice(@FieldMap Map<String, String> paramMap);

    @FormUrlEncoded
    @POST("CheckCardNo")
    Observable<VerifyResultResponseModel> verify(@FieldMap Map<String, String> paramMap);

    @FormUrlEncoded
    @POST("VerifyVersion")
    Observable<CheckVersionResponseModel> checkVersion(@FieldMap Map<String, String> paramMap);
}
