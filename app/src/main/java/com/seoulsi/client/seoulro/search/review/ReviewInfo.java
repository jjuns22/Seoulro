package com.seoulsi.client.seoulro.search.review;


import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by SanJuku on 2017-10-20.
 */

public class ReviewInfo implements Parcelable{
    public int article_id;
    public int user_id;
    public String title;
    public String content;
    public int placeid;
    public String place_picture;
    public String profile_picture;
    public String nickname;
    public long written_time;


    public ReviewInfo(Parcel in){
        this.title = in.readString();
        this.content = in.readString();
        this.place_picture = in.readString();
        this.written_time = in.readLong();
    }
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.title);
        dest.writeString(this.content);
        dest.writeString(this.place_picture);
        dest.writeLong(this.written_time);
    }
    @SuppressWarnings("rawtypes")
    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {

        @Override
        public ReviewInfo createFromParcel(Parcel in) {
            return new ReviewInfo(in);
        }

        @Override
        public ReviewInfo[] newArray(int size) {
            // TODO Auto-generated method stub
            return new ReviewInfo[size];
        }

    };


}
