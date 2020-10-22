package com.kosmos.brushbreakfast.views;

import com.kosmos.brushbreakfast.beans.VerifyResultResponseModel;
import com.kosmos.brushbreakfast.beans.VerifyRecordModel;

public interface VerifyView {
    void insertSuccess(VerifyRecordModel verifyRecordModel);

    void verifySuccess(VerifyResultResponseModel verifyResultResponseModel, String cardId);
}
