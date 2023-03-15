package com.example.shopsneaker.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.shopsneaker.retrofit.ApiService;

public class QLDonHangActivity extends AppCompatActivity {
    androidx.appcompat.widget.Toolbar toolbar;

    private io.reactivex.rxjava3.disposables.CompositeDisposable compositeDisposable = new io.reactivex.rxjava3.disposables.CompositeDisposable();
    private ApiService apiBanGiay;
    java.util.List<com.example.shopsneaker.model.ThongKeTinhTrangDonHang> mangThongKeTinhTrangDonHang;
    android.widget.TextView countTatCaDonHang, countChoDuyet, countDaDuyet, countDangGiao, countDaGiao, countDaHuy;
    android.widget.LinearLayout linearLayoutTatCaDonHang, linearLayoutChoDuyet, linearLayoutDaDuyet,linearLayoutDangGiao, linearLayoutDaGiao, linearLayoutDaHuy;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.example.shopsneaker.R.layout.activity_qldon_hang);
        apiBanGiay = com.example.shopsneaker.retrofit.RetrofitClient.getInstance(com.example.shopsneaker.utils.Utils.BASE_URL).create(ApiService.class);
        Init();
        ActionToolBar();
        GetThongKeTinhTrangDonHang();
        EventClick();
    }

    private void EventClick() {
        android.content.Intent intent = new android.content.Intent();
        linearLayoutTatCaDonHang.setOnClickListener(view -> {
            intent.setClass(getApplicationContext(),OrderManagerActivity.class);
            intent.putExtra("status",0);
            intent.putExtra("Title","Tất cả đơn hàng");
            startActivity(intent);
        });
        linearLayoutChoDuyet.setOnClickListener(view -> {
            intent.setClass(getApplicationContext(),OrderManagerActivity.class);
            intent.putExtra("status",1);
            intent.putExtra("Title","Đơn hàng đang chờ duyệt");
            startActivity(intent);
        });
        linearLayoutDaDuyet.setOnClickListener(view -> {
            intent.setClass(getApplicationContext(),OrderManagerActivity.class);
            intent.putExtra("status",2);
            intent.putExtra("Title","Đơn hàng đã duyệt");
            startActivity(intent);
        });
        linearLayoutDangGiao.setOnClickListener(view -> {
            intent.setClass(getApplicationContext(),OrderManagerActivity.class);
            intent.putExtra("status",3);
            intent.putExtra("Title","Đơn hàng đang giao");
            startActivity(intent);
        });
        linearLayoutDaGiao.setOnClickListener(view -> {
            intent.setClass(getApplicationContext(),OrderManagerActivity.class);
            intent.putExtra("status",4);
            intent.putExtra("Title","Đơn hàng đã giao");
            startActivity(intent);
        });
        linearLayoutDaHuy.setOnClickListener(view -> {
            intent.setClass(getApplicationContext(),OrderManagerActivity.class);
            intent.putExtra("status",5);
            intent.putExtra("Title","Đơn hàng đã hủy");
            startActivity(intent);
        });

    }

    private void getGiaTri() {
        int tatcadonhang = mangThongKeTinhTrangDonHang.get(0).getTatCaDonHang();
        int choduyet = mangThongKeTinhTrangDonHang.get(0).getDangChoDuyet();
        int daduyet = mangThongKeTinhTrangDonHang.get(0).getDaDuyet();
        int danggiao = mangThongKeTinhTrangDonHang.get(0).getDangGiao();
        int dagiao = mangThongKeTinhTrangDonHang.get(0).getDaGiaoHang();
        int dahuy = mangThongKeTinhTrangDonHang.get(0).getDaHuy();
        String txttatcadonhang = String.valueOf(tatcadonhang);
        String txtchoduyet = String.valueOf(choduyet);
        String txtdaduyet = String.valueOf(daduyet);
        String txtdanggiao = String.valueOf(danggiao);
        String txtdagiao = String.valueOf(dagiao);
        String txtdahuy = String.valueOf(dahuy);


        countTatCaDonHang.setText(txttatcadonhang);
        countChoDuyet.setText(txtchoduyet);
        countDaDuyet.setText(txtdaduyet);
        countDangGiao.setText(txtdanggiao);
        countDaGiao.setText(txtdagiao);
        countDaHuy.setText(txtdahuy);

    }

    private void GetThongKeTinhTrangDonHang() {

        compositeDisposable.add(apiBanGiay.getTinhTrangDonHang().subscribeOn(io.reactivex.rxjava3.schedulers.Schedulers.io())
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
                            android.widget.Toast.makeText(getApplicationContext(),"Không kết nối được server ", android.widget.Toast.LENGTH_LONG).show();
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
        toolbar= findViewById(com.example.shopsneaker.R.id.ToolbarQLTinhTrangDonHang);
        countTatCaDonHang= findViewById(com.example.shopsneaker.R.id.countTatCaDonHang);
        countChoDuyet= findViewById(com.example.shopsneaker.R.id.countChoDuyet);
        countDaDuyet= findViewById(com.example.shopsneaker.R.id.countDaDuyet);
        countDangGiao= findViewById(com.example.shopsneaker.R.id.countDangGiao);
        countDaGiao= findViewById(com.example.shopsneaker.R.id.countDaGiao);
        countDaHuy= findViewById(com.example.shopsneaker.R.id.countDaHuy);
        linearLayoutTatCaDonHang=findViewById(com.example.shopsneaker.R.id.linearLayoutTatCaDonHang);
        linearLayoutChoDuyet= findViewById(com.example.shopsneaker.R.id.linearLayoutChoDuyet);
        linearLayoutDaDuyet = findViewById(com.example.shopsneaker.R.id.linearLayoutDaDuyet);
        linearLayoutDangGiao = findViewById(com.example.shopsneaker.R.id.linearLayoutDangGiao);
        linearLayoutDaGiao = findViewById(com.example.shopsneaker.R.id.linearLayoutDaGiao);
        linearLayoutDaHuy = findViewById(com.example.shopsneaker.R.id.linearLayoutDaHuy);
        mangThongKeTinhTrangDonHang = new java.util.ArrayList<>();
    }
}