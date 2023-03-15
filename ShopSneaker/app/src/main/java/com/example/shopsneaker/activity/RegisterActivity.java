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

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class RegisterActivity extends AppCompatActivity {

    EditText edtUserName;
    EditText edtPassWord, repass;
    Button btnRegister;
    ApiService apiBanGiay;
    CompositeDisposable compositeDisposable = new CompositeDisposable();

    @Override
    protected  void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_register);
        initView();
        initControll();
    }


    private void initControll() {
        btnRegister.setOnClickListener(v -> ReGisterAccount());
    }

    private boolean isValidEmailId(String email){

        return Pattern.compile("^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@"
                + "((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
                + "([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|"
                + "([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$").matcher(email).matches();
    }

    public static boolean isValidPassword(String password) {
        Matcher matcher = Pattern.compile("((?=.*[a-z])(?=.*\\d)(?=.*[A-Z])(?=.*[@#$%!]).{8,20})").matcher(password);
        return matcher.matches();
    }

    private void ReGisterAccount() {
        String struser = edtUserName.getText().toString().trim();
        String strpass = edtPassWord.getText().toString().trim();
        String strrepass = repass.getText().toString().trim();
        if(TextUtils.isEmpty(struser)){
            Toast.makeText(getApplicationContext(),"Bạn chưa nhập Email", Toast.LENGTH_LONG).show();
        }
        else if(TextUtils.isEmpty(strpass)){
            Toast.makeText(getApplicationContext(),"Bạn chưa nhập mật khẩu", Toast.LENGTH_LONG).show();
        }
        else if(TextUtils.isEmpty(strrepass)){
            Toast.makeText(getApplicationContext(),"Bạn chưa xác nhận mật khẩu", Toast.LENGTH_LONG).show();
        }
        else if(!isValidEmailId(struser)){
            Toast.makeText(getApplicationContext(), "Email không hợp lệ!", Toast.LENGTH_SHORT).show();
        }
        else if(!isValidPassword(strpass)){
            Toast.makeText(getApplicationContext(), "Mật khẩu phải có 8-20 kí tự gồm chữ hoa,chữ thường, số, kí tự đặc biệt", Toast.LENGTH_SHORT).show();
        }
        else {
            if(strpass.equals(strrepass)){
                String pass = Utils.getMD5(strpass);
                compositeDisposable.add(apiBanGiay.ReGister(struser,pass).subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                userModel -> {
                                    if(userModel.isSuccess()){
                                        Intent intent = new Intent(getApplicationContext(), AddInforActivity.class);
                                        intent.putExtra("user", struser);
                                        startActivity(intent);
                                        finish();
                                    }else {
                                        Toast.makeText(getApplicationContext(),userModel.getMessage(), Toast.LENGTH_LONG).show();
                                    }
                                },
                                throwable -> {
                                    Toast.makeText(getApplicationContext(),throwable.getMessage(), Toast.LENGTH_LONG).show();
                                }
                        ));
            }else {
                Toast.makeText(getApplicationContext(),"Mật khẩu không khớp", Toast.LENGTH_LONG).show();
            }
        }
    }

    private void initView() {
        apiBanGiay = RetrofitClient.getInstance(Utils.BASE_URL).create(ApiService.class);
        edtUserName = findViewById(R.id.txtUserDK);
        edtPassWord = findViewById(R.id.txtPassDK);
        repass = findViewById(R.id.txtrePassDK);
        btnRegister = findViewById(R.id.btnDangkiDK);
    }

    @Override
    protected void onDestroy(){
        compositeDisposable.clear();
        super.onDestroy();
    }


}