package com.seoulsi.client.seoulro.mypage;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.seoulsi.client.seoulro.R;
import com.seoulsi.client.seoulro.search.recyclerview.ItemDataReview;
import com.seoulsi.client.seoulro.search.recyclerview.ReviewViewHolder;

import java.util.ArrayList;

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
        holder.img_reviewpicture_mypage.setImageResource(itemdatas.get(position).img);
        holder.text_titleRV_mypage.setText(itemdatas.get(position).title);
        holder.text_contextRV_mypage.setText(itemdatas.get(position).content);
        holder.text_dateRV_mypage.setText(itemdatas.get(position).date);
        holder.text_dateRV_mypage.setText(itemdatas.get(position).writer);
    }

    @Override
    public int getItemCount() {
        return itemdatas != null ? itemdatas.size() : 0;
    }
}

