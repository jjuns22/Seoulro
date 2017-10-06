package com.seoulsi.client.seoulro;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private String htmlPageUrl = "http://www.seoul.go.kr/v2012/news/list.html?tr_code=gnb_news";

    public ImageView news1, news2;
    public ArrayList<String> news_link = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        news1 = (ImageView)findViewById(R.id.img_main_news1);
        news2 = (ImageView)findViewById(R.id.img_main_news2);

        NewsAsyncTask newsAsyncTask = new NewsAsyncTask();
        newsAsyncTask.execute();
    }

    private class NewsAsyncTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                Document doc = Jsoup.connect(htmlPageUrl).get();
                Elements links = doc.select("ul.newsUL li dl dt a img");
                Elements imglink = links;



//                String imgUrl = imglink.toString();
//
//                String website = "";
//                URL url = null;
//                website = imgUrl;
//                url = new URL(website);                  //String을 URL로 바꿔줌

                //htmlContentInStringFormat +=

//                myboardDatas.add(new MyboardDatas(url, link.ownText()));  //이미지, 보드이름 data저장
//
                Log.i("msg", links.toString());
                for (Element link : links) {
                    Log.i("msg", "link 있다.");
                    news_link.add(link.attr("src"));

                }


            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            Glide.with(MainActivity.this).load(news_link.get(2)).into(news1);
            Glide.with(MainActivity.this).load(news_link.get(3)).into(news2);
        }
    }


}
