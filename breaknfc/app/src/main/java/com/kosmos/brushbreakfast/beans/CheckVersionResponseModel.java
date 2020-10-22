package com.kosmos.brushbreakfast.beans;

public class CheckVersionResponseModel {

    /**
     * Result : true
     * Msg : 当前APP为最新版本
     */

    private String Result;
    private String Msg;

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

    @Override
    public String toString() {
        return "CheckVersionResponseModel{" +
                "Result='" + Result + '\'' +
                ", Msg='" + Msg + '\'' +
                '}';
    }
}
