package com.seoulsi.client.seoulro.main.proofShot;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.seoulsi.client.seoulro.R;

/**
 * Created by user on 2017-10-31.
 */

public class ProofViewHolder extends RecyclerView.ViewHolder {
    ImageView proofImg;

    public ProofViewHolder(View itemView){
        super(itemView);
        proofImg = (ImageView)itemView.findViewById(R.id.img_proof);

    }

}
