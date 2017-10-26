package com.seoulsi.client.seoulro.mypage;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.seoulsi.client.seoulro.R;
import com.seoulsi.client.seoulro.search.recyclerview.ItemDataReview;
import com.seoulsi.client.seoulro.search.recyclerview.ReviewViewHolder;
import com.seoulsi.client.seoulro.util.TimeUtil;

import java.util.ArrayList;

import static java.security.AccessController.getContext;

/**
 * Created by user on 2017-10-19.
 */

public class MyReviewRecyclerAdapter extends RecyclerView.Adapter<MyReviewViewHolder>{
    ArrayList<ItemDataMyReview> itemdatas;
    View.OnClickListener clickListener;

    public MyReviewRecyclerAdapter(ArrayList<ItemDataMyReview> itemdatas, View.OnClickListener clickListener) {
        this.itemdatas = itemdatas;
        this.clickListener = clickListener;
    }

    @Override
    public MyReviewViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_mypage_myreview,parent,false);
        MyReviewViewHolder viewHolder = new MyReviewViewHolder(view);
        view.setOnClickListener(clickListener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MyReviewViewHolder holder, int position) {
        if (String.valueOf(itemdatas.get(position).place_picture).equals("")) {
            holder.img_reviewpicture_mypage.setImageResource(R.drawable.mypage_review_picture);
        } else {
            //이미지 받아오면
            Glide.with(holder.itemView.getContext()).load(itemdatas.get(position).place_picture).into(holder.img_reviewpicture_mypage);
            holder.img_reviewpicture_mypage.setScaleType(ImageView.ScaleType.FIT_XY);
            //holder.symbolImage.setImageResource(R.drawable.none);
        }

        holder.text_titleRV_mypage.setText(itemdatas.get(position).title);
        holder.text_contextRV_mypage.setText(itemdatas.get(position).content);
        holder.text_dateRV_mypage.setText(TimeUtil.getTimeToPastString(itemdatas.get(position).written_time));
        holder.text_nameRV_mypage.setText(itemdatas.get(position).nickname);
    }

    @Override
    public int getItemCount() {
        return itemdatas != null ? itemdatas.size() : 0;
    }
}

