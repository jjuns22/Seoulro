package com.seoulsi.client.seoulro.mypage.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.seoulsi.client.seoulro.R;
import com.seoulsi.client.seoulro.mypage.ItemDataMyReview;
import com.seoulsi.client.seoulro.mypage.MyReviewRecyclerAdapter;
import com.seoulsi.client.seoulro.mypage.MySeoulloDatas;
import com.seoulsi.client.seoulro.mypage.MyseoulloAdapter;

import java.util.ArrayList;

/**
 * Created by SanJuku on 2017-10-15.
 */

public class MySeoulroFragment extends Fragment {
    //    GridView gridView;
    RecyclerView recyclerView;
    ArrayList<MySeoulloDatas> myseoulloDatas;
    //    StaggeredGridLayoutManager mLayoutManager;
    GridLayoutManager mLayoutManager;
    MyseoulloAdapter adapter;

    private ArrayList<String> imgURLs = new ArrayList<String>();
    ImageView img_seoullo;

    public MySeoulroFragment()
    {
    }
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        LinearLayout layout = (LinearLayout) inflater.inflate(R.layout.fragment_myseoul, container, false);
        recyclerView = (RecyclerView) layout.findViewById(R.id.recyclerview_myseoul_mypage);
        //각 item의 크기가 일정할 경우 고정
        recyclerView.setHasFixedSize(true);
        myseoulloDatas = new ArrayList<MySeoulloDatas>();
        adapter = new MyseoulloAdapter(myseoulloDatas, clickEvent);
        mLayoutManager = new GridLayoutManager(getActivity(), 3); //설정에서 지정하는 상징의 크기에 따라 칼럼수변화
        recyclerView.setLayoutManager(mLayoutManager);


        recyclerView.setAdapter(adapter);


        //itemdatas = new ArrayList<ItemDataMyReview>();
        myseoulloDatas.add(new MySeoulloDatas(R.drawable.mypage_review_picture));
        myseoulloDatas.add(new MySeoulloDatas(R.drawable.mypage_tab_camera));
        myseoulloDatas.add(new MySeoulloDatas(R.drawable.mypage_profile));
        myseoulloDatas.add(new MySeoulloDatas(R.drawable.mypage_review_picture));
        myseoulloDatas.add(new MySeoulloDatas(R.drawable.mypage_review_picture));
        myseoulloDatas.add(new MySeoulloDatas(R.drawable.mypage_tab_camera));
        myseoulloDatas.add(new MySeoulloDatas(R.drawable.mypage_review_picture));
        myseoulloDatas.add(new MySeoulloDatas(R.drawable.mypage_tab_camera));
        myseoulloDatas.add(new MySeoulloDatas(R.drawable.mypage_profile));
        myseoulloDatas.add(new MySeoulloDatas(R.drawable.mypage_review_picture));




        adapter.notifyDataSetChanged(); //꼭해야함


        return layout;

    }
    public View.OnClickListener clickEvent = new View.OnClickListener() {
        public void onClick(View v) {
            final int itemPosition = recyclerView.getChildPosition(v);
            Toast.makeText(getContext(),itemPosition+"번 리스트 클릭!!",Toast.LENGTH_SHORT).show();

        }
    };

}

