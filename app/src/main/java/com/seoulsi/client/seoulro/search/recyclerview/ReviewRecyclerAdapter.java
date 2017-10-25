package com.seoulsi.client.seoulro.search.recyclerview;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.seoulsi.client.seoulro.R;
import com.seoulsi.client.seoulro.search.review.ReviewInfo;
import com.seoulsi.client.seoulro.search.review.UpdateReviewInfo;
import com.seoulsi.client.seoulro.util.TimeUtil;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;

/**
 * Created by SanJuku on 2017-10-18.
 */

public class ReviewRecyclerAdapter extends RecyclerView.Adapter<ReviewViewHolder>{
    ArrayList<ReviewInfo> reviewDatas;
    View.OnClickListener clickListener;

    public ReviewRecyclerAdapter(ArrayList<ReviewInfo> reviewDatas, View.OnClickListener clickListener) {
        this.reviewDatas = reviewDatas;
        this.clickListener = clickListener;
    }

    public void updateAdapter(ArrayList<ReviewInfo> reviewDatas){
        this.reviewDatas = new ArrayList<>();
        this.reviewDatas.clear();
        this.reviewDatas.addAll(reviewDatas);
        notifyDataSetChanged();
    }
    @Override
    public ReviewViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_review,parent,false);
        ReviewViewHolder viewHolder = new ReviewViewHolder(view);
        view.setOnClickListener(clickListener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ReviewViewHolder holder, int position) {
        final ReviewInfo currentReviewData = reviewDatas.get(position);
        //임시 데이터 저장
       /* holder.imageViewReviewImg.setImageResource(itemdatas.get(position).img);
        holder.textViewReviewTitle.setText(itemdatas.get(position).title);
        holder.textViewReviewContent.setText(itemdatas.get(position).content);
        holder.textViewReviewDate.setText(itemdatas.get(position).date);
        holder.textViewReviewWriter.setText(itemdatas.get(position).writer);
        */
       //base64String 데이터 -> stream 데이터 -> image 데이터

        //이미지 뷰에 이미지 보이게 하기
        if(currentReviewData.place_picture !=null) {
            //holder.imageViewReviewImg.setImageBitmap(ChangeBitmap(pictureString));
            Glide.with(holder.itemView.getContext())
                    .load(currentReviewData.place_picture)
                    .into(holder.imageViewReviewImg);
        }
        holder.textViewReviewWriter.setText(currentReviewData.nickname);
        holder.textViewReviewTitle.setText(currentReviewData.title);
        holder.textViewReviewContent.setText(currentReviewData.content);
        holder.textViewReviewDate.setText(TimeUtil.getTimeToPastString(currentReviewData.written_time));
    }

    @Override
    public int getItemCount() {
        return reviewDatas != null ? reviewDatas.size() : 0;
    }


}
