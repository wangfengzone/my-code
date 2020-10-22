package com.kosmos.brushbreakfast.beans;

public class BindHotelWithDeviceResponseData {


    /**
     * Result : true
     * Msg : 验证码有效，手机成功绑定酒店
     */

    private String Result;
    private String Msg;
    private String HotelID;

    public String getHotelID() {
        return HotelID;
    }

    public void setHotelID(String hotelID) {
        HotelID = hotelID;
    }

    public String getResult() {
        return Result;
    }

    public void setResult(String Result) {
        this.Result = Result;
    }

    public String getMsg() {
        return Msg;
    }

    public void setMsg(String Msg) {
        this.Msg = Msg;
    }
}
