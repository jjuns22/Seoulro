package com.seoulsi.client.seoulro.mypage;

import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.seoulsi.client.seoulro.R;
import com.seoulsi.client.seoulro.mypage.Fragment.MyReviewFragment;
import com.seoulsi.client.seoulro.mypage.Fragment.MySeoulroFragment;

public class MyPageActivity extends AppCompatActivity {
    ViewPager vp;
    Button btnMyreview, btnMyseoul;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_page);
        vp = (ViewPager)findViewById(R.id.viewpager_mypage);
        btnMyreview = (Button)findViewById(R.id.btn_myreview_mypage);
        btnMyseoul = (Button)findViewById(R.id.btn_myseoul_mypage);

        vp.setAdapter(new pagerAdapter(getSupportFragmentManager()));
        vp.setCurrentItem(0);
        vp.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position){
                    case 0 :
                        btnMyseoul.setBackgroundResource(R.drawable.mypage_menu_green);
                        btnMyreview.setBackgroundResource(R.drawable.mypage_menu_white);

                        break;
                    case 1 :
                        btnMyreview.setBackgroundResource(R.drawable.mypage_menu_green);
                        btnMyseoul.setBackgroundResource(R.drawable.mypage_menu_white);

                        break;
                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        btnMyseoul.setOnClickListener(movePageListener);
        btnMyseoul.setTag(0);
        btnMyreview.setOnClickListener(movePageListener);
        btnMyreview.setTag(1);
    }

    View.OnClickListener movePageListener = new View.OnClickListener()
    {
        @Override
        public void onClick(View v)
        {
            int tag = (int) v.getTag();
            switch (tag){
                case 0 :
                    btnMyseoul.setBackgroundResource(R.drawable.mypage_menu_green);
                    btnMyreview.setBackgroundResource(R.drawable.mypage_menu_white);
                    break;
                case 1:
                    btnMyreview.setBackgroundResource(R.drawable.mypage_menu_green);
                    btnMyseoul.setBackgroundResource(R.drawable.mypage_menu_white);
                    break;
            }
            vp.setCurrentItem(tag);

        }
    };

    private class pagerAdapter extends FragmentStatePagerAdapter
    {
        public pagerAdapter(android.support.v4.app.FragmentManager fm)
        {
            super(fm);
        }
        @Override
        public android.support.v4.app.Fragment getItem(int position)
        {
            switch(position)
            {
                case 0:
                    return new MySeoulroFragment();
                case 1:
                    return new MyReviewFragment();

                default:
                    return null;
            }
        }
        @Override
        public int getCount()
        {
            return 2;
        }
    }
}
