package com.kosmos.brushbreakfast.presenters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.google.gson.Gson;
import com.kosmos.brushbreakfast.beans.CardNo;
import com.kosmos.brushbreakfast.beans.VerifyRecordModel;
import com.kosmos.brushbreakfast.beans.VerifyResultResponseModel;
import com.kosmos.brushbreakfast.config.RetrofitManager;
import com.kosmos.brushbreakfast.config.RxObserver;
import com.kosmos.brushbreakfast.database.DatabaseHelper;
import com.kosmos.brushbreakfast.utils.RxUtils;
import com.kosmos.brushbreakfast.utils.UT;
import com.kosmos.brushbreakfast.views.VerifyView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class VerifyPresenter {
    private VerifyView mVerifyView;
    private SQLiteDatabase mDatabase;

    public VerifyPresenter(Context context, VerifyView verifyView) {
        mVerifyView = verifyView;
        DatabaseHelper databaseHelper = new DatabaseHelper(context, DatabaseHelper.DATABASE_NAME, null, DatabaseHelper.databaseVersion);
        mDatabase = databaseHelper.getWritableDatabase();
    }

    public List<VerifyRecordModel> getVerifyRecord() {
        List<VerifyRecordModel> recordModelList = new ArrayList<>();
        @SuppressLint("SimpleDateFormat") String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        String selectAllWithDate = "select * from " + DatabaseHelper.VERIFY_RECORD_TABLE_NAME + " where verifyDate = ? order by verifyTime desc ";
        Cursor cursor = mDatabase.rawQuery(selectAllWithDate, new String[]{date});
        if (cursor.moveToFirst()) {
            do {
                String verifyTime = cursor.getString(cursor.getColumnIndex("verifyTime"));
                String roomCardNumber = cursor.getString(cursor.getColumnIndex("roomCardNumber"));
                String verifyResult = cursor.getString(cursor.getColumnIndex("verifyResult"));
                String verifyDate = cursor.getString(cursor.getColumnIndex("verifyDate"));
                String ext = cursor.getString(cursor.getColumnIndex("ext"));
                VerifyRecordModel verifyRecordModel = new VerifyRecordModel();
                verifyRecordModel.setVerifyTime(verifyTime);
                verifyRecordModel.setRoomCardNumber(roomCardNumber);
                verifyRecordModel.setVerifyResult(verifyResult);
                verifyRecordModel.setVerifyDate(verifyDate);
                verifyRecordModel.setExt(ext);
                recordModelList.add(verifyRecordModel);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return recordModelList;
    }

    public void insertVerifyRecord(VerifyRecordModel verifyRecordModel) {
        String insertRecordSql = "insert into " + DatabaseHelper.VERIFY_RECORD_TABLE_NAME +
                "(verifyTime, roomCardNumber, verifyResult, verifyDate, ext)values (?, ?, ?, ?, ?)";

        mDatabase.execSQL(insertRecordSql,
                new String[]{verifyRecordModel.getVerifyTime(), verifyRecordModel.getRoomCardNumber(),
                        verifyRecordModel.getVerifyResult(), verifyRecordModel.getVerifyDate(), verifyRecordModel.getExt()});
        mVerifyView.insertSuccess(verifyRecordModel);
    }

    public void deleteNoTodayRecord() {
        @SuppressLint("SimpleDateFormat")
        String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        String deleteNoTodayRecord = "delete from " + DatabaseHelper.VERIFY_RECORD_TABLE_NAME + " where verifyDate != ?";
        mDatabase.execSQL(deleteNoTodayRecord, new String[]{date});
    }

    public void verifyCard(String originCardId, final String cardId) {
        List<CardNo> cardNoList = new ArrayList<>();
        CardNo originCardNo = new CardNo();
        originCardNo.setCardNo(originCardId);
        cardNoList.add(originCardNo);
        CardNo proceedCardNo = new CardNo();
        proceedCardNo.setCardNo(cardId);
        cardNoList.add(proceedCardNo);

        Map<String, String> params = UT.baseParam();
        params.put("CardNoList", new Gson().toJson(cardNoList));
//        params.put("CardNo", "60AC9434");// A102
//        params.put("CardNo", "60AC9435");// A103
        Log.e("tupobi", "入参 == " + params);

        RetrofitManager.request().verify(params)
                .compose(RxUtils.applySchedulersLife())
                .subscribe(new RxObserver<VerifyResultResponseModel>() {
                    @Override
                    public void onNext(VerifyResultResponseModel verifyResultResponseModel) {
                        UT.loge("verifyResultResponseModel == " + verifyResultResponseModel);
                        mVerifyView.verifySuccess(verifyResultResponseModel, cardId);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                });
    }
}
