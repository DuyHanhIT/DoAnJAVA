package com.example.shopsneaker.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.example.shopsneaker.R;
import com.example.shopsneaker.model.SizeManagment;
import com.example.shopsneaker.retrofit.ApiService;
import com.example.shopsneaker.retrofit.RetrofitClient;
import com.example.shopsneaker.utils.Utils;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class updateSizeActivity extends AppCompatActivity {
    Toolbar toolbarUpSize;
    TextView txtProductName;
    ImageView imgProduct;
    EditText edtS38,edtS39,edtS40,edtS41,edtS42,edtS43,edtS44,edtS45,edtS46,edtS47,edtS48;
    Button btnUpSize;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    ApiService apiBanGiay;
    public int sizeid = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_size);
        apiBanGiay = RetrofitClient.getInstance(Utils.BASE_URL).create(ApiService.class);
        Init();
        ActionBar();
        getInformation();
        EventClick();
    }

    private void EventClick() {
        btnUpSize.setOnClickListener(v -> {
            updateSize();
        });
    }

    private void updateSize() {
        String str_s38 = edtS38.getText().toString().trim();
        String str_s39 = edtS39.getText().toString().trim();
        String str_s40 = edtS40.getText().toString().trim();
        String str_s41 = edtS41.getText().toString().trim();
        String str_s42 = edtS42.getText().toString().trim();
        String str_s43 = edtS43.getText().toString().trim();
        String str_s44 = edtS44.getText().toString().trim();
        String str_s45 = edtS45.getText().toString().trim();
        String str_s46 = edtS46.getText().toString().trim();
        String str_s47 = edtS47.getText().toString().trim();
        String str_s48 = edtS48.getText().toString().trim();
        compositeDisposable.add(apiBanGiay.updateSize(sizeid,str_s38,str_s39,str_s40,str_s41,str_s42,str_s43,str_s44,str_s45,str_s46,str_s47,str_s48)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        addModel -> {
                            Toast.makeText(this, "Thành Công", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplicationContext(), SizeManagmentActivity.class);
                            startActivity(intent);
                            finish();
                            },
                        throwable -> {
                        }));
    }

    private void getInformation() {
        String name = "";
        String images = "";
        int s38 = 0;
        int s39 = 0;
        int s40 = 0;
        int s41 = 0;
        int s42 = 0;
        int s43 = 0;
        int s44 = 0;
        int s45 = 0;
        int s46 = 0;
        int s47 = 0;
        int s48 = 0;
        SizeManagment sizeManagment = (SizeManagment) getIntent().getSerializableExtra("themsize");
        sizeid = sizeManagment.getSizeid();
        name = sizeManagment.getName();
        images = sizeManagment.getImages();
        s38 = sizeManagment.getS38();
        s39 = sizeManagment.getS39();
        s40 = sizeManagment.getS40();
        s41 = sizeManagment.getS41();
        s42 = sizeManagment.getS42();
        s43 = sizeManagment.getS43();
        s44 = sizeManagment.getS44();
        s45 = sizeManagment.getS45();
        s46 = sizeManagment.getS46();
        s47 = sizeManagment.getS47();
        s48 = sizeManagment.getS48();

        txtProductName.setText(name);
        if (images.contains("http")){
            Glide.with(getApplicationContext()).load(images).into(imgProduct);
        }else {
            String hinh = Utils.BASE_URL+"images/" + images;
            Glide.with(getApplicationContext()).load(hinh).into(imgProduct);
        }
        edtS38.setText(s38+"");
        edtS39.setText(s39+"");
        edtS40.setText(s40+"");
        edtS41.setText(s41+"");
        edtS42.setText(s42+"");
        edtS43.setText(s43+"");
        edtS44.setText(s44+"");
        edtS45.setText(s45+"");
        edtS46.setText(s46+"");
        edtS47.setText(s47+"");
        edtS48.setText(s48+"");
    }

    private void ActionBar() {
        setSupportActionBar(toolbarUpSize);
        androidx.appcompat.app.ActionBar actionBar=getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        toolbarUpSize.setNavigationOnClickListener(v -> finish());
    }

    private void Init() {
        toolbarUpSize = findViewById(R.id.toolbarAddSize);
        txtProductName = findViewById(R.id.txtname);
        imgProduct = findViewById(R.id.imageShoes2);
        edtS38 = findViewById(R.id.edtS38);
        edtS39 = findViewById(R.id.edtS39);
        edtS40 = findViewById(R.id.edtS40);
        edtS41 = findViewById(R.id.edtS41);
        edtS42 = findViewById(R.id.edtS42);
        edtS43 = findViewById(R.id.edtS43);
        edtS44 = findViewById(R.id.edtS44);
        edtS45 = findViewById(R.id.edtS45);
        edtS46 = findViewById(R.id.edtS46);
        edtS47 = findViewById(R.id.edtS47);
        edtS48 = findViewById(R.id.edtS48);
        btnUpSize = findViewById(R.id.btnUpdateSize);
    }
}