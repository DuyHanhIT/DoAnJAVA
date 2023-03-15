package com.example.shopsneaker.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.example.shopsneaker.R;
import com.example.shopsneaker.retrofit.ApiService;
import com.example.shopsneaker.retrofit.RetrofitClient;
import com.example.shopsneaker.utils.Utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import io.paperdb.Paper;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class ChangePassActivity extends AppCompatActivity {

    EditText txtOldpass;
    EditText txtNewpass, txtConfirmPass;
    AppCompatButton btnConfirm, btnHuy;
    ApiService apiBanGiay;
    CompositeDisposable compositeDisposable = new CompositeDisposable();


    @Override
    protected  void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_changepass);
        initView();
        initControll();


        btnHuy.setOnClickListener(v -> {
            if (com.example.shopsneaker.utils.Utils.user_current.getRolesid()==1|| com.example.shopsneaker.utils.Utils.user_current.getRolesid()==2){
                Intent intent = new Intent(getApplicationContext(), AdminActivity.class);
                startActivity(intent);
                finish();
            }else {
                Intent intent = new Intent(getApplicationContext(), ProfileActivity.class);
                startActivity(intent);
                finish();
            }

        });
    }

    public static boolean isValidPassword(String password) {
        Matcher matcher = Pattern.compile("((?=.*[a-z])(?=.*\\d)(?=.*[A-Z])(?=.*[@#$%!]).{8,20})").matcher(password);
        return matcher.matches();
    }

    private void initControll() {
        btnConfirm.setOnClickListener(v -> ChangePass());
    }

    private void ChangePass() {
        String username = Utils.user_current.getUsername();
        String stroldpass = txtOldpass.getText().toString().trim();
        String strnewpass = txtNewpass.getText().toString().trim();
        String strconfirmpass = txtConfirmPass.getText().toString().trim();
        if(TextUtils.isEmpty(stroldpass)){
            Toast.makeText(getApplicationContext(),"Bạn chưa nhập mật khẩu cũ", Toast.LENGTH_LONG).show();
        }
        else if(TextUtils.isEmpty(strnewpass)){
            Toast.makeText(getApplicationContext(),"Bạn chưa nhập mật khẩu mới", Toast.LENGTH_LONG).show();
        }
        else if(TextUtils.isEmpty(strconfirmpass)){
            Toast.makeText(getApplicationContext(),"Bạn chưa xác nhận mật khẩu", Toast.LENGTH_LONG).show();
        }
        else if(!isValidPassword(strnewpass)){
            Toast.makeText(getApplicationContext(), "Mật khẩu phải có 8-20 kí tự gồm chữ hoa,chữ thường, số, kí tự đặc biệt", Toast.LENGTH_SHORT).show();
        }
        else {
            if(strnewpass.equals(strconfirmpass)){
                compositeDisposable.add(apiBanGiay.changePass(username,stroldpass,strnewpass).subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                userModel -> {
                                    if(userModel.isSuccess()){
                                        Paper.book().write("username",strnewpass);
                                        android.widget.Toast.makeText(this, "Đổi mật khẩu thành công", android.widget.Toast.LENGTH_SHORT).show();
                                        if (com.example.shopsneaker.utils.Utils.user_current.getRolesid()==1|| com.example.shopsneaker.utils.Utils.user_current.getRolesid()==2){
                                            Intent intent = new Intent(getApplicationContext(), AdminActivity.class);
                                            startActivity(intent);
                                            finish();
                                        }else {
                                            Intent intent = new Intent(getApplicationContext(), ProfileActivity.class);
                                            startActivity(intent);
                                            finish();
                                        }

                                    }else {
                                        Toast.makeText(getApplicationContext(),userModel.getMessage()+"", Toast.LENGTH_LONG).show();
                                    }
                                },
                                throwable -> {
                                    Toast.makeText(getApplicationContext(),throwable.getMessage()+"", Toast.LENGTH_LONG).show();
                                }
                        ));
            }else {
                Toast.makeText(getApplicationContext(),"Mật khẩu không khớp", Toast.LENGTH_LONG).show();
            }
        }
    }


    private void initView() {
        apiBanGiay = RetrofitClient.getInstance(Utils.BASE_URL).create(ApiService.class);
        txtOldpass = findViewById(R.id.txtoldpass);
        txtNewpass = findViewById(R.id.txtnewpass);
        txtConfirmPass= findViewById(R.id.txtconfirm);
        btnConfirm = findViewById(R.id.btnconfirm);
        btnHuy = findViewById(R.id.btnhuy);
    }
}
