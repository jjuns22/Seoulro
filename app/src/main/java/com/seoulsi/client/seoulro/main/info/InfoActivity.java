package com.seoulsi.client.seoulro.main.info;

import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.seoulsi.client.seoulro.R;
import com.seoulsi.client.seoulro.main.Fragment.FacilityFragment;
import com.seoulsi.client.seoulro.main.Fragment.KeyPointFragment;
import com.seoulsi.client.seoulro.main.Fragment.LandScapeFragment;
import com.seoulsi.client.seoulro.main.MainActivity;
import com.seoulsi.client.seoulro.main.info.fragment.EventFragment;
import com.seoulsi.client.seoulro.main.info.fragment.PermanentProgramFragment;
import com.seoulsi.client.seoulro.main.info.fragment.WalkRoadTourCourseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class InfoActivity extends AppCompatActivity {
    @BindView(R.id.viewpager_main_info)
    ViewPager viewPagerMainInfo;
    @BindView(R.id.btn_main_info_event)
    Button btnMainInfoEvent;
    @BindView(R.id.btn_main_info_tour_course)
    Button btnMainInfoTourCourse;
    @BindView(R.id.btn_main_info_permanent_program)
    Button btnMainInfoPermanentProgram;
    @BindView(R.id.imageview_main_info_back)
    ImageView imageViewMainInfoBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        ButterKnife.bind(this);
        viewPagerMainInfo.setAdapter(new InfoActivity.pagerAdapter(getSupportFragmentManager()));
        viewPagerMainInfo.setCurrentItem(0);

        viewPagerMainInfo.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        btnMainInfoEvent.setBackgroundResource(R.drawable.mypage_menu_green);
                        btnMainInfoTourCourse.setBackgroundResource(R.drawable.mypage_menu_white);
                        btnMainInfoPermanentProgram.setBackgroundResource(R.drawable.mypage_menu_white);
                        break;

                    case 1:
                        btnMainInfoEvent.setBackgroundResource(R.drawable.mypage_menu_white);
                        btnMainInfoTourCourse.setBackgroundResource(R.drawable.mypage_menu_green);
                        btnMainInfoPermanentProgram.setBackgroundResource(R.drawable.mypage_menu_white);
                        break;

                    case 2:
                        btnMainInfoEvent.setBackgroundResource(R.drawable.mypage_menu_white);
                        btnMainInfoTourCourse.setBackgroundResource(R.drawable.mypage_menu_white);
                        btnMainInfoPermanentProgram.setBackgroundResource(R.drawable.mypage_menu_green);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        imageViewMainInfoBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btnMainInfoEvent.setOnClickListener(movePageListener);
        btnMainInfoEvent.setTag(0);
        btnMainInfoTourCourse.setOnClickListener(movePageListener);
        btnMainInfoTourCourse.setTag(1);
        btnMainInfoPermanentProgram.setOnClickListener(movePageListener);
        btnMainInfoPermanentProgram.setTag(2);
    }

    View.OnClickListener movePageListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int tag = (int) v.getTag();
            switch (tag) {
                case 0:
                    btnMainInfoEvent.setBackgroundResource(R.drawable.mypage_menu_green);
                    btnMainInfoTourCourse.setBackgroundResource(R.drawable.mypage_menu_white);
                    btnMainInfoPermanentProgram.setBackgroundResource(R.drawable.mypage_menu_white);
                    break;
                case 1:
                    btnMainInfoEvent.setBackgroundResource(R.drawable.mypage_menu_white);
                    btnMainInfoTourCourse.setBackgroundResource(R.drawable.mypage_menu_green);
                    btnMainInfoPermanentProgram.setBackgroundResource(R.drawable.mypage_menu_white);
                    break;
                case 2:
                    btnMainInfoEvent.setBackgroundResource(R.drawable.mypage_menu_white);
                    btnMainInfoTourCourse.setBackgroundResource(R.drawable.mypage_menu_white);
                    btnMainInfoPermanentProgram.setBackgroundResource(R.drawable.mypage_menu_green);
                    break;
            }
            viewPagerMainInfo.setCurrentItem(tag);
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
                    return new EventFragment();
                case 1:
                    return new WalkRoadTourCourseFragment();
                case 2:
                    return new PermanentProgramFragment();
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            return 3;
        }
    }
}
