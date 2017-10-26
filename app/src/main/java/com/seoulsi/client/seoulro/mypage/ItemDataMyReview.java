package com.seoulsi.client.seoulro.mypage;

/**
 * Created by SanJuku on 2017-10-20.
 */

public class ItemDataMyReview {
    public int article_id;
    public int user_id;
    public String title;
    public String content;
    public int place_id;
    public String place_picture;
    public String nickname;
    public int written_time;

    public ItemDataMyReview(
            int article_id,
            int user_id,
            String title,
            String content,
            int place_id,
            String place_picture,
            String nickname,
            int written_time
    ) {
        this.article_id = article_id;
        this.user_id = user_id;
        this.title = title;
        this.content = content;
        this.place_id = place_id;
        this.place_picture = place_picture;
        this.nickname = nickname;
        this.written_time = written_time;
    }
}
