package com.example.shopsneaker.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shopsneaker.R;
import com.example.shopsneaker.adapter.GioHangAdapter;
import com.example.shopsneaker.model.EventBus.tinhtongtien;
import com.example.shopsneaker.utils.Utils;
import com.example.shopsneaker.utils.checkconnect;
import com.nex3z.notificationbadge.NotificationBadge;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.text.DecimalFormat;

public class GioHangActivity extends AppCompatActivity {
    RecyclerView rcvgiohang;
    TextView txtthongbao;
    TextView txttongtien;
    Button btthanhtoan,bttieptucmuahang, btnTru, btnCong, btnMua;
    Toolbar toolbargiohang;
    GioHangAdapter giohangAdapter;
    long tongtien;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_giohang);
        initUi();
        actionToolbar();
        checkData();
        evenUltil();
        evenButton();
    }
    private void evenButton() {
        bttieptucmuahang.setOnClickListener(v -> {
            Intent intent =new Intent(getApplicationContext(),MainActivity.class);
            startActivity(intent);
            finish();
        });
        btthanhtoan.setOnClickListener(v -> {
            if(Utils.manggiohang.size()>0){
                Intent intent=new Intent(getApplicationContext(), PaymentActivity.class);
                intent.putExtra("tongtien",tongtien);
                startActivity(intent);
                finish();
            }else{
                checkconnect.ShowToast_Short(getApplicationContext(),"Giỏ hàng của bạn chưa có sản phẩm để thanh toán");
            }
        });
    }

    public void evenUltil() {
        tongtien=0;
        for(int i=0;i<Utils.manggiohang.size();i++){
            tongtien+=Utils.manggiohang.get(i).getPurchased()*Utils.manggiohang.get(i).getPrice();
        }
        DecimalFormat decimalFormat =new DecimalFormat("###,###,###");
        txttongtien.setText(decimalFormat.format(tongtien)+ "USD");
    }

    private void checkData() {
        if(Utils.manggiohang.size()<=0){
            giohangAdapter.notifyDataSetChanged();
            txtthongbao.setVisibility(View.VISIBLE);
            rcvgiohang.setVisibility(View.INVISIBLE);
        }else{
            giohangAdapter.notifyDataSetChanged();
            txtthongbao.setVisibility(View.INVISIBLE);
            rcvgiohang.setVisibility(View.VISIBLE);
        }
    }
    private void actionToolbar() {
        setSupportActionBar(toolbargiohang);
        ActionBar actionBar=getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        toolbargiohang.setNavigationOnClickListener(v -> {
            finish();
        });
    }
    private void initUi() {
        rcvgiohang=findViewById(R.id.rcvgiohang);
        txtthongbao=findViewById(R.id.textviewthongbaogiohangtrong);
        txttongtien=findViewById(R.id.textviewtongtien);
        btthanhtoan=findViewById(R.id.buttonthanhtoan);
        bttieptucmuahang=findViewById(R.id.buttontieptucmuahang);
        btnCong=findViewById(R.id.buttonminusright);
        btnTru=findViewById(R.id.buttonminusleft);
        btnMua=findViewById(R.id.buttonvalue);
        toolbargiohang=findViewById(R.id.toolbargiohang);
        rcvgiohang.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        rcvgiohang.setLayoutManager(layoutManager);
        giohangAdapter = new GioHangAdapter(getApplicationContext(), Utils.manggiohang);
        rcvgiohang.setAdapter(giohangAdapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }
    @Subscribe(sticky = true,threadMode = ThreadMode.MAIN)
    public void eventTinhTien(tinhtongtien event){
        if(event!=null){
            evenUltil();
        }
    }
}
