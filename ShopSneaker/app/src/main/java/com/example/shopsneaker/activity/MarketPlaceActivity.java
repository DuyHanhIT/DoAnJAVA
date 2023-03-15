package com.example.shopsneaker.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shopsneaker.R;
import com.example.shopsneaker.adapter.MarketAdapter;
import com.example.shopsneaker.model.Market;
import com.example.shopsneaker.retrofit.ApiService;
import com.example.shopsneaker.retrofit.RetrofitClient;
import com.example.shopsneaker.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MarketPlaceActivity extends AppCompatActivity {
    Toolbar toolbar;
    RecyclerView recyclerView;
    FrameLayout frametoolbar;
    MarketAdapter marketAdapter;
    List<Market> arrMarket;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    ApiService apiBanGiay;
    private SearchView searchView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_market_place);
        apiBanGiay = RetrofitClient.getInstance(Utils.BASE_URL).create(ApiService.class);
        Init();
        actionToolbar();
        getPosts();
        EvenClick();
    }

    private void getPosts() {
        int statusid = 2;
        compositeDisposable.add(apiBanGiay.getPosts(statusid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        marketModel -> {
                            if(marketModel.isSuccess()){
                                arrMarket = marketModel.getResult();
                                marketAdapter = new MarketAdapter(getApplicationContext(),arrMarket);
                                recyclerView.setAdapter(marketAdapter);
                            }
                        },
                        throwable -> {
                            Toast.makeText(getApplicationContext(),"Không có bài đăng nào", Toast.LENGTH_LONG).show();
                        }
                ));
    }

    private void EvenClick(){
        frametoolbar.setOnClickListener(v -> {
            Intent intent= new Intent(getApplicationContext(), MessageActivity.class);
            startActivity(intent);
        });
    }

    private void actionToolbar() {
        setSupportActionBar(toolbar);
        androidx.appcompat.app.ActionBar actionBar=getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(v -> {
            Intent intent= new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            finish();
        });
    }

    private void Init() {
        toolbar = findViewById(R.id.toolbarProduct);
        frametoolbar = findViewById(R.id.frametoolbar);
        recyclerView = findViewById(R.id.recyclerViewItems1);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        arrMarket = new ArrayList<>();
        marketAdapter = new MarketAdapter(getApplicationContext(),arrMarket);
        recyclerView.setAdapter(marketAdapter);
    }
}