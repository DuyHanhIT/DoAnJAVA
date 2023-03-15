package com.example.shopsneaker.activity;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.shopsneaker.R;
import com.example.shopsneaker.retrofit.ApiService;
import com.example.shopsneaker.retrofit.RetrofitClient;
import com.example.shopsneaker.utils.Utils;

import io.paperdb.Paper;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class LoginActivity extends AppCompatActivity {
    Button btnLogin;
    TextView btnRegister;
    EditText edtusername, edtpassword;
    boolean isLogin = false;
    ApiService apiBanGiay;
    CompositeDisposable compositeDisposable = new CompositeDisposable();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
        initControll();
    }

    private void initControll() {
        btnRegister.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
            startActivity(intent);
        });
        btnLogin.setOnClickListener(v -> {
            String struser = edtusername.getText().toString().trim();
            String strpass = Utils.getMD5( edtpassword.getText().toString().trim());
            if (TextUtils.isEmpty(struser)) {
                Toast.makeText(getApplicationContext(), "Bạn chưa nhập tên đang nhập", Toast.LENGTH_LONG).show();
            } else if (TextUtils.isEmpty(strpass)) {
                Toast.makeText(getApplicationContext(), "Bạn chưa nhập mật khẩu", Toast.LENGTH_LONG).show();
            } else if (TextUtils.isEmpty(strpass)) {
                Toast.makeText(getApplicationContext(), "Bạn chưa nhập mật khẩu", Toast.LENGTH_LONG).show();
            }
            else {
                //save data
                Paper.book().write("username", struser);
                Paper.book().write("password", strpass);
                Login(struser, strpass);
            }
        });
    }

    private void Login(String struser, String strpass) {
        compositeDisposable.add(apiBanGiay.LoGin(struser, strpass).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        userModel -> {
                            if (userModel.isSuccess()) {
                                isLogin = true;
                                Paper.book().write("isLogin",isLogin);
                                Utils.user_current = userModel.getResult().get(0);
                                Paper.book().write("user",userModel.getResult().get(0));
                                String s = "admin";
                                if (com.example.shopsneaker.utils.Utils.user_current.getEnabled()==0){
                                    androidx.appcompat.app.AlertDialog.Builder b = new androidx.appcompat.app.AlertDialog.Builder(this);
                                    //Thiết lập tiêu đề
                                    b.setTitle("Thông báo");
                                    b.setMessage("Tài khoản của bạn đã bị khóa, liên hệ shop để được hỗ trợ     SĐT: 0355787500");
                                    b.setPositiveButton("Liên hệ ngay", (dialog, id) -> {
                                        Paper.book().delete("user");
                                        isLogin = false;
                                        Utils.user_current.setUsername(null);
                                        Utils.user_current.setPassword(null);
                                        Intent intent = new Intent(getApplicationContext(), ContactActivity3.class);
                                        startActivity(intent);
                                       // finish();

                                    });
////Nút Cancel
                                    b.setNegativeButton("Không đồng ý", (dialog, id) -> {
                                        Paper.book().delete("user");
                                        isLogin = false;
                                        Utils.user_current.setUsername(null);
                                        Utils.user_current.setPassword(null);
                                        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                                        startActivity(intent);
                                        finish();
                                    });
//Tạo dialog
                                    androidx.appcompat.app.AlertDialog al = b.create();
//Hiển thị
                                    al.show();
                                    ///finish();
                                }else{
                                    if(s.equals(Utils.user_current.getUsername()) || Utils.user_current.getRolesid()==2){
                                        Toast.makeText(getApplicationContext(), "Welcome the king of Europe", Toast.LENGTH_LONG).show();
                                        Intent intent;
                                        intent = new Intent(getApplicationContext(), AdminActivity.class);
                                        startActivity(intent);
                                        finish();
                                    }
                                    else{
                                        Toast.makeText(getApplicationContext(), "Welcome the king of Europe", Toast.LENGTH_LONG).show();
                                        Intent intent;
                                        intent = new Intent(getApplicationContext(), MainActivity.class);
                                        startActivity(intent);
                                        finish();
                                    }
                                }


                            } else {
                                Toast.makeText(getApplicationContext(), "Tài khoản hoặc mật khẩu không đúng", Toast.LENGTH_LONG).show();
                            }
                        },
                        throwable -> {
                            Toast.makeText(getApplicationContext(), "Tài khoản hoặc mật khẩu không đúng", Toast.LENGTH_LONG).show();
                        }
                ));
    }

    private void initView() {
        Paper.init(this);
        apiBanGiay = RetrofitClient.getInstance(Utils.BASE_URL).create(ApiService.class);
        btnRegister = findViewById(R.id.btnDangki);
        btnLogin = findViewById(R.id.btnDangnhap);
        edtusername = findViewById(R.id.txtUsername);
        edtpassword = findViewById(R.id.txtPass);

        if (Paper.book().read("user") != null) {
            edtusername.setText(Paper.book().read("username"));
            edtpassword.setText(Paper.book().read("password"));
            if(Paper.book().read("isLogin")!=null){
                boolean flag = Paper.book().read("isLogin");
                if(flag){
                    new Handler().postDelayed(() -> {

                    },1000);

                }
            }

        }
    }

    @Override
    protected void onResume () {
        super.onResume();
        if (Utils.user_current.getUsername() != null && Utils.user_current.getPassword() != null ){
            edtusername.setText(Utils.user_current.getUsername());
            edtpassword.setText(Utils.user_current.getPassword());
        }
    }
    @Override
    protected void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
    }
}
