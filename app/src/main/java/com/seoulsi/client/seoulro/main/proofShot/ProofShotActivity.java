package com.seoulsi.client.seoulro.main.proofShot;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.seoulsi.client.seoulro.R;
import com.seoulsi.client.seoulro.mypage.MySeoulloDatas;
import com.seoulsi.client.seoulro.mypage.MyseoulloAdapter;
import com.seoulsi.client.seoulro.mypage.MyseoulloResult;
import com.seoulsi.client.seoulro.network.NetworkService;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProofShotActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    ArrayList<ProofShotDatas> proofDatas;
    //    StaggeredGridLayoutManager mLayoutManager;
    GridLayoutManager mLayoutManager;
    ProofViewAdapter adapter;
    private NetworkService service;
    String token;
    int id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_proof_shot);

        recyclerView = (RecyclerView)findViewById(R.id.recyclerview_proofshot);
        //각 item의 크기가 일정할 경우 고정
        recyclerView.setHasFixedSize(true);
        proofDatas = new ArrayList<ProofShotDatas>();
        adapter = new ProofViewAdapter(proofDatas, clickEvent);
        mLayoutManager = new GridLayoutManager(getApplicationContext(), 3); //설정에서 지정하는 상징의 크기에 따라 칼럼수변화
        recyclerView.setLayoutManager(mLayoutManager);


        recyclerView.setAdapter(adapter);
        id = Integer.MAX_VALUE;
        Call<ProofShotResult> ProofData = service.getProofResult(id);
        ProofData.enqueue(new Callback<ProofShotResult>() {
            @Override
            public void onResponse(Call<ProofShotResult> call, Response<ProofShotResult> response) {
                if (response.isSuccessful()) {
                    ProofShotResult proofResult = response.body();
                        proofDatas.addAll(proofResult.result);
                        Log.i("data", proofResult.result.get(0).getimage_url() );
                        adapter.notifyDataSetChanged();

                }
            }

            @Override
            public void onFailure(Call<ProofShotResult> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "서비스 연결을 확인하세요.", Toast.LENGTH_SHORT).show();
            }
        });


    }
    public View.OnClickListener clickEvent = new View.OnClickListener() {
        public void onClick(View v) {
            final int itemPosition = recyclerView.getChildPosition(v);
            Toast.makeText(getApplicationContext(),itemPosition+"번 리스트 클릭!!",Toast.LENGTH_SHORT).show();

        }
    };

}
