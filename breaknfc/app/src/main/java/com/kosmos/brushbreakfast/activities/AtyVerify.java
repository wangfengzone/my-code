package com.kosmos.brushbreakfast.activities;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.OvershootInterpolator;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.kosmos.brushbreakfast.R;
import com.kosmos.brushbreakfast.adapters.AdapterRvVerifyList;
import com.kosmos.brushbreakfast.beans.VerifyResultResponseModel;
import com.kosmos.brushbreakfast.beans.VerifyRecordModel;
import com.kosmos.brushbreakfast.presenters.VerifyPresenter;
import com.kosmos.brushbreakfast.utils.CPUThreadPool;
import com.kosmos.brushbreakfast.utils.Constants;
import com.kosmos.brushbreakfast.utils.SoundPlayer;
import com.kosmos.brushbreakfast.utils.StatusBarUtil;
import com.kosmos.brushbreakfast.utils.ThemeUtil;
import com.kosmos.brushbreakfast.utils.ToastUtils;
import com.kosmos.brushbreakfast.views.VerifyView;
import com.nexless.lockstarreadcard.NFCActivity;
import com.unisound.client.SpeechConstants;
import com.unisound.client.SpeechSynthesizer;
import com.unisound.client.SpeechSynthesizerListener;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

import jp.wasabeef.recyclerview.animators.SlideInRightAnimator;

public class AtyVerify extends NFCActivity implements VerifyView {

    public static void actionStart(Context context) {
        context.startActivity(new Intent(context, AtyVerify.class));
    }

    private RecyclerView rv_verify_list;
    private View view_mask;
    private TextView tv_verify_result, tv_card_number;
    private float mViewMaskTranslationX;
    private AdapterRvVerifyList mAdapterRvVerifyList;
    private VerifyPresenter mVerifyPresenter;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aty_verify);
        ThemeUtil.setStatusBar(this);
        StatusBarUtil.setTitleStatusBarColor(this);
        rv_verify_list = findViewById(R.id.rv_verify_list);
        view_mask = findViewById(R.id.view_mask);
        tv_verify_result = findViewById(R.id.tv_verify_result);
        tv_card_number = findViewById(R.id.tv_card_number);
        TextView tv_date = findViewById(R.id.tv_date);
        mVerifyPresenter = new VerifyPresenter(this, this);

        mVerifyPresenter.deleteNoTodayRecord();
        List<VerifyRecordModel> verifyRecordModelList = mVerifyPresenter.getVerifyRecord();
        mAdapterRvVerifyList = new AdapterRvVerifyList(verifyRecordModelList, this);
        view_mask.post(new Runnable() {
            @Override
            public void run() {
                mViewMaskTranslationX = view_mask.getWidth();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        startTextAnimation();
                    }
                });
            }
        });

        @SuppressLint("SimpleDateFormat") String date = new SimpleDateFormat("yyyy年MM月dd日").format(new Date());
        tv_date.setText(date);

        rv_verify_list.setLayoutManager(new LinearLayoutManager(this));
        rv_verify_list.setAdapter(mAdapterRvVerifyList);
        SlideInRightAnimator animator = new SlideInRightAnimator(new OvershootInterpolator(1f));  //相比于第一种，第二种为SlideInUpAnimator添加插值器
        rv_verify_list.setItemAnimator(animator);
        Objects.requireNonNull(rv_verify_list.getItemAnimator()).setAddDuration(1000);
        rv_verify_list.getItemAnimator().setRemoveDuration(1000);

    }

    private static final int SOUND_TYPE_SUCCESS = 70001;
    private static final int SOUND_TYPE_INVALIDATE = 70002;
    private static final int SOUND_TYPE_NOT_ENOUGH = 70003;

    private void playSoundPrompt(String prompt, int soundType) {

        switch (soundType) {
            case SOUND_TYPE_SUCCESS:
                SoundPlayer.play(this, R.raw.success);
                prompt = "欢迎用餐";
                break;
            case SOUND_TYPE_INVALIDATE:
                SoundPlayer.play(this, R.raw.invalidate);
                prompt = "房卡无效";
                break;
            case SOUND_TYPE_NOT_ENOUGH:
                SoundPlayer.play(this, R.raw.not_enough);
                prompt = "没有餐券";
                break;
        }
        String finalPrompt = prompt;
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                CPUThreadPool.getInstance().execute(new Runnable() {
                    @Override
                    public void run() {
                        //1. 创建语音合成对象, appKey和secret通过 http://dev.hivoice.cn/ 网站申请
                        SpeechSynthesizer mTTSPlayer = new SpeechSynthesizer(AtyVerify.this, Constants.appKey, Constants.appSecret);
                        //2. 设置语音合成参数,设置语音合成模式为本地合成。更多识别参数的设置可以参考《云知声USC API手册(Android)》
                        mTTSPlayer.setOption(SpeechConstants.TTS_SERVICE_MODE, SpeechConstants.TTS_SERVICE_MODE_NET);
                        //3. 设置回调监听
                        mTTSPlayer.setTTSListener(new SpeechSynthesizerListener() {
                            // 语音合成事件回调，支持的回调类型见5.3事件回调
                            public void onEvent(int type) {
                                Log.e("tupobi", "type == " + type);
                            }

                            // 语音合成错误回调,错误输出格式及错误列表见附录。
                            public void onError(int type, String errorMSG) {
                                Log.e("tupobi", "type == " + type + ", errorMSG == " + errorMSG);
                            }
                        });
                        //4. 初始化语音合成引擎
                        mTTSPlayer.init(null);

                        //5. 开始语音合成并播报
                        mTTSPlayer.playText(finalPrompt);
                    }
                });
            }
        }, 50);

    }


    public void startTextAnimation() {
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(
                ObjectAnimator.ofFloat(view_mask, "translationX", 0, mViewMaskTranslationX),
                ObjectAnimator.ofFloat(tv_verify_result, "alpha", 0, 1f)
        );
        animatorSet.setDuration(2000);
        animatorSet.setInterpolator(new AccelerateDecelerateInterpolator());
        animatorSet.start();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        try {
            super.onNewIntent(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (getCardId() != null && !"".equals(getCardId())) {
            String originCardId = getCardId().toUpperCase();
            Log.e("tupobi", "原来卡号 == " + originCardId);
            String processedCardId = processCardNo(originCardId).toUpperCase();
            Log.e("tupobi", "processedCardId == " + processedCardId);
            mVerifyPresenter.verifyCard(originCardId, processedCardId);
        } else {
            ToastUtils.showShortToastSafe("没有读取到卡号");
        }
    }

    private String processCardNo(String originCardNo) {
        if (originCardNo.length() <= 2) {
            return originCardNo;
        }

        boolean isDouble = false;
        int count = originCardNo.length() / 2;
        // 判断是单位还是双位
        if (originCardNo.length() % 2 == 0) {
            isDouble = true;
        } else {
            isDouble = false;
        }
        StringBuilder processedCardNo = new StringBuilder();
        for (int i = count; i > 0; i--) {
            if (isDouble) {
                int tempIndex = 2 * i - 2;
                processedCardNo.append(originCardNo.substring(tempIndex, tempIndex + 2));
            } else {
                int tempIndex = 2 * i - 1;
                processedCardNo.append(originCardNo.substring(tempIndex, tempIndex + 2));
                if (i == 1) {
                    processedCardNo.append(originCardNo.charAt(0));
                }
            }
        }
        return processedCardNo.toString();
    }

    @Override
    public void insertSuccess(final VerifyRecordModel verifyRecordModel) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mAdapterRvVerifyList.addItem2Top(verifyRecordModel, rv_verify_list);
            }
        });
    }

    /***
     *  status：房卡是否有效 1有效 -1无效
     *  result：核销结果 true false
     *  feeType：餐券类型 1免费早餐，101付费早餐
     * @param verifyResultResponseModel model
     * @param cardId
     */
    @Override
    public void verifySuccess(VerifyResultResponseModel verifyResultResponseModel, String cardId) {
        VerifyRecordModel model = new VerifyRecordModel();
        model.setExt("11");
        String dateStr = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        model.setVerifyDate(dateStr.substring(0, 10));
        model.setVerifyTime(dateStr.substring(11));
        model.setRoomCardNumber(cardId);
        model.setExt(new Gson().toJson(verifyResultResponseModel));
        // 房卡是否有效，-1无效1有效
        String status = verifyResultResponseModel.getStatus();
        if (status == null || status.length() == 0) {
            ToastUtils.showShortToastSafe("核销状态返回失败");
            return;
        } else {
            if ("1".equals(status)) {
                // 有效的房卡
                String result = verifyResultResponseModel.getResult();
                if (result == null || result.length() == 0) {
                    ToastUtils.showShortToastSafe("核销结果返回失败");
                    return;
                } else {
                    if ("true".equals(result)) {
                        // 核销成功，看FeeType什么类型，为null的话就是没有餐票了
                        String feeType = verifyResultResponseModel.getFeeType();
                        if (feeType == null || feeType.length() == 0) {
                            ToastUtils.showShortToastSafe("餐票类型返回失败");
                            return;
                        }
                        if ("101".equals(feeType)) {
                            // 付费早餐
                            if (verifyResultResponseModel.getCardNo() != null && !"".equals(verifyResultResponseModel.getCardNo())) {
                                model.setRoomCardNumber(verifyResultResponseModel.getCardNo());
                            }

                            String roomNumber = verifyResultResponseModel.getRoomNo();
                            model.setVerifyResult(roomNumber + "房核销成功，领一份付费早餐");
                            mVerifyPresenter.insertVerifyRecord(model);
                            playSoundPrompt(roomNumber + "房核销成功，领一份付费早餐", SOUND_TYPE_SUCCESS);
                            tv_verify_result.setText(roomNumber + "房核销成功，领一份付费早餐");

                            if (model.getRoomCardNumber().length() > 4) {
                                StringBuilder sb = new StringBuilder();
                                int prefixLength = model.getRoomCardNumber().length() - 4;
                                for (int i = 0; i < prefixLength; i++) {
                                    sb.append("*");
                                }
                                sb.append(model.getRoomCardNumber().substring(model.getRoomCardNumber().length() - 4));
                                tv_card_number.setText(sb.toString());
                            } else {
                                tv_card_number.setText(model.getRoomCardNumber());
                            }
                            startTextAnimation();
                        } else if ("1".equals(feeType)) {
                            // 免费早餐
                            if (verifyResultResponseModel.getCardNo() != null && !"".equals(verifyResultResponseModel.getCardNo())) {
                                model.setRoomCardNumber(verifyResultResponseModel.getCardNo());
                            }
                            String roomNumber = verifyResultResponseModel.getRoomNo();
                            model.setVerifyResult(roomNumber + "房核销成功，领一份免费早餐");
                            mVerifyPresenter.insertVerifyRecord(model);
                            playSoundPrompt(roomNumber + "房核销成功，领一份免费早餐", SOUND_TYPE_SUCCESS);
                            tv_verify_result.setText(roomNumber + "房核销成功，领一份免费早餐");

                            if (model.getRoomCardNumber().length() > 4) {
                                StringBuilder sb = new StringBuilder();
                                int prefixLength = model.getRoomCardNumber().length() - 4;
                                for (int i = 0; i < prefixLength; i++) {
                                    sb.append("*");
                                }
                                sb.append(model.getRoomCardNumber().substring(model.getRoomCardNumber().length() - 4));
                                tv_card_number.setText(sb.toString());
                            } else {
                                tv_card_number.setText(model.getRoomCardNumber());
                            }
                            startTextAnimation();
                        }

                    } else if ("false".equals(result)) {
                        String feeType = verifyResultResponseModel.getFeeType();
                        if (null == feeType || "null".equals(feeType)) {
                            ToastUtils.showShortToastSafe("该房卡无餐券");
                            playSoundPrompt("该房卡无餐券", SOUND_TYPE_NOT_ENOUGH);
                        }
                    } else {
                        ToastUtils.showShortToastSafe("核销结果返回失败");
                        return;
                    }
                }
            } else if ("-1".equals(status)) {
                // 无效的房卡
                //ToastUtils.showShortToastSafe("房卡无效");
                Toast.makeText(this,"房卡无效",Toast.LENGTH_SHORT).show();
                playSoundPrompt("房卡无效", SOUND_TYPE_INVALIDATE);
            } else {
                ToastUtils.showShortToastSafe("核销状态返回失败");
                return;
            }

        }


    }

    public static String getEncryptCardId(String cardId) {
        if (cardId.length() < 3) {
            return cardId;
        }
        StringBuilder encryptCardId = new StringBuilder();
        for (int i = 0; i < cardId.length(); i++) {
            if (i < 3) {
                encryptCardId.append("*");
            } else {
                encryptCardId.append(cardId.charAt(i));
            }
        }
        return encryptCardId.toString();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SoundPlayer.release();
    }
}
