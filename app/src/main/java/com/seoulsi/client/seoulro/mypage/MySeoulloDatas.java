package com.seoulsi.client.seoulro.mypage;

import java.net.URL;

/**
 * Created by user on 2017-10-21.
 */

public class MySeoulloDatas {
    int place_id;
    String place_name;
    String place_address;
    String place_picture;
    String place_tel;
    String place_opentime;
    String place_introduce;
    String place_info;
    int place_part;
    int place_region;
    int like_count;


    public  MySeoulloDatas(int place_id,
            String place_name,
            String place_address,
            String place_picture,
            String place_tel,
            String place_opentime,
            String place_introduce,
            String place_info,
            int place_part,
            int place_region,
            int like_count
    ){
        this.place_id = place_id;
        this.place_name = place_name;
        this.place_address = place_address;
        this.place_picture = place_picture;
        this.place_tel = place_tel;
        this.place_opentime = place_opentime;
        this.place_introduce = place_introduce;
        this.place_info = place_info;
        this.place_part = place_part;
        this.place_region = place_region;
        this.like_count = like_count;

    }
    public String getimage_url()
    {
        return place_picture;
    }
    public String getPlace_name() {return  place_name;}

    public void setimage_url(String place_picture)
    {
        this.place_picture = place_picture;
    }


}


