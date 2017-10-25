package com.seoulsi.client.seoulro.search;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.seoulsi.client.seoulro.R;
import com.seoulsi.client.seoulro.search.Fragment.DetailsFragment;
import com.seoulsi.client.seoulro.search.Fragment.ReviewFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SearchInfoActivity extends AppCompatActivity {

    @BindView(R.id.fab)
    FloatingActionButton fab;
    @BindView(R.id.btn_search_info_details)
    Button btnSearchInfoDetails;
    @BindView(R.id.btn_search_info_review)
    Button btnSearchInfoReview;
    @BindView(R.id.view_pager_search_info)
    ViewPager vp;
    @BindView(R.id.textview_search_info_placename)
    TextView textViewSearchInfoPlaceName;
    @BindView(R.id.textview_search_info_place_address)
    TextView textViewSearchInfoPlaceAddress;


    public String placeInfo;
    public String placeIntroduce;
    public String placeOpenTime;
    public String placeTel;
    private String placeName;
    private String placeAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_info);

        ButterKnife.bind(this);


        Intent getData = getIntent();
        placeName = getData.getStringExtra("place_name");
        placeAddress = getData.getStringExtra("place_address");

        placeInfo = getData.getStringExtra("place_info");
        placeIntroduce = getData.getStringExtra("place_introduce");
        placeOpenTime = getData.getStringExtra("place_opentime");
        placeTel = getData.getStringExtra("place_tel");


        // DetailsFragment detailsFragment = new DetailsFragment().newInstance(placeInfo,placeTel,placeOpenTime,placeIntroduce);

        textViewSearchInfoPlaceName.setText(placeName);
        textViewSearchInfoPlaceAddress.setText(placeAddress);

        vp.setAdapter(new pagerAdapter(getSupportFragmentManager(), placeInfo, placeTel, placeOpenTime, placeIntroduce));
        vp.setCurrentItem(0);

        vp.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        btnSearchInfoDetails.setBackgroundResource(R.drawable.mypage_menu_green);
                        btnSearchInfoReview.setBackgroundResource(R.drawable.mypage_menu_white);
                        fab.setVisibility(View.GONE);

                        //SearchInfoActivity에서 DetailsFragment로 데이터 전송

                        /*DetailsFragment detailsFragment = new DetailsFragment();
                        Bundle bundle = new Bundle(4);
                        bundle.putString("place_location",placeInfo);
                        bundle.putString("place_tel",placeTel);
                        bundle.putString("place_opentime",placeOpenTime);
                        bundle.putString("place_introduce",placeIntroduce);
                        detailsFragment.setArguments(bundle);*/

                        break;

                    case 1:
                        btnSearchInfoDetails.setBackgroundResource(R.drawable.mypage_menu_white);
                        btnSearchInfoReview.setBackgroundResource(R.drawable.mypage_menu_green);
                        fab.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        // Set up the ViewPager with the sections adapter.


        btnSearchInfoDetails.setOnClickListener(movePageListener);
        btnSearchInfoDetails.setTag(0);
        btnSearchInfoReview.setOnClickListener(movePageListener);
        btnSearchInfoReview.setTag(1);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(), WriteReviewActivity.class);
                intent.putExtra("placename", placeName);
                startActivity(intent);
            }
        });

    }

    View.OnClickListener movePageListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int tag = (int) v.getTag();
            switch (tag) {
                case 0:
                    btnSearchInfoDetails.setBackgroundResource(R.drawable.mypage_menu_green);
                    btnSearchInfoReview.setBackgroundResource(R.drawable.mypage_menu_white);
                    break;
                case 1:
                    btnSearchInfoReview.setBackgroundResource(R.drawable.mypage_menu_green);
                    btnSearchInfoDetails.setBackgroundResource(R.drawable.mypage_menu_white);
                    break;
            }
            vp.setCurrentItem(tag);
        }
    };

    private class pagerAdapter extends FragmentStatePagerAdapter {
        private String location;
        private String tel;
        private String openTime;
        private String introduce;

        public pagerAdapter(android.support.v4.app.FragmentManager fm) {
            super(fm);
        }

        public pagerAdapter(android.support.v4.app.FragmentManager fm, String placeLocation, String placeTel, String placeOpenTime, String placeIntroduce) {
            super(fm);
            location = placeLocation;
            tel = placeTel;
            openTime = placeOpenTime;
            introduce = placeIntroduce;
        }
        @Override
        public android.support.v4.app.Fragment getItem(int position) {
            switch (position) {
                case 0:
                    DetailsFragment detailsFragment = new DetailsFragment();
                    Bundle bundle = new Bundle();
                    bundle.putString("place_location",location);
                    bundle.putString("place_tel",tel);
                    bundle.putString("place_opentime",openTime);
                    bundle.putString("place_introduce",introduce);
                    detailsFragment.setArguments(bundle);
                    return detailsFragment;
                case 1:
                    return new ReviewFragment();

                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            return 2;
        }
    }

}
