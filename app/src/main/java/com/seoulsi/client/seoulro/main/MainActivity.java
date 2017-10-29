package com.seoulsi.client.seoulro.main;

import android.app.FragmentManager;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.GroundOverlay;
import com.google.android.gms.maps.model.GroundOverlayOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.seoulsi.client.seoulro.R;

import com.seoulsi.client.seoulro.application.ApplicationController;
import com.seoulsi.client.seoulro.login.LoginUserInfo;
import com.seoulsi.client.seoulro.network.NetworkService;
import com.seoulsi.client.seoulro.search.Fragment.DetailsFragment;
import com.seoulsi.client.seoulro.search.SearchInfoActivity;
import com.seoulsi.client.seoulro.login.LoginActivity;
import com.seoulsi.client.seoulro.mypage.MyPageActivity;
import com.seoulsi.client.seoulro.search.SearchActivity;
import com.seoulsi.client.seoulro.search.UploadReviewResult;
import com.seoulsi.client.seoulro.search.WriteReviewActivity;
import com.seoulsi.client.seoulro.search.details.DetailsInfo;
import com.seoulsi.client.seoulro.search.details.DetailsResult;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Header;
import retrofit2.http.Path;

import static com.seoulsi.client.seoulro.R.id.btn_toolBar_mypage;
import static com.seoulsi.client.seoulro.R.id.btn_toolBar_search;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback {

    private final String TAG = "MainActivity";
    private String htmlPageUrl = "http://www.seoul.go.kr/v2012/news/list.html?tr_code=gnb_news";

    public ImageView news1, news2;
    public ArrayList<String> news_link = new ArrayList<>();
    private ArrayList<DetailsInfo> detailsDatas;
    private String token;
    private int placeid = 1;
    private NetworkService service;

    @BindView(btn_toolBar_mypage)
    Button BtnToolBarMypage;
    @BindView(R.id.btn_toolBar_search)
    Button BtnToolBarSearch;
    @BindView(R.id.btn_main_proof_shot)
    Button btnMainProofShot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        //서비스 객체 초기화
        service = ApplicationController.getInstance().getNetworkService();

        news1 = (ImageView) findViewById(R.id.img_main_news1);
        news2 = (ImageView) findViewById(R.id.img_main_news2);

        NewsAsyncTask newsAsyncTask = new NewsAsyncTask();
        newsAsyncTask.execute();


        BtnToolBarMypage.setOnClickListener(onClickListener);
        BtnToolBarSearch.setOnClickListener(onClickListener);

        FragmentManager fragmentManager = getFragmentManager();
        MapFragment mapFragment = (MapFragment)fragmentManager
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        btnMainProofShot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public void onMapReady(final GoogleMap map) {

        LatLng SEOUL = new LatLng(37.556, 126.97);

        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(SEOUL);
        markerOptions.title("서울");
        markerOptions.snippet("한국의 수도");
        map.addMarker(markerOptions);

        map.moveCamera(CameraUpdateFactory.newLatLng(SEOUL));
        map.animateCamera(CameraUpdateFactory.zoomTo(15));

//        LatLng NEWARK = new LatLng(37.556, 126.97);
//
//        //GroundOverlayOptions newarkMap = new GroundOverlayOptions()
//        //        .image(BitmapDescriptorFactory.fromResource(R.drawable.newark_nj_1922))
//        //        .position(NEWARK, 8600f, 6500f);
//
//// Add an overlay to the map, retaining a handle to the GroundOverlay object.
//        GroundOverlay imageOverlay = map.addGroundOverlay(newarkMap);
//
//        LatLngBounds newarkBounds = new LatLngBounds(
//                new LatLng(40.712216, -74.22655),       // South west corner
//                new LatLng(40.773941, -74.12544));      // North east corner
//        GroundOverlayOptions newarkMap = new GroundOverlayOptions()
//                .image(BitmapDescriptorFactory.fromResource(R.drawable.newark_nj_1922))
//                .positionFromBounds(newarkBounds);
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
            //Glide.with(MainActivity.this).load(news_link.get(0)).into(news1);
            // Glide.with(MainActivity.this).load(news_link.get(1)).into(news2);
        }
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btn_toolBar_mypage:
                    Intent mypage = new Intent(MainActivity.this, MyPageActivity.class);
                    startActivity(mypage);
                    break;
                case R.id.btn_toolBar_search:
                    NetWorking();
                    break;

            }
        }
    };

    void NetWorking() {
        token = LoginUserInfo.getInstance().getUserInfo().token;
        detailsDatas = new ArrayList<>();
        Call<DetailsResult> getDetailsResult = service.getDetailsResult(token, placeid);
        getDetailsResult.enqueue(new Callback<DetailsResult>() {
            @Override
            public void onResponse(Call<DetailsResult> call, Response<DetailsResult> response) {
                Log.d(TAG, "통신 전");
                if (response.isSuccessful()) {
                    Log.d(TAG, "통신 후");
                    if (response.body().msg.equals("6")) {
                        Log.d(TAG, "성공");
                        detailsDatas = response.body().result;

                        Intent intent = new Intent(MainActivity.this, SearchInfoActivity.class);
                        intent.putExtra("placeid",placeid);
                        intent.putExtra("place_picture",detailsDatas.get(0).place_picture);
                        intent.putExtra("place_name",detailsDatas.get(0).place_name);
                        intent.putExtra("place_address",detailsDatas.get(0).place_address);
                        intent.putExtra("place_info",detailsDatas.get(0).place_info);
                        intent.putExtra("place_tel",detailsDatas.get(0).place_tel);
                        intent.putExtra("place_opentime",detailsDatas.get(0).place_opentime);
                        intent.putExtra("place_introduce",detailsDatas.get(0).place_introduce);

                        startActivity(intent);
                        Toast.makeText(getBaseContext(), "성공", Toast.LENGTH_SHORT).show();
                    } else if (response.body().msg.equals("1")) {
                        Toast.makeText(getBaseContext(), "유효하지 않은 토큰에러", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(getBaseContext(), "해당계정이 없습니다.", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Log.d(TAG, "실패");
                    Toast.makeText(getBaseContext(), "커넥팅 에러", Toast.LENGTH_SHORT).show();
                }
            }


            @Override
            public void onFailure(Call<DetailsResult> call, Throwable t) {
                Toast.makeText(getBaseContext(), "onFailure", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
