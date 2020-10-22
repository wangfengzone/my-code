package com.kosmos.brushbreakfast.beans;

public class IsBindResponseData {

    /**
     * Result : true
     * Msg : 该设备暂已绑定
     * HotelID : 021002
     */

    private String Result;
    private String Msg;
    private String HotelID;

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

    public String getHotelID() {
        return HotelID;
    }

    public void setHotelID(String HotelID) {
        this.HotelID = HotelID;
    }
}
