package com.seoulsi.client.seoulro.mypage;

/**
 * Created by SanJuku on 2017-10-20.
 */

public class ItemDataMyReview {
    public int img;
    public String title;
    public String content;
    public String writer;
    public String date;

    public ItemDataMyReview(int img, String title, String content, String writer, String date) {
        this.img = img;
        this.title = title;
        this.content = content;
        this.writer = writer;
        this.date = date;
    }
}
