package com.kosmos.brushbreakfast.utils;

import com.kosmos.brushbreakfast.MyApplication;

public class InfoRecord {

    private static final String HOTEL_CODE = "HOTEL_CODE";
    private static final String HOTEL_MANAGER_PHONE_NUMBER = "HOTEL_MANAGER_PHONE_NUMBER";


    private InfoRecord() {
    }

    private static class InfoRecordHolder {
        private static InfoRecord mInstance = new InfoRecord();
    }

    public static InfoRecord getInstance() {
        return InfoRecordHolder.mInstance;
    }

    public void setHotelCode(String hotelCode) {
        SPUtil.setString(MyApplication.getsAppContext(), HOTEL_CODE, hotelCode);
    }

    public String getHotelCode() {
        return SPUtil.getString(MyApplication.getsAppContext(), HOTEL_CODE, "");
    }

//    public void setHotelManagerPhoneNumber(String phoneNumber) {
//        SPUtil.setString(MyApplication.getsAppContext(), HOTEL_MANAGER_PHONE_NUMBER, phoneNumber);
//    }
//
//    public String getHotelManagerPhoneNumber() {
//        return SPUtil.getString(MyApplication.getsAppContext(), HOTEL_MANAGER_PHONE_NUMBER, "");
//    }
}
