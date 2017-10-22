package com.seoulsi.client.seoulro.search;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.seoulsi.client.seoulro.R;
import com.seoulsi.client.seoulro.network.NetworkService;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ReviewDetailsActivity extends AppCompatActivity {
    private final String TAG = "ReviewDetailsActivity";
    private String writer;
    private String title;
    private String content;
    private String placePicture;

    @BindView(R.id.textview_details_review_title)
    TextView textViewDetailsReviewTitle;
    @BindView(R.id.textview_details_review_content)
    TextView textViewDetailsReviewContent;
    @BindView(R.id.textview_details_review_writer)
    TextView textViewDetailsReviewWriter;
    @Nullable
    @BindView(R.id.imageview_details_review_image)
    ImageView imageViewDetailsReviewImage;
    @BindView(R.id.btn_toolBar_details_review_cancel)
    Button btnToolBarDetailsReviewCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_details);

        ButterKnife.bind(this);

        Intent intent = getIntent();
        writer = intent.getStringExtra("writer");
        title = intent.getStringExtra("title");
        content = intent.getStringExtra("content");
        placePicture = intent.getStringExtra("placePicture");

        if(imageViewDetailsReviewImage != null) {
            Glide.with(getBaseContext())
                    .load(placePicture)
                    .into(imageViewDetailsReviewImage);
        }
        textViewDetailsReviewWriter.setText(writer);
        textViewDetailsReviewTitle.setText(title);
        textViewDetailsReviewContent.setText(content);

        btnToolBarDetailsReviewCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
