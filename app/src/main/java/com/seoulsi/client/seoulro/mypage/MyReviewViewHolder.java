package com.seoulsi.client.seoulro.mypage;

import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.seoulsi.client.seoulro.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by user on 2017-10-19.
 */

public class MyReviewViewHolder extends RecyclerView.ViewHolder{
    @Nullable
    @BindView(R.id.img_reviewpicture_mypage)
    ImageView img_reviewpicture_mypage;
    @BindView(R.id.text_titleRV_mypage)
    TextView text_titleRV_mypage;
    @BindView(R.id.text_contextRV_mypage)
    TextView text_contextRV_mypage;
    @BindView(R.id.text_dateRV_mypage)
    TextView text_dateRV_mypage;
    @BindView(R.id.text_nameRV_mypage)
    TextView text_nameRV_mypage;

    public MyReviewViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
