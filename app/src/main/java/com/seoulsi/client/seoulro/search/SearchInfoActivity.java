package com.seoulsi.client.seoulro.search;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Switch;

import com.seoulsi.client.seoulro.R;
import com.seoulsi.client.seoulro.search.Fragment.DetailsFragment;
import com.seoulsi.client.seoulro.search.Fragment.ReviewFragment;

import butterknife.ButterKnife;

public class SearchInfoActivity extends AppCompatActivity {
    private ViewPager mViewPager;
    private TabLayout tabLayout;
    private FloatingActionButton fab;

    private SectionsPagerAdapter mSectionsPagerAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_info);

        ButterKnife.bind(this);

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        //플로팅버튼 초기화
        fab = (FloatingActionButton) findViewById(R.id.fab);

        //view 객체 초기화
        mViewPager = (ViewPager) findViewById(R.id.view_pager);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        //tab 객체 초기화
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(SearchInfoActivity.this, WriteReviewActivity.class);
                startActivity(i);
            }
        });
        // Set up the ViewPager with the sections adapter.
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                SearchInfoActivity.this.invalidateTabIcon();
            }

            @Override
            public void onPageSelected(int position) {
                if (position == 0) {
                    fab.setVisibility(View.GONE);
                } else {
                    fab.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

   /* public void invalidateTabIcon() {
        int selectedPosition = tabLayout.getSelectedTabPosition();
        for (int i = 0; i < tabLayout.getTabCount(); i++) {
            tabLayout.getTabAt(i).setIcon(getTabIcon(i, i == selectedPosition));
        }
    }*/

/*    private int getTabIcon(int position, boolean isSelected) {
        switch (position) {
            case 0:
                if (isSelected) {
                    return R.drawable.tab_home_seletion;
                } else {
                    return R.drawable.tab_home_seletion_off;
                }
            case 1:
                if (isSelected) {
                    return R.drawable.tab_mypage_seletion;
                } else {
                    return R.drawable.tab_mypage_seletion_off;
                }
//            case 2:
//                if (isSelected) {
//                    return R.drawable.tab_notification_seletion;
//                } else {
//                    return R.drawable.tab_notification_seletion_off;
//                }
            case 2:
                if (isSelected) {
                    return R.drawable.tab_setting_seletion;
                } else {
                    return R.drawable.tab_setting_seletion_off;
                }
        }
        return R.drawable.tab_home_seletion;
    }
    */
public void invalidateTabIcon() {
    int selectedPosition = tabLayout.getSelectedTabPosition();
    for (int i = 0; i < tabLayout.getTabCount(); i++) {
        tabLayout.getTabAt(i).setText(getTabText(i, i == selectedPosition));
    }
}

    private String getTabText(int position, boolean isSelected) {
        switch (position) {
            case 0:
                if (isSelected) {
                    return "정보";
                } else {
                    return "정보";
                }
            case 1:
                if (isSelected) {
                    return "후기";
                } else {
                    return "후기";
                }
        }
        return "정보";
    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            switch (position) {
                case 0:
                    return new DetailsFragment();
                case 1:
                    return new ReviewFragment();
            }
            return null;
        }

        @Override
        public int getCount() {
            // Show 2 total pages.
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch(position){
                case 0:
                    return "정보";
                case 1:
                    return "후기";
                default:
                    return null;
            }
        }

        @Override
        public int getItemPosition(Object object) {
            return POSITION_NONE;
        }

    }

}
