package com.seoulsi.client.seoulro.mypage;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.seoulsi.client.seoulro.R;

/**
 * Created by user on 2017-10-21.
 */

public class MyseoulloViewHolder extends RecyclerView.ViewHolder {
    ImageView seoulloImg;

    public MyseoulloViewHolder(View itemView){
        super(itemView);
        seoulloImg = (ImageView)itemView.findViewById(R.id.img_myseoullo);

    }

}
