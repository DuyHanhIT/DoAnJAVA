package com.example.shopsneaker.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.shopsneaker.R;
import com.example.shopsneaker.retrofit.ApiService;
import com.example.shopsneaker.retrofit.RetrofitClient;
import com.example.shopsneaker.utils.Utils;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class AddInforActivity extends AppCompatActivity {

    EditText edtName, edtAddress, edtPhone;
    Button btnConfirm;
    ApiService apiBanGiay;
    String username;
    CompositeDisposable compositeDisposable = new CompositeDisposable();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information);
        initControll();
        initView();

    }

    private void initView() {
        btnConfirm.setOnClickListener(v -> ReGisterInfor());
    }

    private void ReGisterInfor() {
        String strname = edtName.getText().toString().trim();
        String straddress = edtAddress.getText().toString().trim();
        String strphone = edtPhone.getText().toString().trim();
        if(TextUtils.isEmpty(strname)){
            Toast.makeText(getApplicationContext(),"Bạn chưa nhập họ tên", Toast.LENGTH_LONG).show();
        }
        else if(TextUtils.isEmpty(strphone)){
            Toast.makeText(getApplicationContext(),"Bạn chưa nhập số điện thoại", Toast.LENGTH_LONG).show();
        }
        else if(TextUtils.isEmpty(straddress)){
            Toast.makeText(getApplicationContext(),"Bạn chưa nhập địa chỉ", Toast.LENGTH_LONG).show();
        }
        else {

                compositeDisposable.add(apiBanGiay.AddInfor(username,strname,straddress,strphone).subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                userModel -> {
                                    if(userModel.isSuccess()){
                                        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                                        startActivity(intent);
                                        finish();
                                    }else {
                                        Toast.makeText(getApplicationContext(),"Thất bại", Toast.LENGTH_LONG).show();
                                    }
                                },
                                throwable -> {
                                    Toast.makeText(getApplicationContext(),"Không kết nối được server", Toast.LENGTH_LONG).show();
                                }
                        ));
        }
    }

    private void initControll() {
        username = getIntent().getStringExtra("user");
        apiBanGiay = RetrofitClient.getInstance(Utils.BASE_URL).create(ApiService.class);
        edtName = findViewById(R.id.edtname);
        edtAddress = findViewById(R.id.edtaddress);
        edtPhone = findViewById(R.id.edtphone);
        btnConfirm = findViewById(R.id.buttonxacnhan);
    }

    @Override
    protected void onDestroy(){
        compositeDisposable.clear();
        super.onDestroy();
    }
}
