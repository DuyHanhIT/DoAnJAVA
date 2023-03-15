package com.example.shopsneaker.activity;


import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;

import com.example.shopsneaker.R;
import com.example.shopsneaker.retrofit.ApiService;
import com.example.shopsneaker.retrofit.RetrofitClient;
import com.example.shopsneaker.utils.Utils;

import io.paperdb.Paper;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;


public class ProfileActivity extends AppCompatActivity {
    TextView txtUserprof, txtgiohang, txtdonhang,txtPhone,txtName,txtAddress,txtEdit;
    AppCompatButton btnChangePass, btnLogout;
    android.widget.LinearLayout  linearLayoutGioHang, linearLayoutDonHang, linearLayoutManagement,linearLayout_AddPost;
    Toolbar toolbarProfile;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private ApiService apiBanGiay;
    String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        apiBanGiay = RetrofitClient.getInstance(Utils.BASE_URL).create(ApiService.class);
        username = Utils.user_current.getUsername();
        getUser();
        initView();
        actionToolbar();
        initControl();
    }
    private void actionToolbar() {
        setSupportActionBar(toolbarProfile);
        ActionBar actionBar=getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        toolbarProfile.setNavigationOnClickListener(v -> finish());
    }

    private void getUser(){
        compositeDisposable.add(apiBanGiay.getUser(username).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        userModel -> {
                            if(userModel.isSuccess()){
                                Utils.user_current = userModel.getResult().get(0);
                            }else {
                                Toast.makeText(getApplicationContext(),"Thất bại", Toast.LENGTH_LONG).show();
                            }
                        },
                        throwable -> {
                            Toast.makeText(getApplicationContext(),"Không kết nối được server", Toast.LENGTH_LONG).show();
                        }
                ));
    }

    private void initControl(){
        txtEdit.setOnClickListener(v -> {

            Intent intent = new Intent(getApplicationContext(), ThongTinKhachHangActivity.class);
            startActivity(intent);
        });
        btnLogout.setOnClickListener(v -> {
            androidx.appcompat.app.AlertDialog.Builder b = new androidx.appcompat.app.AlertDialog.Builder(v.getContext());
            //Thiết lập tiêu đề
            b.setTitle("Thông báo");
            b.setMessage("Bạn chắc chắn muốn đăng xuất ?");
            b.setPositiveButton("Đăng xuất", new android.content.DialogInterface.OnClickListener() {
                public void onClick(android.content.DialogInterface dialog, int id) {
                    Paper.book().delete("user");
                    Utils.user_current.setUsername(null);
                    Utils.user_current.setPassword(null);
                    Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                    startActivity(intent);
                    finish();
                }
            });
            b.setNegativeButton("Ở lại", new android.content.DialogInterface.OnClickListener() {
                public void onClick(android.content.DialogInterface dialog, int id) {

                    return;
                }
            });//Tạo dialog
            androidx.appcompat.app.AlertDialog al = b.create();//Hiển thị
            al.show();

        });

        btnChangePass.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), ChangePassActivity.class);
            startActivity(intent);
            finish();
        });

        linearLayoutGioHang.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), GioHangActivity.class);
            startActivity(intent);
        });
        linearLayoutDonHang.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), OrderActivity.class);
            startActivity(intent);
        });
        linearLayoutManagement.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), TransactionManagementActivity.class);
            startActivity(intent);
        });
        linearLayout_AddPost.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), AddPostsActivity.class);
            startActivity(intent);
        });
    }

    private void initView() {
        txtEdit=findViewById(R.id.textEdit);
        txtUserprof = findViewById(R.id.txtEmail);
        txtdonhang= findViewById(R.id.txtDonHang);
        txtgiohang = findViewById(R.id.txtGioHang);
        btnChangePass = findViewById(R.id.btnChangePass);
        btnLogout = findViewById(R.id.btnLogout);
        txtPhone = findViewById(R.id.txtPhone);
        txtName = findViewById(R.id.txtName);
        txtAddress = findViewById(R.id.txtAddress);
        toolbarProfile = findViewById(R.id.toolbarProfile);
        linearLayoutGioHang = findViewById(com.example.shopsneaker.R.id.linearLayout_GioHang);
        linearLayoutDonHang = findViewById(com.example.shopsneaker.R.id.linearLayout_DonHang);
        linearLayoutManagement = findViewById(R.id.linearLayout_Managment);
        linearLayout_AddPost = findViewById(R.id.linearLayout_AddPost);
    }

    protected void onResume () {
        super.onResume();
        if (Utils.user_current.getUsername() != null && Utils.user_current.getPassword() != null ){
            txtAddress.setText(Utils.user_current.getAddress());
            txtName.setText(Utils.user_current.getName());
            txtPhone.setText(Utils.user_current.getPhone());
            txtUserprof.setText(Utils.user_current.getUsername());
        }
    }

    @Override
    protected void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
    }

}
