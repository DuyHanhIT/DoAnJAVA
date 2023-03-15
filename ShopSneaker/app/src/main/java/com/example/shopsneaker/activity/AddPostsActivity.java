package com.example.shopsneaker.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.shopsneaker.R;
import com.example.shopsneaker.model.Images;
import com.example.shopsneaker.model.Market;
import com.example.shopsneaker.retrofit.ApiService;
import com.example.shopsneaker.retrofit.RetrofitClient;
import com.example.shopsneaker.utils.Utils;
import com.google.gson.Gson;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import br.com.simplepass.loading_button_lib.customViews.CircularProgressButton;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class AddPostsActivity extends AppCompatActivity {
    Toolbar toolbar;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    ApiService apiBanGiay;
    Market marketUp;
    EditText productname, price,edtsize,edtdescription;
    boolean flag = false;
    int id = Utils.user_current.getAccountid();
    ImageView selectedImage;
    CircularProgressButton btnSubmit;
    List<Uri> files = new ArrayList<>();
    List<Images> listUrl= new java.util.ArrayList<>();
    List<MultipartBody.Part> list = new ArrayList<>();
    Uri Url;

    private LinearLayout parentLinearLayout;

    final private int REQUEST_CODE_ASK_PERMISSIONS = 123;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_posts);
        apiBanGiay = RetrofitClient.getInstance(Utils.BASE_URL).create(ApiService.class);
        Init();
        actionToolbar();
        Intent intent = getIntent();
        marketUp = (Market) intent.getSerializableExtra("sua");
        if(marketUp == null){
            //them
            flag = false;
        }else {
            //sua
            flag = true;
            btnSubmit.setText("Sửa sản phẩm");
            toolbar.setTitle("Sửa sản phẩm");
            //show data
            productname.setText(marketUp.getShoesname());
            price.setText(marketUp.getPrice()+"");
            edtdescription.setText(marketUp.getDescription());
            edtsize.setText(marketUp.getSize());
        }
        InitData();
    }
    private void InitData() {
        btnSubmit.setOnClickListener(v -> {
            if(flag == false){
                themBaiDang();
            }else {
                suaBaiDang();
            }
        });
        selectedImage.setOnClickListener(v -> addImage());
    }

    private void suaBaiDang() {
        String str_ten = productname.getText().toString().trim();
        String str_gia = price.getText().toString().trim();
        String str_size = edtsize.getText().toString().trim();
        String str_mota = edtdescription.getText().toString().trim();

        if(TextUtils.isEmpty(str_ten) || TextUtils.isEmpty(str_gia) || TextUtils.isEmpty(str_size) || TextUtils.isEmpty(str_mota)){
            Toast.makeText(getApplicationContext(),"Nhập đầy đủ thông tin",Toast.LENGTH_LONG).show();
        }else {

        }
    }


    private void themBaiDang() {
        String str_ten = productname.getText().toString().trim();
        String str_gia = price.getText().toString().trim();
        String str_size = edtsize.getText().toString().trim();
        String str_mota = edtdescription.getText().toString().trim();
        requestPermission();
        //uploadImages();
        Log.d("test", new Gson().toJson(list));

        if(TextUtils.isEmpty(str_ten) || TextUtils.isEmpty(str_gia)  || TextUtils.isEmpty(str_size) || TextUtils.isEmpty(str_mota)){
            Toast.makeText(getApplicationContext(),"Nhập đầy đủ thông tin",Toast.LENGTH_LONG).show();
        }else if (Integer.parseInt(str_gia)<0) {
            Toast.makeText(getApplicationContext(),"Giá phải lớn hơn 0",Toast.LENGTH_LONG).show();
        }else if (Integer.parseInt(str_size)<38||Integer.parseInt(str_size)>48) {
            Toast.makeText(getApplicationContext(),"Size phải trong khoảng [38,48]",Toast.LENGTH_LONG).show();
        }else {
                compositeDisposable.add(apiBanGiay.insertPosts(str_ten,str_gia,str_size,str_mota,id, new Gson().toJson(listUrl) ).subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                marketModel -> {
                                    if(marketModel.isSuccess()){

                                        Toast.makeText(getApplicationContext(),"Thành công", Toast.LENGTH_LONG).show();
                                        finish();
                                    }
                                },
                                throwable -> {
                                    finish();
                                    //Toast.makeText(getApplicationContext(),"Không kết nối được server", Toast.LENGTH_LONG).show();
                                }
                        ));
            }

    }
    private void actionToolbar() {
        setSupportActionBar(toolbar);
        androidx.appcompat.app.ActionBar actionBar=getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(v -> finish());
    }

    private void Init() {
        btnSubmit = findViewById(R.id.submit_button);
        toolbar = findViewById(R.id.toolbarAddPosts);
        selectedImage = findViewById(R.id.iv_add_image);
        productname = findViewById(R.id.productname);
        price = findViewById(R.id.price);
        edtsize = findViewById(R.id.edtsize);
        edtdescription = findViewById(R.id.edtdescription);
        parentLinearLayout = findViewById(R.id.parent_linear_layout);


    }
    //===== add image in layout
    public void addImage() {
        LayoutInflater inflater=(LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View rowView=inflater.inflate(R.layout.image, null);
        // Add the new row before the add field button.
        parentLinearLayout.addView(rowView, parentLinearLayout.getChildCount() - 1);
        parentLinearLayout.isFocusable();

        selectedImage = rowView.findViewById(R.id.number_edit_text);
        selectImage(AddPostsActivity.this);
    }

    //===== select image
    private void selectImage(Context context) {
        final CharSequence[] options = {"Take Photo", "Choose from Gallery", "Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setCancelable(false);
        builder.setTitle("Choose a Media");

        builder.setItems(options, (dialog, item) -> {

            if (options[item].equals("Take Photo")) {
                Intent takePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(takePicture, 0);

            } else if (options[item].equals("Choose from Gallery")) {
                Intent pickPhoto = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(pickPhoto, 1);//one can be replaced with any action code

            } else if (options[item].equals("Cancel")) {
                dialog.dismiss();
            }
        });
        builder.show();
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_CANCELED) {
            switch (requestCode) {
                case 0:
                    if (resultCode == RESULT_OK && data != null) {

                        Bitmap img = (Bitmap) data.getExtras().get("data");
                        //selectedImage.setImageBitmap(img);
                        //Picasso.get().load(getImageUri(AddPostsActivity.this,img)).into(selectedImage);

                        //String imgPath = FileUtils.getPath(AddPostsActivity.this,getImageUri(AddPostsActivity.this,img));
                        Url  = getImageUri(AddPostsActivity.this,img);

                        //files.add(Uri.parse(imgPath));

                        uploadImages(Url);
                       // Log.e("image", imgPath);
                    }

                    break;
                case 1:
                    if (resultCode == RESULT_OK && data != null) {
                        Uri img = data.getData();
//                        Picasso.get().load(img).into(selectedImage);
//
//                        String imgPath = FileUtils.getPath(AddPostsActivity.this,img);
//
//                        files.add(Uri.parse(imgPath));
//                        Url= Uri.parse(imgPath);
                        uploadImages(img);
                        //Log.e("image", imgPath);

                    }
                    break;
            }


        }
    }

    private void uploadImages(Uri u) {
        try {

            com.cloudinary.android.MediaManager.get().upload(u).option("folder","ShoesShop/Post/").callback(new com.cloudinary.android.callback.UploadCallback() {
                @Override
                public void onStart(String requestId) {

                    //binding.anh.setText("Start");
                }

                @Override
                public void onProgress(String requestId, long bytes, long totalBytes) {
                    //binding.anh.setText("InProgess");
                }

                @Override
                public void onSuccess(String requestId, java.util.Map resultData) {
                    listUrl.add(new Images(0,0,resultData.get("url").toString()));
                    com.bumptech.glide.Glide.with(getApplicationContext()).load(resultData.get("url").toString()).into(selectedImage);
                    android.widget.Toast.makeText(getApplicationContext(), "Upload ảnh thành công", android.widget.Toast.LENGTH_SHORT).show();

                }

                @Override
                public void onError(String requestId, com.cloudinary.android.callback.ErrorInfo error) {

                    android.widget.Toast.makeText(getApplicationContext(), "Đã xảy ra lỗi, vui lòng thao tác lại", android.widget.Toast.LENGTH_SHORT).show();

                }

                @Override
                public void onReschedule(String requestId, com.cloudinary.android.callback.ErrorInfo error) {

                    android.widget.Toast.makeText(getApplicationContext(), "Đã xảy ra lỗi, vui lòng thao tác lại", android.widget.Toast.LENGTH_SHORT).show();

                }
            }).dispatch();
        }catch (Exception ex){
            Toast.makeText(this,"ex"+ex, android.widget.Toast.LENGTH_SHORT).show();
        }
    }

    //===== bitmap to Uri
    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "intuenty", null);
        Log.d("image uri",path);
        return Uri.parse(path);
    }

    //===== Upload files to server
    /*public void uploadImages(){

        btnSubmit.startAnimation();

        for (Uri uri:files) {

            Log.i("uris",uri.getPath());

            list.add(prepareFilePart("file", uri));
        }


        ApiBanGiay getResponse = AppConfig.getRetrofit().create(ApiBanGiay.class);


        Call<uploadModel> call = getResponse.uploadNewsFeedImages(list);
        call.enqueue(new Callback<uploadModel>() {
            @Override
            public void onResponse(Call<uploadModel> call, Response<uploadModel> response) {
                btnSubmit.revertAnimation();
                try {

                    uploadModel addMediaModel = response.body();
                    if(addMediaModel.getStatusCode().equals("200")){
                        Toast.makeText(AddPostsActivity.this, "Files uploaded successfuly", Toast.LENGTH_SHORT).show();
                    }

                    Log.e("main", "the status is ----> " + addMediaModel.getStatusCode());
                    Log.e("main", "the message is ----> " + addMediaModel.getStatusMessage());

                }
                catch (Exception e){
                    Log.d("Exception","|=>"+e.getMessage());
//
                }
            }

            @Override
            public void onFailure(Call<uploadModel> call, Throwable t) {
                btnSubmit.revertAnimation();
                Log.i("my",t.getMessage());
            }
        });
    }*/

    @NonNull
    private MultipartBody.Part prepareFilePart(String partName, Uri fileUri) {

        File file = new File(fileUri.getPath());
        Log.i("here is error",file.getAbsolutePath());
        // create RequestBody instance from file

        RequestBody requestFile =
                RequestBody.create(
                        MediaType.parse("image/*"),
                        file);

        // MultipartBody.Part is used to send also the actual file name
        return MultipartBody.Part.createFormData(partName, file.getName(), requestFile);


    }


    // this is all you need to grant your application external storage permision
    private void requestPermission(){
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(AddPostsActivity.this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},REQUEST_CODE_ASK_PERMISSIONS);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        switch (requestCode){
            case REQUEST_CODE_ASK_PERMISSIONS:
                if(grantResults[0]==PackageManager.PERMISSION_GRANTED){
                    addImage();
                }
                else {
                    Toast.makeText(AddPostsActivity.this, "Permission denied", Toast.LENGTH_SHORT).show();
                }
                break;

            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }

    }
}