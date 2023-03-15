package com.example.shopsneaker.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.example.shopsneaker.R;
import com.example.shopsneaker.adapter.BrandStatisticalAdapter;
import com.example.shopsneaker.model.Brand;
import com.example.shopsneaker.retrofit.ApiService;
import com.example.shopsneaker.retrofit.RetrofitClient;
import com.example.shopsneaker.utils.Utils;

import java.util.List;

import io.reactivex.rxjava3.disposables.CompositeDisposable;

public class BrandStatisticalActivity extends AppCompatActivity {
    androidx.appcompat.widget.Toolbar toolbar;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private ApiService apiBanGiay;
    List<Brand> list;
    BrandStatisticalAdapter brandStatisticalAdapter;
    RecyclerView recyclerView;
    TextView countBrand, countShoes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_brandstatistical);
        apiBanGiay = RetrofitClient.getInstance(Utils.BASE_URL).create(ApiService.class);
        Init();
        ActionToolBar();
        getBrandStatistical();
    }



    private void ActionToolBar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setTitle("Thống kê Hãng");
        toolbar.setNavigationOnClickListener(view -> {
            Intent intent =new Intent(getApplicationContext(),TKDayMonthYearActivity.class);
            startActivity(intent);
            finish();
        });

    }

    private void getBrandStatistical() {
        compositeDisposable.add(apiBanGiay.getTKBrand1()
                .subscribeOn(io.reactivex.rxjava3.schedulers.Schedulers.io())
                .observeOn(io.reactivex.rxjava3.android.schedulers.AndroidSchedulers.mainThread())
                .subscribe(
                        orderDetailsModel -> {
                            if (orderDetailsModel.isSuccess()){
                                list = orderDetailsModel.getResult();
                                brandStatisticalAdapter = new BrandStatisticalAdapter(getApplicationContext(),list);
                                recyclerView.setAdapter(brandStatisticalAdapter);
                                countBrand.setText("Brand: "+list.size());
                                int x=0;
                                for (Brand brand : list
                                ) {
                                    x += brand.getCountspdaban();
                                }
                                countShoes.setText("Sản phẩm đã bán: "+x);
                            }
                        },
                        throwable -> {
                            android.widget.Toast.makeText(getApplicationContext(), "Khong ket noi duoc voi server", android.widget.Toast.LENGTH_LONG).show();
                        }
                ));
    }


    private void Init() {
        toolbar= findViewById(R.id.toolbarPostsManagement);
        countBrand = findViewById(R.id.textCountBrand);
        countShoes = findViewById(R.id.textCountShoes);
        list = new java.util.ArrayList<>();
        recyclerView = findViewById(R.id.recyclerViewPostsManagment);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this,2);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        brandStatisticalAdapter =new BrandStatisticalAdapter(getApplicationContext(),list);
        recyclerView.setAdapter(brandStatisticalAdapter);
    }
}