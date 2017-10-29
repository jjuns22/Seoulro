package com.seoulsi.client.seoulro.search;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Service;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Camera;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;
import com.seoulsi.client.seoulro.R;
import com.seoulsi.client.seoulro.application.ApplicationController;
import com.seoulsi.client.seoulro.login.LoginUserInfo;
import com.seoulsi.client.seoulro.network.NetworkService;
import com.seoulsi.client.seoulro.search.Fragment.ReviewFragment;
import com.seoulsi.client.seoulro.search.recyclerview.ReviewRecyclerAdapter;
import com.seoulsi.client.seoulro.search.review.ReviewInfo;
import com.seoulsi.client.seoulro.search.review.UpdateReviewInfo;

import org.w3c.dom.Text;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.logging.Handler;
import java.util.logging.LogRecord;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WriteReviewActivity extends AppCompatActivity {
    private final String TAG = "WriteReviewActivity";
    private final int TAKE_CAMERA = 0;
    private final int TAKE_GALLERY = 1;
    private ArrayList<ReviewInfo> itemDataReview = new ArrayList<>();
    private ReviewRecyclerAdapter adapter;
    private String placeName;
    private String imgUrl = "";
    private Uri imgUri;
    private NetworkService service;
    private String placeId;
    private MultipartBody.Part placeimage;
    private File photo;
    private RequestBody photoBody;
    private String token;


    @BindView(R.id.btn_write_review_image_upload)
    Button btnWriteReviewImageUpload;
    @Nullable
    @BindView(R.id.imageview_write_review_image)
    ImageView imageViewWriteReviewImg;
    @BindView(R.id.btn_write_review_enrollment)
    Button btnWriteReviewEnrollment;
    @BindView(R.id.edittext_write_review_title)
    EditText editTextViewReviewTitle;
    @BindView(R.id.edittext_write_review_content)
    EditText editTextReviewContent;
    @BindView(R.id.textview_write_review_photo_upload)
    TextView textViewWriteReviewPhotoUpload;
    @BindView(R.id.btn_write_review_picture_cancel)
    Button btnWriteReviewPictureCancel;
    @BindView(R.id.linearLayout_write_review_photo_upload)
    LinearLayout linearLayoutWriteReviewPhotoUpload;
    @BindView(R.id.textview_write_review_modify_photo)
    TextView textViewWriteReviewModifyPhoto;
    @BindView(R.id.textview_write_review_writer)
    TextView textViewWriteReviewWriter;
    @BindView(R.id.textview_write_review_placename)
    TextView textViewWriteReviewPlaceName;
    @BindView(R.id.btn_toolBar_write_review_cancel)
    Button btnToolBarWriteReviewCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_review);

        ButterKnife.bind(this);

        Intent getData = getIntent();
        placeName = getData.getStringExtra("placename");
        placeId = String.valueOf(getData.getIntExtra("placeid", -1));
        //서비스 객체 초기화
        service = ApplicationController.getInstance().getNetworkService();
        textViewWriteReviewWriter.setText(LoginUserInfo.getInstance().getUserInfo().nickname);
        textViewWriteReviewPlaceName.setText(placeName);
        btnWriteReviewImageUpload.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                int permissionCheck = ContextCompat.checkSelfPermission(WriteReviewActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE);

                if (permissionCheck == PackageManager.PERMISSION_DENIED) {
                    //권한없음
                    ActivityCompat.requestPermissions(WriteReviewActivity.this,
                            new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                            TAKE_GALLERY);
                } else {
                    // 권한 있음
                    Intent intent = new Intent();
                    intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
                    intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    startActivityForResult(intent, TAKE_GALLERY);
                }
            }
        });
        //사진 삭제 버튼 클릭시
        btnWriteReviewPictureCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                linearLayoutWriteReviewPhotoUpload.setVisibility(View.VISIBLE);    // "사진을 첨부하시겠습니까?" 버튼,텍스트 보임
                imageViewWriteReviewImg.setVisibility(View.GONE);            //이미지사진 숨김
                btnWriteReviewPictureCancel.setVisibility(View.GONE);        //이미지사진 삭제버튼(x) 숨김
                textViewWriteReviewModifyPhoto.setVisibility(View.GONE);     //사진편집 텍스트뷰 숨김
            }
        });
        //사진 편집 텍스트 클릭시
        textViewWriteReviewModifyPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                modifyPhotoDiologShow();
            }
        });
        //저장하기 버튼 클릭시
        btnWriteReviewEnrollment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reviwDialogShow();
            }
        });

        //x버튼 클릭시
        btnToolBarWriteReviewCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                writeReviewCancelDialog();
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case TAKE_GALLERY:
                //Log.d(TAG, "requestCode == TAKE_GALLERY");
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // 권한 허가
                    // 해당 권한을 사용해서 작업을 진행할 수 있습니다
                } else {
                    // 권한 거부
                    // 사용자가 해당권한을 거부했을때 해주어야 할 동작을 수행합니다
                    //Log.d(TAG, "requestCode == TAKE_GALLERY, 거부");
                    new TedPermission(WriteReviewActivity.this)
                            .setPermissionListener(permissionlistener)
                            .setDeniedMessage("[설정] > [권한] 에서 권한을 다시 설정할 수 있습니다.")
                            .setGotoSettingButton(true)
                            .setPermissions(Manifest.permission.READ_CONTACTS)
                            .check();
                }
                return;
        }
    }

    PermissionListener permissionlistener = new PermissionListener() {
        @Override
        public void onPermissionGranted() {
            Toast.makeText(WriteReviewActivity.this, "권한 허가", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onPermissionDenied(ArrayList<String> deniedPermissions) {
            Toast.makeText(WriteReviewActivity.this, "권한 거부\n" + deniedPermissions.toString(), Toast.LENGTH_SHORT).show();
        }
    };


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == TAKE_GALLERY) {
                try {
                    //이미지 데이터를 비트맵으로 받아온다.
                    //Bitmap image_bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), data.getData());

                    //imageViewProfile에 이미지 세팅
                    //imageViewWriteReviewImg.setImageBitmap(image_bitmap);
                    Glide.with(getBaseContext())
                            .load(data.getData())
                            .into(imageViewWriteReviewImg);
                    imageViewWriteReviewImg.setScaleType(ImageView.ScaleType.FIT_XY);
                    linearLayoutWriteReviewPhotoUpload.setVisibility(View.GONE);    // "사진을 첨부하시겠습니까?" 버튼,텍스트 가림
                    imageViewWriteReviewImg.setVisibility(View.VISIBLE);            //이미지사진 보임
                    btnWriteReviewPictureCancel.setVisibility(View.VISIBLE);        //이미지사진 삭제버튼(x) 보임
                    textViewWriteReviewModifyPhoto.setVisibility(View.VISIBLE);     //사진편집 텍스트뷰 보임
                    this.imgUri = data.getData();
                    imgUrl = this.imgUri.getPath();

                    if (imgUrl.equals("") || imgUrl == null) {
                        photo = null;
                    } else {

                        /**
                         * 비트맵 관련한 자료는 아래의 링크에서 참고
                         * http://mainia.tistory.com/468
                         */

                        BitmapFactory.Options options = new BitmapFactory.Options();
                        options.inSampleSize = 4; //얼마나 줄일지 설정하는 옵션 4--> 1/4로 줄이겠다

                        InputStream in = null;
                        try {
                            in = getContentResolver().openInputStream(imgUri);
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }

                        Bitmap bitmap = BitmapFactory.decodeStream(in, null, options); // InputStream 으로부터 Bitmap 을 만들어 준다.
                        if (bitmap.getHeight() < bitmap.getWidth()) {
                            bitmap = imgRotate(bitmap);
                        }

                        ByteArrayOutputStream baos = new ByteArrayOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 20, baos);
                        // 압축 옵션( JPEG, PNG ) , 품질 설정 ( 0 - 100까지의 int형 ), 압축된 바이트 배열을 담을 스트림
                        photoBody = RequestBody.create(MediaType.parse("image/png"), baos.toByteArray());
                        photo = new File(imgUrl);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (requestCode == TAKE_CAMERA) {
                Uri currImageURI = data.getData();
                Log.d(TAG, "CAMERA : " + getRealPathFromURI(currImageURI));
            }
        }
    }

    //    public static int getImageOrientation(String path){
//
//        int rotation =0;
//        try {
//            ExifInterface exif = new ExifInterface(path);
//            int rot= exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
//
//            if(rot == ExifInterface.ORIENTATION_ROTATE_90){
//                rotation = 90;
//            }else if(rot == ExifInterface.ORIENTATION_ROTATE_180){
//                rotation = 180;
//            }else if(rot == ExifInterface.ORIENTATION_ROTATE_270){
//                rotation = 270;
//            }else{
//                rotation = 0;
//            }
//        } catch (IOException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//
//        return rotation;
//    }
    //  화면 비율 때문에 회전된 이미지 바로잡기
    private Bitmap imgRotate(Bitmap bmp) {
        int width = bmp.getWidth();
        int height = bmp.getHeight();

        Matrix matrix = new Matrix();
        matrix.postRotate(90);

        Bitmap resizedBitmap = Bitmap.createBitmap(bmp, 0, 0, width, height, matrix, true);
        bmp.recycle();

        return resizedBitmap;
    }

//    public static Bitmap imgRotate(Bitmap bmp, int orientation){
//        int width = bmp.getWidth();
//        int height = bmp.getHeight();
//
//        Matrix matrix = new Matrix();
//        matrix.postRotate(orientation);
//
//        Bitmap resizedBitmap = Bitmap.createBitmap(bmp, 0, 0, width, height, matrix, true);
//        bmp.recycle();
//
//        return resizedBitmap;
//    }

    public String getRealPathFromURI(Uri contentUri) {
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = managedQuery(contentUri, proj, null, null, null);//에러
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);

    }

    void reviwDialogShow() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("저장");
        builder.setMessage("후기를 저장하시겠습니까?");
        builder.setPositiveButton("예",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        //Toast.makeText(getApplicationContext(),"예를 선택했습니다.",Toast.LENGTH_LONG).show();

                        // MultipartBody.Part

                        if (photo != null) {
                            placeimage = MultipartBody.Part.createFormData("placeimage", photo.getName(), photoBody);
                        }
                        token = LoginUserInfo.getInstance().getUserInfo().token;
                        RequestBody title = RequestBody.create(MediaType.parse("multipart/form-data"), editTextViewReviewTitle.getText().toString());
                        RequestBody content = RequestBody.create(MediaType.parse("multipart/form-data"), editTextReviewContent.getText().toString());
                        RequestBody placeid = RequestBody.create(MediaType.parse("multipart/form-data"), placeId);


                        Call<UploadReviewResult> uploadReview = service.uploadReview(placeimage, token, title, content, placeid);
                        uploadReview.enqueue(new Callback<UploadReviewResult>() {
                            @Override
                            public void onResponse(Call<UploadReviewResult> call, Response<UploadReviewResult> response) {
                                Log.d(TAG, "통신 전");
                                if (editTextViewReviewTitle.getText().toString().equals("")) {
                                    Toast.makeText(getBaseContext(), "제목을 입력해주세요.", Toast.LENGTH_SHORT).show();
                                } else if (editTextReviewContent.getText().toString().equals("")) {
                                    Toast.makeText(getBaseContext(), "내용을 입력해주세요.", Toast.LENGTH_SHORT).show();
                                } else {
                                    if (response.isSuccessful()) {
                                        Log.d(TAG, "통신 후");
                                        if (response.body().msg.equals("6")) {
                                            Log.d(TAG, "성공");
                                            Toast.makeText(getBaseContext(), "성공", Toast.LENGTH_SHORT).show();
                                            Intent returnIntent = new Intent();
                                            //Log.i(TAG,"받은최신글 : "+ response.body().result.get(0).title);
                                            itemDataReview.addAll(response.body().result);
                                            returnIntent.putParcelableArrayListExtra("itemDataReview", itemDataReview);
                                            // Log.i(TAG,"최신글 : "+ itemDataReview.get(0).title);
                                            setResult(RESULT_OK, returnIntent);
                                            //ReviewFragment.flag = true;
                                            finish();
                                        }
                                    } else {
                                        Log.d(TAG, "실패");
                                        Toast.makeText(getBaseContext(), "실패", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }

                            @Override
                            public void onFailure(Call<UploadReviewResult> call, Throwable t) {
                                Toast.makeText(getBaseContext(), "onFailure", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
        builder.setNegativeButton("아니오",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        //Toast.makeText(getApplicationContext(),"아니오를 선택했습니다.",Toast.LENGTH_LONG).show();
                        dialog.dismiss();
                    }
                });
        builder.show();
    }

    void modifyPhotoDiologShow() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        // builder.setTitle("AlertDialog Title");
        builder.setMessage("사진을 편집하시겠습니까?");
        builder.setPositiveButton("예",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        //Toast.makeText(getApplicationContext(),"예를 선택했습니다.",Toast.LENGTH_LONG).show();
                        int permissionCheck = ContextCompat.checkSelfPermission(WriteReviewActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE);
                        if (permissionCheck == PackageManager.PERMISSION_DENIED) {
                            //권한없음
                            ActivityCompat.requestPermissions(WriteReviewActivity.this,
                                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                                    TAKE_GALLERY);
                        } else {
                            // 권한 있음
                            Intent intent = new Intent();
                            intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
                            intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                            intent.setAction(Intent.ACTION_GET_CONTENT);
                            startActivityForResult(intent, TAKE_GALLERY);
                        }
                    }
                });
        builder.setNegativeButton("아니오",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        //Toast.makeText(getApplicationContext(),"아니오를 선택했습니다.",Toast.LENGTH_LONG).show();
                        dialog.dismiss();
                    }
                });
        builder.show();
    }

    void writeReviewCancelDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("취소");
        builder.setMessage("취소하시겠습니까?");
        builder.setPositiveButton("예",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        //Toast.makeText(getApplicationContext(),"예를 선택했습니다.",Toast.LENGTH_LONG).show();
                        finish();
                    }
                });
        builder.setNegativeButton("아니오",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        //Toast.makeText(getApplicationContext(),"아니오를 선택했습니다.",Toast.LENGTH_LONG).show();
                        dialog.dismiss();
                    }
                });
        builder.show();
    }
}
