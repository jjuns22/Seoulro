package com.seoulsi.client.seoulro.mypage;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.seoulsi.client.seoulro.R;

import java.util.ArrayList;

/**
 * Created by user on 2017-10-21.
 */

public class MyseoulloAdapter extends RecyclerView.Adapter<MyseoulloViewHolder> {

    private ViewGroup parent;
    ArrayList<MySeoulloDatas> mySeoulloDatas;
    View.OnClickListener recyclerlistener;
    public static View itemView;


    public MyseoulloAdapter(ArrayList<MySeoulloDatas> myseoulloDatas, View.OnClickListener recyclerlistener) {
        this.mySeoulloDatas = myseoulloDatas;
        this.recyclerlistener = recyclerlistener;

    }

    @Override
    public MyseoulloViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {

        this.parent = parent;
        //설정에서 사용자가 지정하는 상징의 크기에 따라 그에 맞는 xml 세팅해주는 부분
        itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_mypage_myseoul, parent, false);
        itemView.setOnClickListener(recyclerlistener);

        MyseoulloViewHolder viewHolder = new MyseoulloViewHolder(itemView);
        return  viewHolder;
    }


    @Override
    public void onBindViewHolder(final MyseoulloViewHolder holder, final int position) {
        if (String.valueOf(mySeoulloDatas.get(position).getimage_url()).equals("")) {
             holder.seoulloImg.setImageResource(R.drawable.mypage_review_picture);
        } else {
            //holder.seoulloImg.setImageResource(mySeoulloDatas.get(position).getimage_url());
            //이미지 받아오면
            Glide.with(parent.getContext()).load(mySeoulloDatas.get(position).place_picture).into(holder.seoulloImg);
            holder.seoulloImg.setScaleType(ImageView.ScaleType.FIT_XY);
            //holder.symbolImage.setImageResource(R.drawable.none);
        }


    }


    @Override
    public int getItemCount() {

        return (mySeoulloDatas != null) ? mySeoulloDatas.size() : 0;
    }

}
