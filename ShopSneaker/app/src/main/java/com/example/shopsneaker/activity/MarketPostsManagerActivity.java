package com.example.shopsneaker.activity;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shopsneaker.R;
import com.example.shopsneaker.adapter.MarketManagerAdapter;
import com.example.shopsneaker.model.Market;
import com.example.shopsneaker.retrofit.ApiService;
import com.example.shopsneaker.retrofit.RetrofitClient;
import com.example.shopsneaker.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MarketPostsManagerActivity extends AppCompatActivity {
    Toolbar toolbar;
    RecyclerView recyclerView;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    ApiService apiBanGiay;
    List<Market> arrayPosts;
    private int statusid;
    private String Title;
    MarketManagerAdapter postsManagementAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_posts_managment);
        apiBanGiay = RetrofitClient.getInstance(Utils.BASE_URL).create(ApiService.class);
        statusid = getIntent().getIntExtra("status",0);
        Title = getIntent().getStringExtra("Title");
        Init();
        actionToolbar();
        getPosts();
    }

    private void getPosts() {
        compositeDisposable.add(apiBanGiay.getPosts(statusid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        marketModel -> {
                            if(marketModel.isSuccess()){
                                arrayPosts = marketModel.getResult();
                                postsManagementAdapter = new MarketManagerAdapter(getApplicationContext(),arrayPosts);
                                recyclerView.setAdapter(postsManagementAdapter);
                            }
                        },
                        throwable -> {
                            Toast.makeText(getApplicationContext(),"Không có bài đăng nào", Toast.LENGTH_LONG).show();
                        }
                ));
    }

    private void actionToolbar() {
        setSupportActionBar(toolbar);
        androidx.appcompat.app.ActionBar actionBar=getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle(Title);
        toolbar.setNavigationOnClickListener(v -> finish());
    }

    private void Init() {
        toolbar = findViewById(R.id.toolbarPostsManagment);
        recyclerView = findViewById(R.id.recyclerViewPostsManagment);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        arrayPosts= new ArrayList<>();
        postsManagementAdapter = new MarketManagerAdapter(getApplicationContext(),arrayPosts);
        recyclerView.setAdapter(postsManagementAdapter);
    }
}