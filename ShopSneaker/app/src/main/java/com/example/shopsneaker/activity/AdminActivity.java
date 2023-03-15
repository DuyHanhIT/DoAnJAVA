package com.example.shopsneaker.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.shopsneaker.R;
import com.example.shopsneaker.adapter.AdminAdapter;
import com.example.shopsneaker.model.Admin;
import com.example.shopsneaker.model.User;
import com.example.shopsneaker.utils.Utils;

import java.util.ArrayList;

import io.paperdb.Paper;

public class AdminActivity extends AppCompatActivity {
    ImageView logout;
    ListView listView;
    Toolbar toolbar;
    ArrayList<Admin> arrayList;
    AdminAdapter adminAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        Paper.init(this);
        if(Paper.book().read("user")!=null){
            User user = Paper.book().read("user");
            Utils.user_current = user;
        }
        Init();
        actionToolbar();
        EventClick();
    }
    private void actionToolbar() {
        setSupportActionBar(toolbar);
        androidx.appcompat.app.ActionBar actionBar=getSupportActionBar();

    }
    @Override
    public boolean onCreateOptionsMenu(android.view.Menu menu) {
        getMenuInflater().inflate(com.example.shopsneaker.R.menu.menu_admin1,menu);

        return true;
    }
    @Override
    public boolean onOptionsItemSelected(@androidx.annotation.NonNull android.view.MenuItem item) {
        switch (item.getItemId()){
            case com.example.shopsneaker.R.id.menuChangpass_Admin:
                Intent intent = new Intent(getApplicationContext(), ChangePassActivity.class);
                startActivity(intent);
                finish();
                break;
            case com.example.shopsneaker.R.id.menuLogout_Admin:
                androidx.appcompat.app.AlertDialog.Builder b = new androidx.appcompat.app.AlertDialog.Builder(this);
                //Thiết lập tiêu đề
                b.setTitle("Thông báo");
                b.setMessage("Bạn chắc chắn muốn đăng xuất ?");
                b.setPositiveButton("Đăng xuất", new android.content.DialogInterface.OnClickListener() {
                    public void onClick(android.content.DialogInterface dialog, int id) {
                        io.paperdb.Paper.book().delete("user");
                        com.example.shopsneaker.utils.Utils.user_current.setUsername(null);
                        com.example.shopsneaker.utils.Utils.user_current.setPassword(null);
                        android.content.Intent intent = new android.content.Intent(getApplicationContext(), com.example.shopsneaker.activity.LoginActivity.class);
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
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void Init() {
        //logout = findViewById(R.id.logout);
        listView = findViewById(R.id.lv_manager);
        arrayList = new java.util.ArrayList<>();
        if (Utils.user_current.getRolesid()== 1){
            arrayList.add(new Admin("Quản lý đơn hàng","https://img.icons8.com/cute-clipart/344/task.png"));
            arrayList.add(new Admin("Quản lý sản phẩm", "https://img.icons8.com/external-itim2101-lineal-color-itim2101/344/external-shoe-shop-store-itim2101-lineal-color-itim2101.png"));
            arrayList.add(new Admin("Quản lý sales", "https://img.icons8.com/external-flaticons-flat-flat-icons/344/external-sales-cyber-monday-flaticons-flat-flat-icons.png"));
            arrayList.add(new Admin("Quản lý size giày","https://img.icons8.com/color-glass/344/dialing-phone.png"));
            arrayList.add(new Admin("Quản lý bài đăng","https://img.icons8.com/fluency/344/management.png"));
            arrayList.add(new Admin("Quản lý tài khoản", "https://img.icons8.com/external-flaticons-flat-flat-icons/344/external-account-e-commerce-flaticons-flat-flat-icons.png"));
            arrayList.add(new Admin("Thống kê", "https://img.icons8.com/fluency/344/statistics.png"));

        }else if(Utils.user_current.getRolesid()==2){
            arrayList.add(new Admin("Quản lý đơn hàng","https://img.icons8.com/cute-clipart/344/task.png"));
            arrayList.add(new Admin("Quản lý sản phẩm", "https://img.icons8.com/external-itim2101-lineal-color-itim2101/344/external-shoe-shop-store-itim2101-lineal-color-itim2101.png"));
        }

        adminAdapter = new AdminAdapter(AdminActivity.this, R.layout.item_manager,arrayList);
        listView.setAdapter(adminAdapter);
        toolbar = findViewById(R.id.toolbarAdmin);
    }

    private void EventClick() {
        listView.setOnItemClickListener((parent, view, position, id) -> {
            if(position == 0){
                android.content.Intent intent = new android.content.Intent();
                intent.setClass(AdminActivity.this, QLDonHangActivity.class);
                startActivity(intent);
            }
            if(position == 1){
                android.content.Intent intent = new android.content.Intent();
                intent.setClass(AdminActivity.this, QLSPActivity.class);
                startActivity(intent);
            }
            if(position == 2){
                android.content.Intent intent = new android.content.Intent();
                intent.setClass(AdminActivity.this, SalesActivity.class);
                startActivity(intent);
            }
            if(position == 3){
                android.content.Intent intent = new android.content.Intent();
                intent.setClass(AdminActivity.this, SizeManagmentActivity.class);
                startActivity(intent);
            }
            if(position == 4){
                android.content.Intent intent = new android.content.Intent();
                intent.setClass(AdminActivity.this, MarketManagerActivity.class);
                startActivity(intent);
            }
            if(position == 5){
                android.content.Intent intent = new android.content.Intent();
                intent.setClass(AdminActivity.this, AccountManagerActivity.class);
                startActivity(intent);
            }
            if(position == 6){
                android.content.Intent intent = new android.content.Intent();
                intent.setClass(AdminActivity.this, TKDayMonthYearActivity.class);
                startActivity(intent);
            }

        });
       /* logout.setOnClickListener(view -> {
            androidx.appcompat.app.AlertDialog.Builder b = new androidx.appcompat.app.AlertDialog.Builder(view.getContext());
            //Thiết lập tiêu đề
            b.setTitle("Thông báo");
            b.setMessage("Bạn chắc chắn muốn đăng xuất ?");
            b.setPositiveButton("Đăng xuất", new android.content.DialogInterface.OnClickListener() {
                public void onClick(android.content.DialogInterface dialog, int id) {
                    io.paperdb.Paper.book().delete("user");
                    com.example.shopsneaker.utils.Utils.user_current.setUsername(null);
                    com.example.shopsneaker.utils.Utils.user_current.setPassword(null);
                    android.content.Intent intent = new android.content.Intent(getApplicationContext(), com.example.shopsneaker.activity.LoginActivity.class);
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
        });*/

    }
}