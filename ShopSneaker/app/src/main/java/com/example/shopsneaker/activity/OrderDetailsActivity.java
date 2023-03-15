package com.example.shopsneaker.activity;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.shopsneaker.R;
import com.example.shopsneaker.adapter.OrderDetailsAdapter;
import com.example.shopsneaker.model.OrderDetails;
import com.example.shopsneaker.retrofit.ApiService;
import com.example.shopsneaker.retrofit.RetrofitClient;
import com.example.shopsneaker.utils.Utils;
import java.util.ArrayList;
import java.util.List;
import io.reactivex.rxjava3.disposables.CompositeDisposable;

public class OrderDetailsActivity extends AppCompatActivity {
    private Toolbar toolbarOrderDetails;
    private RecyclerView recyclerOrderDetails;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private ApiService apiBanGiay;
    private List<OrderDetails> mangSanPham;
    private OrderDetailsAdapter orderDetailsAdapter;
    private int idorder;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chitietdonhang);
        apiBanGiay = RetrofitClient.getInstance(Utils.BASE_URL).create(ApiService.class);
        idorder = getIntent().getIntExtra("iddh",-1);
        initUi();
        actionToolbar();
        getSanPham();
    }



    private void actionToolbar() {
        setSupportActionBar(toolbarOrderDetails);
        ActionBar actionBar=getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        toolbarOrderDetails.setNavigationOnClickListener(v -> finish());
    }

    private void getSanPham() {
        compositeDisposable.add(apiBanGiay.getChiTietDH(idorder)
                .subscribeOn(io.reactivex.rxjava3.schedulers.Schedulers.io())
                .observeOn(io.reactivex.rxjava3.android.schedulers.AndroidSchedulers.mainThread())
                .subscribe(
                        orderDetailsModel -> {
                            if (orderDetailsModel.isSuccess()){
                                mangSanPham = orderDetailsModel.getResult();
                                orderDetailsAdapter = new OrderDetailsAdapter(getApplicationContext(),mangSanPham);
                                recyclerOrderDetails.setAdapter(orderDetailsAdapter);
                            }
                        },
                        throwable -> {
                            android.widget.Toast.makeText(getApplicationContext(), "Khong ket noi duoc voi server", android.widget.Toast.LENGTH_LONG).show();
                        }
                ));
    }
    public void initUi() {
        toolbarOrderDetails = findViewById(R.id.toolbarOrderDetails);
        //recyclerview
        recyclerOrderDetails = findViewById(R.id.recyclerOrderDetails);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerOrderDetails.setLayoutManager(linearLayoutManager);
        mangSanPham = new ArrayList<>();
        orderDetailsAdapter =new OrderDetailsAdapter(getApplicationContext(),mangSanPham);
        recyclerOrderDetails.setAdapter(orderDetailsAdapter);

    }
}
