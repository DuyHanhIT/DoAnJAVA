package com.example.shopsneaker.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.shopsneaker.R;
import com.example.shopsneaker.adapter.SalesDetailsAdapter;
import com.example.shopsneaker.model.SaleDetails;
import com.example.shopsneaker.retrofit.ApiService;
import com.example.shopsneaker.retrofit.RetrofitClient;
import com.example.shopsneaker.utils.Utils;
import com.google.gson.Gson;


import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.disposables.CompositeDisposable;

public class SaleDetailsActivity extends AppCompatActivity{
    private RecyclerView recyclerviewSales;
    private Toolbar toolbarSales;
    private SalesDetailsAdapter salesDetailsAdapter;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private ApiService apiBanGiay;
    private List<SaleDetails> listSales;
    private int salesId;
    private String salesName;
    private TextView txtcountsp;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sales);
        salesId = getIntent().getIntExtra("idsales",-1);
        salesName = getIntent().getStringExtra("salesName");
        initUi();
        actionToolbar();
        getSaleDetails();
    }
    private void actionToolbar() {
        setSupportActionBar(toolbarSales);
        ActionBar actionBar=getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        toolbarSales.setNavigationOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(),SalesActivity.class);
            startActivity(intent);
            Utils.ListSaleDetailsDelete.clear();
            finish();
        });
    }

    public void initUi() {
        apiBanGiay = RetrofitClient.getInstance(Utils.BASE_URL).create(ApiService.class);
        toolbarSales = findViewById(R.id.toolbarSales);
        toolbarSales.setTitle(salesName);
        txtcountsp = findViewById(R.id.txtcountsp);

        //recyclerview
        recyclerviewSales = findViewById(R.id.recyclerViewSales);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerviewSales.setLayoutManager(linearLayoutManager);
        listSales = new ArrayList<>();
        salesDetailsAdapter =new SalesDetailsAdapter(getApplicationContext(),listSales);
        recyclerviewSales.setAdapter(salesDetailsAdapter);
    }

    private void getSaleDetails() {
        compositeDisposable.add(apiBanGiay.getSaleDetails(salesId)
                .subscribeOn(io.reactivex.rxjava3.schedulers.Schedulers.io())
                .observeOn(io.reactivex.rxjava3.android.schedulers.AndroidSchedulers.mainThread())
                .subscribe(
                        salesModel -> {
                            if (salesModel.isSuccess()){
                                listSales = salesModel.getResult();
                                salesDetailsAdapter = new SalesDetailsAdapter(getApplicationContext(),listSales);
                                recyclerviewSales.setAdapter(salesDetailsAdapter);
                                txtcountsp.setText("Tổng: "+listSales.size());
                            }
                        },
                        throwable -> {
                            android.widget.Toast.makeText(getApplicationContext(), "Khong ket noi duoc voi server", android.widget.Toast.LENGTH_LONG).show();
                        }
                ));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_crud,menu);
        MenuItem item = menu.findItem(R.id.menuCehckedAll);
        item.setVisible(false);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.menuAdd:
                Intent intent = new Intent(getApplicationContext(),AddProductSales.class);
                intent.putExtra("salesid", salesId);
                Utils.ListSaleDetailsDelete.clear();
                Utils.ListSaleDetails.clear();
                startActivity(intent);
                finish();
                break;
            case R.id.menuDelete:
                if(Utils.ListSaleDetailsDelete.size()>0){
                    Log.d("test", new Gson().toJson(Utils.ListSaleDetailsDelete));
                    compositeDisposable.add(apiBanGiay.deleteSaleDetails(salesId,new Gson().toJson(Utils.ListSaleDetailsDelete))
                            .subscribeOn(io.reactivex.rxjava3.schedulers.Schedulers.io())
                            .observeOn(io.reactivex.rxjava3.android.schedulers.AndroidSchedulers.mainThread())
                            .subscribe(
                                    salesModel -> {
                                        Toast.makeText(this, "Thành Công", Toast.LENGTH_SHORT).show();
                                        Utils.ListSaleDetailsDelete.clear();
                                        finish();
                                        startActivity(getIntent());
                                    },
                                    throwable -> {
                                        Toast.makeText(getApplicationContext(), "Khong ket noi duoc voi server", Toast.LENGTH_LONG).show();
                                    }
                            ));
                }
                else {
                    Toast.makeText(getApplicationContext(), "Chưa chọn sản phẩm cần xóa", Toast.LENGTH_LONG).show();
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
