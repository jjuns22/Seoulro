package com.seoulsi.client.seoulro.search.recyclerview;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.seoulsi.client.seoulro.R;

import java.util.ArrayList;

/**
 * Created by SanJuku on 2017-10-18.
 */

public class ReviewRecyclerAdapter extends RecyclerView.Adapter<ReviewViewHolder>{
    ArrayList<ItemDataReview> itemdatas;
    View.OnClickListener clickListener;

    public ReviewRecyclerAdapter(ArrayList<ItemDataReview> itemdatas, View.OnClickListener clickListener) {
        this.itemdatas = itemdatas;
        this.clickListener = clickListener;
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
        holder.imageViewReviewImg.setImageResource(itemdatas.get(position).img);
        holder.textViewReviewTitle.setText(itemdatas.get(position).title);
        holder.textViewReviewContent.setText(itemdatas.get(position).content);
        holder.textViewReviewDate.setText(itemdatas.get(position).date);
        holder.textViewReviewWriter.setText(itemdatas.get(position).writer);
    }

    @Override
    public int getItemCount() {
        return itemdatas != null ? itemdatas.size() : 0;
    }
}
