package com.seoulsi.client.seoulro.search;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_info);

        ButterKnife.bind(this);

        vp.setAdapter(new pagerAdapter(getSupportFragmentManager()));
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
                Intent i = new Intent(SearchInfoActivity.this, WriteReviewActivity.class);
                startActivity(i);
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
        public pagerAdapter(android.support.v4.app.FragmentManager fm) {
            super(fm);
        }

        @Override
        public android.support.v4.app.Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return new DetailsFragment();
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
