package com.seoulsi.client.seoulro.main.proofShot;

import android.content.Context;
import android.graphics.Picture;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.seoulsi.client.seoulro.R;
import com.seoulsi.client.seoulro.mypage.MySeoulloDatas;
import com.seoulsi.client.seoulro.mypage.MyseoulloViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by SanJuku on 2017-10-29.
 */

public class ProofViewAdapter extends RecyclerView.Adapter<ProofViewHolder>{
    private ViewGroup parent;
    ArrayList<ProofShotDatas> myProofDatas;
    View.OnClickListener recyclerlistener;
    public static View itemView;


    public ProofViewAdapter(ArrayList<ProofShotDatas> proofDatas, View.OnClickListener recyclerlistener) {
        this.myProofDatas = proofDatas;
        this.recyclerlistener = recyclerlistener;

    }

    @Override
    public ProofViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {

        this.parent = parent;
        //설정에서 사용자가 지정하는 상징의 크기에 따라 그에 맞는 xml 세팅해주는 부분
        itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_proof, parent, false);
        itemView.setOnClickListener(recyclerlistener);

        ProofViewHolder viewHolder = new ProofViewHolder(itemView);
        return  viewHolder;
    }


    @Override
    public void onBindViewHolder(final ProofViewHolder holder, final int position) {
        if (String.valueOf(myProofDatas.get(position).getimage_url()).equals("")) {
            holder.proofImg.setImageResource(R.drawable.mypage_review_picture);
        } else {
            //holder.seoulloImg.setImageResource(mySeoulloDatas.get(position).getimage_url());
            //이미지 받아오면
            Glide.with(parent.getContext()).load(myProofDatas.get(position).place_picture).into(holder.proofImg);
            holder.proofImg.setScaleType(ImageView.ScaleType.FIT_XY);
            //holder.symbolImage.setImageResource(R.drawable.none);
        }


    }


    @Override
    public int getItemCount() {

        return (myProofDatas != null) ? myProofDatas.size() : 0;
    }

}
