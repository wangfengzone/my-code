package com.kosmos.brushbreakfast.adapters;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.TextView;

import com.kosmos.brushbreakfast.R;
import com.kosmos.brushbreakfast.beans.VerifyRecordModel;

import java.util.List;

public class AdapterRvVerifyList extends RecyclerView.Adapter<AdapterRvVerifyList.VerifyListHolder> {

    private List<VerifyRecordModel> mData;

    private ScaleInAnimation mSelectAnimation = new ScaleInAnimation();

    private Context mContext;

    public AdapterRvVerifyList(List<VerifyRecordModel> data, Context context) {
        mData = data;
        mContext = context;
    }

    public void addItem2Top(VerifyRecordModel data, RecyclerView rv_verify_list) {
        mData.add(0, data);
        notifyItemInserted(0);
        rv_verify_list.smoothScrollToPosition(0);
    }

    @NonNull
    @Override
    public VerifyListHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new VerifyListHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_of_rv_verify_list, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull VerifyListHolder verifyListHolder, int positoin) {
        VerifyRecordModel verifyRecordModel = mData.get(positoin);
        String verifyTime = verifyRecordModel.getVerifyTime();
        String verifyResult = verifyRecordModel.getVerifyResult();
        String roomCardNumber = verifyRecordModel.getRoomCardNumber();
        verifyListHolder.tv_time.setText(verifyTime);
        verifyListHolder.tv_verify_result.setText(verifyResult);

        StringBuilder sb = new StringBuilder();
        int prefixLength = roomCardNumber.length() - 4;
        for (int i = 0; i < prefixLength; i++) {
            sb.append("*");
        }
        sb.append(roomCardNumber.substring(roomCardNumber.length() - 4));
        verifyListHolder.tv_card_number.setText(sb.toString());
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    class VerifyListHolder extends RecyclerView.ViewHolder {

        private TextView tv_time, tv_card_number, tv_verify_result;

        VerifyListHolder(@NonNull View itemView) {
            super(itemView);
            tv_time = itemView.findViewById(R.id.tv_time);
            tv_card_number = itemView.findViewById(R.id.tv_card_number);
            tv_verify_result = itemView.findViewById(R.id.tv_verify_result);
        }
    }


    private void addAnimation(VerifyListHolder holder) {
        for (Animator anim : mSelectAnimation.getAnimators(holder.itemView)) {
            anim.setDuration(500).start();
            anim.setInterpolator(new LinearInterpolator());
        }
    }

    @Override
    public void onViewAttachedToWindow(@NonNull VerifyListHolder holder) {
        super.onViewAttachedToWindow(holder);
        addAnimation(holder);
    }

    private class ScaleInAnimation {
        private static final float DEFAULT_SCALE_FROM = .5f;
        private final float mFrom;

        ScaleInAnimation() {
            this(DEFAULT_SCALE_FROM);
        }

        ScaleInAnimation(float from) {
            mFrom = from;
        }

        Animator[] getAnimators(View view) {
            ObjectAnimator scaleX = ObjectAnimator.ofFloat(view, "scaleX", mFrom, 1f);
            ObjectAnimator scaleY = ObjectAnimator.ofFloat(view, "scaleY", mFrom, 1f);
            return new ObjectAnimator[]{scaleX, scaleY};
        }
    }
}
