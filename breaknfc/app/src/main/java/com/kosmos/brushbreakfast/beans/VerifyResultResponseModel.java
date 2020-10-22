package com.kosmos.brushbreakfast.beans;

public class VerifyResultResponseModel {

    /**
     * Result : true
     * Msg : 发送验证码成功
     * Status : 1
     * Name : 王阁下
     * RoomNo : A201
     */

    private String Result;
    private String Msg;

    /**
     * 房卡状态
     * 房卡状态：
     * 1有效的房卡
     * -1无效的房卡
     *
     */
    private String Status;

    /**
     * xx阁下
     */
    private String Name;

    private String RoomNo;

    /**
     * 1免费早餐
     * 101付费早餐
     */
    private String FeeType;


    /**
     * 0未核销
     * 1核销
     */
    private String Used;

    /**
     * 返回的房卡号
     */
    private String CardNo;

    public String getFeeType() {
        return FeeType;
    }

    public void setFeeType(String feeType) {
        FeeType = feeType;
    }

    public String getUsed() {
        return Used;
    }

    public void setUsed(String used) {
        Used = used;
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

    public String getStatus() {
        return Status;
    }

    public void setStatus(String Status) {
        this.Status = Status;
    }

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public String getRoomNo() {
        return RoomNo;
    }

    public void setRoomNo(String RoomNo) {
        this.RoomNo = RoomNo;
    }

    public String getCardNo() {
        return CardNo;
    }

    public void setCardNo(String cardNo) {
        CardNo = cardNo;
    }

    @Override
    public String toString() {
        return "VerifyResultResponseModel{" +
                "Result='" + Result + '\'' +
                ", Msg='" + Msg + '\'' +
                ", Status='" + Status + '\'' +
                ", Name='" + Name + '\'' +
                ", RoomNo='" + RoomNo + '\'' +
                ", FeeType='" + FeeType + '\'' +
                ", Used='" + Used + '\'' +
                ", CardNo='" + CardNo + '\'' +
                '}';
    }
}
