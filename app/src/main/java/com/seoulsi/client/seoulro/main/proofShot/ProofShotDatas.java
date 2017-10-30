package com.seoulsi.client.seoulro.main.proofShot;

/**
 * Created by user on 2017-10-31.
 */

public class ProofShotDatas {
    String place_picture;
    public  ProofShotDatas(String place_picture){
        this.place_picture = place_picture;
    }
    public String getimage_url()
    {
        return place_picture;
    }
}
