package com.seoulsi.client.seoulro.search.recyclerview;

/**
 * Created by SanJuku on 2017-10-18.
 */

public class ItemDataReview {
    int img;
    String title;
    String content;
    String writer;
    String date;

    public ItemDataReview(int img, String title, String content, String writer, String date) {
        this.img = img;
        this.title = title;
        this.content = content;
        this.writer = writer;
        this.date = date;
    }
}
