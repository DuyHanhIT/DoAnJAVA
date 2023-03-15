package com.example.shopsneaker.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.shopsneaker.R;
import com.example.shopsneaker.model.ThongKeTinhTrangDonHang;
import com.example.shopsneaker.retrofit.ApiService;
import com.example.shopsneaker.retrofit.RetrofitClient;
import com.example.shopsneaker.utils.Utils;

import java.util.List;

import io.reactivex.rxjava3.disposables.CompositeDisposable;

public class MarketManagerActivity extends AppCompatActivity {
    androidx.appcompat.widget.Toolbar toolbar;

    private CompositeDisposable compositeDisposable = new io.reactivex.rxjava3.disposables.CompositeDisposable();
    private ApiService apiBanGiay;
    TextView tcdh,dcd,dd;
    List<ThongKeTinhTrangDonHang> mangThongKeTinhTrangDonHang;
    android.widget.TextView countTatCaDonHang, countChoDuyet, countDaDuyet;
    android.widget.LinearLayout linearLayoutTatCaDonHang, linearLayoutChoDuyet, linearLayoutDaDuyet,linearLayoutDangGiao, linearLayoutDaGiao, linearLayoutDaHuy;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qldon_hang);
        apiBanGiay = RetrofitClient.getInstance(Utils.BASE_URL).create(ApiService.class);
        Init();
        ActionToolBar();
        GetThongKeTinhTrangDonHang();
        EventClick();
    }

    private void EventClick() {
        android.content.Intent intent = new android.content.Intent();
        linearLayoutTatCaDonHang.setOnClickListener(view -> {
            intent.setClass(getApplicationContext(), MarketPostsManagerActivity.class);
            intent.putExtra("status",0);
            intent.putExtra("Title","Tất cả bài đăng");
            startActivity(intent);
        });
        linearLayoutChoDuyet.setOnClickListener(view -> {
            intent.setClass(getApplicationContext(), MarketPostsManagerActivity.class);
            intent.putExtra("status",1);
            intent.putExtra("Title","Chưa duyệt");
            startActivity(intent);
        });
        linearLayoutDaDuyet.setOnClickListener(view -> {
            intent.setClass(getApplicationContext(), MarketPostsManagerActivity.class);
            intent.putExtra("status",2);
            intent.putExtra("Title","Đã duyệt");
            startActivity(intent);
        });

    }

    private void getGiaTri() {
        int tatcadonhang = mangThongKeTinhTrangDonHang.get(0).getTatCaDonHang();
        int choduyet = mangThongKeTinhTrangDonHang.get(0).getDangChoDuyet();
        int daduyet = mangThongKeTinhTrangDonHang.get(0).getDaDuyet();
        String txttatcadonhang = String.valueOf(tatcadonhang);
        String txtchoduyet = String.valueOf(choduyet);
        String txtdaduyet = String.valueOf(daduyet);
        countTatCaDonHang.setText(txttatcadonhang);
        countChoDuyet.setText(txtchoduyet);
        countDaDuyet.setText(txtdaduyet);

    }

    private void GetThongKeTinhTrangDonHang() {

        compositeDisposable.add(apiBanGiay.getTKDHClient().subscribeOn(io.reactivex.rxjava3.schedulers.Schedulers.io())
                .observeOn(io.reactivex.rxjava3.android.schedulers.AndroidSchedulers.mainThread())
                .subscribe(
                        thongKeTinhTrangDonHangModel -> {
                            if(thongKeTinhTrangDonHangModel.isSuccess()){
                                mangThongKeTinhTrangDonHang = thongKeTinhTrangDonHangModel.getResult();
                                getGiaTri();
                            }else {
                                android.widget.Toast.makeText(getApplicationContext(),"Thất bại", android.widget.Toast.LENGTH_LONG).show();
                            }
                        },
                        throwable -> {
                            android.widget.Toast.makeText(getApplicationContext(),"Không kết nối được server", android.widget.Toast.LENGTH_LONG).show();
                        }
                ));

    }


    private void ActionToolBar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar.setNavigationOnClickListener(view -> {
            android.content.Intent intent =new android.content.Intent(getApplicationContext(),AdminActivity.class);
            startActivity(intent);
            finish();
        });

    }

    private void Init() {
        tcdh = findViewById(R.id.tcdh);
        tcdh.setText("Tất cả bài đăng");
        dcd = findViewById(R.id.dcd);
        dcd.setText("Đang chờ duyệt");
        dd = findViewById(R.id.dd);
        dd.setText("Đã duyệt");
        toolbar= findViewById(com.example.shopsneaker.R.id.ToolbarQLTinhTrangDonHang);
        toolbar.setTitle("Quản lý bài đăng");
        countTatCaDonHang= findViewById(com.example.shopsneaker.R.id.countTatCaDonHang);
        countChoDuyet= findViewById(com.example.shopsneaker.R.id.countChoDuyet);
        countDaDuyet= findViewById(com.example.shopsneaker.R.id.countDaDuyet);
        linearLayoutTatCaDonHang=findViewById(com.example.shopsneaker.R.id.linearLayoutTatCaDonHang);
        linearLayoutChoDuyet= findViewById(com.example.shopsneaker.R.id.linearLayoutChoDuyet);
        linearLayoutDaDuyet = findViewById(com.example.shopsneaker.R.id.linearLayoutDaDuyet);
        linearLayoutDangGiao = findViewById(com.example.shopsneaker.R.id.linearLayoutDangGiao);
        linearLayoutDangGiao.setVisibility(View.INVISIBLE);
        linearLayoutDaGiao = findViewById(com.example.shopsneaker.R.id.linearLayoutDaGiao);
        linearLayoutDaGiao.setVisibility(View.INVISIBLE);
        linearLayoutDaHuy = findViewById(com.example.shopsneaker.R.id.linearLayoutDaHuy);
        linearLayoutDaHuy.setVisibility(View.INVISIBLE);
        mangThongKeTinhTrangDonHang = new java.util.ArrayList<>();
    }
}