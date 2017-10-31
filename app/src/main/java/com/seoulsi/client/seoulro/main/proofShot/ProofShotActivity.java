package com.seoulsi.client.seoulro.main.proofShot;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.seoulsi.client.seoulro.R;
import com.seoulsi.client.seoulro.application.ApplicationController;
import com.seoulsi.client.seoulro.mypage.MySeoulloDatas;
import com.seoulsi.client.seoulro.mypage.MyseoulloAdapter;
import com.seoulsi.client.seoulro.mypage.MyseoulloResult;
import com.seoulsi.client.seoulro.network.NetworkService;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProofShotActivity extends AppCompatActivity {
    private RecyclerView mrecyclerview;
    private ArrayList<ProofShotDatas> proofDatas;
    //    StaggeredGridLayoutManager mLayoutManager;
    private GridLayoutManager mLayoutManager;
    private ProofViewAdapter adapter;
    private NetworkService service;
    private int id;
    private boolean listIsEnd = false;
    private boolean isListViewAppending = false;
    private boolean isListExpandable = true;

    @BindView(R.id.btn_proofShot_cascel)
    Button btnProofShotCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_proof_shot);

        ButterKnife.bind(this);
        //서비스 객체 초기화
        service = ApplicationController.getInstance().getNetworkService();
        mrecyclerview = (RecyclerView)findViewById(R.id.recyclerview_proofshot);
        //각 item의 크기가 일정할 경우 고정
        mrecyclerview.setHasFixedSize(true);
        proofDatas = new ArrayList<ProofShotDatas>();
        adapter = new ProofViewAdapter(proofDatas, clickEvent);

        mLayoutManager = new GridLayoutManager(getApplicationContext(), 3); //설정에서 지정하는 상징의 크기에 따라 칼럼수변화
        mrecyclerview.setLayoutManager(mLayoutManager);


        mrecyclerview.setAdapter(adapter);
        id = Integer.MAX_VALUE;

        //뒤로가기 버튼
        btnProofShotCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Call<ProofShotResult> ProofData = service.getProofResult(id);
        ProofData.enqueue(new Callback<ProofShotResult>() {
            @Override
            public void onResponse(Call<ProofShotResult> call, Response<ProofShotResult> response) {
                if (response.isSuccessful()) {
                    ProofShotResult proofResult = response.body();
                        proofDatas.addAll(proofResult.result);
                        Log.i("data", proofResult.result.get(0).place_picture );
                        adapter.notifyDataSetChanged();

                }
            }

            @Override
            public void onFailure(Call<ProofShotResult> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "서비스 연결 문제", Toast.LENGTH_SHORT).show();
            }
        });


    }
    public View.OnClickListener clickEvent = new View.OnClickListener() {
        public void onClick(View v) {
            final int itemPosition = mrecyclerview.getChildPosition(v);
            //Toast.makeText(getApplicationContext(),itemPosition+"번 리스트 클릭!!",Toast.LENGTH_SHORT).show();
        }
    };

}
