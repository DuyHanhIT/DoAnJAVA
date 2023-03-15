package com.example.shopsneaker.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.shopsneaker.R;
import com.example.shopsneaker.databinding.ActivityAddProductBinding;
import com.example.shopsneaker.model.Shoes;
import com.example.shopsneaker.retrofit.ApiService;
import com.example.shopsneaker.retrofit.RetrofitClient;
import com.example.shopsneaker.utils.Utils;
import com.github.dhaval2404.imagepicker.ImagePicker;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class AddProduct extends AppCompatActivity {
    public List<com.example.shopsneaker.model.Brand> brandList;
    Toolbar toolbar;
    Spinner spinnerhang, spinnernew, spinneractive;
    int loai;
    int spmoi;
    int spexist;
    int index=0;
    String Title;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    ApiService apiBanGiay;
    ActivityAddProductBinding binding;
    //android.net.Uri mediaPath;
   String mediaPath;
    Shoes shoesUpDe;
    boolean flag = false;
    String UrlShoes;

    com.example.shopsneaker.adapter.Spinner_Brand_Adapter adapter1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddProductBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        apiBanGiay = RetrofitClient.getInstance(Utils.BASE_URL).create(ApiService.class);
        Init();

        actionToolbar();
        InitData();
        Intent intent = getIntent();
        shoesUpDe = (Shoes) intent.getSerializableExtra("sua");
        if(shoesUpDe == null){
            //them
            flag = false;
        }else {
            //sua
            flag = true;
            binding.btnAdd.setText("Sửa sản phẩm");
            binding.toolbarAdd.setTitle("Sửa sản phẩm");
            //show data
            binding.tensp.setText(shoesUpDe.getShoeName());
            binding.gia.setText(shoesUpDe.getPrice()+"");
            binding.mota.setText(shoesUpDe.getDescription());
            binding.spinnerMoi.setSelection(shoesUpDe.getShoesNew());


            binding.anh.setText(shoesUpDe.getImages());
            UrlShoes= shoesUpDe.getImages();
            com.bumptech.glide.Glide.with(getApplicationContext()).load(shoesUpDe.getImages()).into(binding.imgShoes);

        }
    }

    private void actionToolbar() {
        setSupportActionBar(toolbar);
        androidx.appcompat.app.ActionBar actionBar=getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(v -> finish());
    }

    private void InitData() {
        List<String> exist = new ArrayList<>();
        exist.add("Thịnh hành");
        exist.add("Ngưng bán");
        ArrayAdapter<String> adapteractive = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item,exist);
        spinneractive.setAdapter(adapteractive);
        spinneractive.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                spexist = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        List<String> moi = new ArrayList<>();
        moi.add("mới");
        moi.add("cũ");
        ArrayAdapter<String> adapternew = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item,moi);
        spinnernew.setAdapter(adapternew);
        spinnernew.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                spmoi = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


//        List<String> hang = new ArrayList<>();
//        hang.add("Vui lòng chọn hãng");
//        hang.add("Nike");
//        hang.add("Adidas");
//        hang.add("Ananas");
//        hang.add("Puma");
//        hang.add("vans");
//        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item,hang);
        brandList= new java.util.ArrayList<>();
        List<com.example.shopsneaker.model.Brand> b;

        compositeDisposable.add(apiBanGiay.getBrand()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        brandModel -> {
                            if(brandModel.isSuccess()){
                                brandList = brandModel.getResult();
                                adapter1= new com.example.shopsneaker.adapter.Spinner_Brand_Adapter(this, android.R.layout.simple_spinner_dropdown_item,brandList);
                                spinnerhang.setAdapter(adapter1);
                                int brandId = getIntent().getIntExtra("brandId",1);

                                for (int i=0;i< brandList.size();i++){
                                    if (brandList.get(i).getBrandid()== brandId){
                                        index=i;
                                        break;
                                    }
                                }
                                binding.spinnerHang.setSelection(index);



                                Toast.makeText(getApplicationContext(), brandModel.getMessage(), Toast.LENGTH_LONG).show();

                            }
                        },
                        throwable -> {
                            Toast.makeText(getApplicationContext(), "Khong ket noi duoc voi server", Toast.LENGTH_LONG).show();
                        }
                ));


        spinnerhang.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                loai = brandList.get(position).getBrandid();
                Title = brandList.get(position).getBrandname().trim();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        binding.btnAdd.setOnClickListener(v -> {
            if(flag == false){
                themSp();
            }else {
                suaSp();
            }
        });
        binding.imgcamera.setOnClickListener(v -> ImagePicker.with(AddProduct.this)
                .crop()
                .compress(1024)
                .maxResultSize(1080,1080)
                .start());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //mediaPath = data.getData();
        mediaPath = data.getDataString();
        if(mediaPath==null){

        }else{
            //uploadImg();
            uploadMultipleFiles();
            Log.d("log","onActivityResult: "+ mediaPath);
        }
    }




    private void suaSp() {
        String str_ten = binding.tensp.getText().toString().trim();
        Integer str_gia = Integer.parseInt(binding.gia.getText().toString().trim());
        String str_anh = binding.anh.getText().toString().trim();
        String str_mota = binding.mota.getText().toString().trim();
        if(TextUtils.isEmpty(str_ten) || TextUtils.isEmpty(str_gia.toString()) || TextUtils.isEmpty(str_anh) || loai == 0){
            Toast.makeText(getApplicationContext(),"Nhập đầy đủ thông tin",Toast.LENGTH_LONG).show();
        }else {
            compositeDisposable.add(apiBanGiay.updateSp((loai),shoesUpDe.getShoeId(),str_ten,str_anh,str_gia,str_mota,(spmoi),(spexist))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            addModel -> {
                                Toast.makeText(this, "Thành Công", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getApplicationContext(), ProductManagement.class);
                                intent.putExtra("brandId",loai);
                                intent.putExtra("Title",Title);
                                startActivity(intent);

                                finish();

                            },
                            throwable -> {
                                //Toast.makeText(this, "Thành Công", Toast.LENGTH_SHORT).show();
                            }
                    ));
        }
    }

    private void themSp() {
        String str_ten = binding.tensp.getText().toString().trim();
        Integer str_gia = Integer.parseInt(binding.gia.getText().toString().trim());
        String str_anh = binding.anh.getText().toString().trim();
        String str_mota = binding.mota.getText().toString().trim();
        if(TextUtils.isEmpty(str_ten) || TextUtils.isEmpty(str_gia.toString()) || TextUtils.isEmpty(str_anh) || loai == 0){
            Toast.makeText(getApplicationContext(),"Nhập đầy đủ thông tin",Toast.LENGTH_LONG).show();
        }else {
            compositeDisposable.add(apiBanGiay.insertSp((loai),str_ten,str_anh,str_gia,str_mota,(spmoi),(spexist))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            addModel -> {
                                Toast.makeText(this, "Thành Công", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getApplicationContext(), ProductManagement.class);
                                intent.putExtra("brandId",loai);
                                intent.putExtra("Title",Title);

                                startActivity(intent);
                                finish();

                            },
                            throwable -> {

                            }
                    ));
        }
    }
    private String getPath(Uri uri){
        String result;
        Cursor cursor = getContentResolver().query(uri,null,null,null,null);
        if(cursor == null){
            result = uri.getPath();
        }else{
            cursor.moveToFirst();
            int index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            result = cursor.getString(index);
            cursor.close();
        }
        return result;
    }


    private void uploadMultipleFiles() {

       try {
           Uri uri = Uri.parse(mediaPath);
           com.cloudinary.android.MediaManager.get().upload(uri).option("folder","ShoesShop/Shoes/").callback(new com.cloudinary.android.callback.UploadCallback() {
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
                   binding.anh.setText(resultData.get("url").toString());
                   com.bumptech.glide.Glide.with(getApplicationContext()).load(resultData.get("url").toString()).into(binding.imgShoes);
                   android.widget.Toast.makeText(getApplicationContext(), "Upload ảnh thành công", android.widget.Toast.LENGTH_SHORT).show();

               }

               @Override
               public void onError(String requestId, com.cloudinary.android.callback.ErrorInfo error) {
                    if(UrlShoes.length()>0){
                        com.bumptech.glide.Glide.with(getApplicationContext()).load(UrlShoes).into(binding.imgShoes);
                        binding.anh.setText(UrlShoes);
                    }
                   android.widget.Toast.makeText(getApplicationContext(), "Đã xảy ra lỗi, vui lòng thao tác lại", android.widget.Toast.LENGTH_SHORT).show();

               }

               @Override
               public void onReschedule(String requestId, com.cloudinary.android.callback.ErrorInfo error) {
                   if(UrlShoes.length()>0){
                       com.bumptech.glide.Glide.with(getApplicationContext()).load(UrlShoes).into(binding.imgShoes);
                       binding.anh.setText(UrlShoes);
                   }
                   android.widget.Toast.makeText(getApplicationContext(), "Đã xảy ra lỗi, vui lòng thao tác lại", android.widget.Toast.LENGTH_SHORT).show();

               }
           }).dispatch();
       }catch (Exception ex){
           Toast.makeText(this,"ex"+ex, android.widget.Toast.LENGTH_SHORT).show();
       }

        // Map is used to multipart the file using okhttp3.RequestBody
//        File file = new File(getPath(uri));
//
//        RequestBody requestBody1 = RequestBody.create(MediaType.parse("*/*"), file);
//
//        MultipartBody.Part fileToUpload1 = MultipartBody.Part.createFormData("file", file.getName(), requestBody1);
//        Call<AddModel> call = apiBanGiay.uploadFile(fileToUpload1);
//        call.enqueue(new Callback<AddModel>(){
//            @Override
//            public void onResponse(Call < AddModel > call, Response< AddModel > response) {
//                AddModel serverResponse = response.body();
//                if (serverResponse != null) {
//                    if (serverResponse.isSuccess()) {
//                        binding.anh.setText(serverResponse.getName());
//                    } else {
//                        Toast.makeText(getApplicationContext(), serverResponse.getMessage(), Toast.LENGTH_SHORT).show();
//                    }
//                } else {
//                    Log.v("Response", serverResponse.toString());
//                }
//
//            }
//            @Override
//            public void onFailure(Call< AddModel > call, Throwable t) {
//                Log.d("log",t.getMessage());
//            }
//        });

    }

    private void Init() {

        toolbar = findViewById(R.id.toolbarAdd);
        spinnerhang = findViewById(R.id.spinnerHang);
        spinnernew = findViewById(R.id.spinnerMoi);
        spinneractive = findViewById(R.id.spinnerActive);



    }
}