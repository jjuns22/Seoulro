package com.seoulsi.client.seoulro.search.recyclerview;

import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.seoulsi.client.seoulro.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by SanJuku on 2017-10-18.
 */

public class ReviewViewHolder extends RecyclerView.ViewHolder{
    @Nullable
    @BindView(R.id.imageview_review_image)
    ImageView imageViewReviewImg;
    @BindView(R.id.textview_review_title)
    TextView textViewReviewTitle;
    @BindView(R.id.textview_review_content)
    TextView textViewReviewContent;
    @BindView(R.id.textview_review_date)
    TextView textViewReviewDate;
    @BindView(R.id.textview_review_writer)
    TextView textViewReviewWriter;

    public ReviewViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
