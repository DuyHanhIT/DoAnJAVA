package com.example.shopsneaker.activity;
import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.shopsneaker.R;
import com.example.shopsneaker.adapter.OrderAdapter;
import com.example.shopsneaker.model.Order;
import com.example.shopsneaker.retrofit.ApiService;
import com.example.shopsneaker.retrofit.RetrofitClient;
import com.example.shopsneaker.utils.Utils;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.disposables.CompositeDisposable;

public class OrderActivity extends AppCompatActivity {
    private RecyclerView recyclerviewOrder;
    private Toolbar toolbarOrder;
    private OrderAdapter orderAdapter;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private ApiService apiBanGiay;
    private List<Order> mangdonhang;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donhang);
        apiBanGiay = RetrofitClient.getInstance(Utils.BASE_URL).create(ApiService.class);
        initUi();
        actionToolbar();
        getDonHang();
    }

    private void actionToolbar() {
        setSupportActionBar(toolbarOrder);
        ActionBar actionBar=getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        toolbarOrder.setNavigationOnClickListener(v -> finish());
    }

    private void getDonHang() {
        int accountID = Utils.user_current.getAccountid();
        compositeDisposable.add(apiBanGiay.getDonHang(accountID)
                .subscribeOn(io.reactivex.rxjava3.schedulers.Schedulers.io())
                .observeOn(io.reactivex.rxjava3.android.schedulers.AndroidSchedulers.mainThread())
                .subscribe(
                        orderModel -> {
                            if (orderModel.isSuccess()){
                                mangdonhang = orderModel.getResult();
                                orderAdapter = new OrderAdapter(getApplicationContext(),mangdonhang);
                                recyclerviewOrder.setAdapter(orderAdapter);
                            }
                        },
                        throwable -> {
                            android.widget.Toast.makeText(getApplicationContext(), "Khong ket noi duoc voi server", android.widget.Toast.LENGTH_LONG).show();
                        }
                ));
    }

    public void initUi() {
        toolbarOrder = findViewById(R.id.toolbardonhang);
        //recyclerview
        recyclerviewOrder = findViewById(R.id.recyclerviewOrder);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerviewOrder.setLayoutManager(linearLayoutManager);
        mangdonhang = new ArrayList<>();
        orderAdapter =new OrderAdapter(getApplicationContext(),mangdonhang);
        recyclerviewOrder.setAdapter(orderAdapter);
    }
}
