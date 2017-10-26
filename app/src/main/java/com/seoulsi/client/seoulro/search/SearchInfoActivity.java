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
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.seoulsi.client.seoulro.R;
import com.seoulsi.client.seoulro.search.Fragment.DetailsFragment;
import com.seoulsi.client.seoulro.search.Fragment.ReviewFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SearchInfoActivity extends AppCompatActivity {

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
    @BindView(R.id.imageview_search_info_img)
    ImageView imageViewSearchInfoImg;

    public String placeInfo;
    private String placePicture;
    public String placeIntroduce;
    public String placeOpenTime;
    public String placeTel;
    private String placeName;
    private String placeAddress;
    private int placeId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_info);

        ButterKnife.bind(this);


        Intent getData = getIntent();
        placeId = getData.getIntExtra("placeid",-1);
        placeName = getData.getStringExtra("place_name");
        placeAddress = getData.getStringExtra("place_address");
        placePicture = getData.getStringExtra("place_picture");
        placeInfo = getData.getStringExtra("place_info");
        placeIntroduce = getData.getStringExtra("place_introduce");
        placeOpenTime = getData.getStringExtra("place_opentime");
        placeTel = getData.getStringExtra("place_tel");


        // DetailsFragment detailsFragment = new DetailsFragment().newInstance(placeInfo,placeTel,placeOpenTime,placeIntroduce);

        textViewSearchInfoPlaceName.setText(placeName);
        textViewSearchInfoPlaceAddress.setText(placeAddress);
        Glide.with(this)
                .load(placePicture)
                .into(imageViewSearchInfoImg);
        imageViewSearchInfoImg.setScaleType(ImageView.ScaleType.FIT_XY);
        vp.setAdapter(new pagerAdapter(getSupportFragmentManager(), placeName, placeInfo, placeTel, placeOpenTime, placeIntroduce, placeId));
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
        private String placeName;
        private String location;
        private String tel;
        private String openTime;
        private String introduce;
        private int placeId;

        public pagerAdapter(android.support.v4.app.FragmentManager fm) {
            super(fm);
        }

        public pagerAdapter(android.support.v4.app.FragmentManager fm, String placeName, String placeLocation, String placeTel, String placeOpenTime, String placeIntroduce, int placeId) {
            super(fm);
            this.placeName = placeName;
            location = placeLocation;
            tel = placeTel;
            openTime = placeOpenTime;
            introduce = placeIntroduce;
            this.placeId = placeId;
        }
        @Override
        public android.support.v4.app.Fragment getItem(int position) {
            switch (position) {
                case 0:
                    DetailsFragment detailsFragment = new DetailsFragment();
                    Bundle bundleDetails = new Bundle();
                    bundleDetails.putString("place_location",location);
                    bundleDetails.putString("place_tel",tel);
                    bundleDetails.putString("place_opentime",openTime);
                    bundleDetails.putString("place_introduce",introduce);
                    detailsFragment.setArguments(bundleDetails);
                    return detailsFragment;
                case 1:
                    ReviewFragment reviewFragment = new ReviewFragment();
                    Bundle bundleReview = new Bundle();
                    bundleReview.putString("placeName",placeName);
                    bundleReview.putInt("placeid",placeId);
                    reviewFragment.setArguments(bundleReview);
                    return reviewFragment;

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
