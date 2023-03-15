package com.example.shopsneaker.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shopsneaker.R;
import com.example.shopsneaker.adapter.SizeManagmentAdapter;
import com.example.shopsneaker.model.SizeManagment;
import com.example.shopsneaker.retrofit.ApiService;
import com.example.shopsneaker.retrofit.RetrofitClient;
import com.example.shopsneaker.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class SizeManagmentActivity extends AppCompatActivity {
    RecyclerView recyclerViewSize;
    SizeManagmentAdapter sizeManagmentAdapter;
    List<SizeManagment> arrSize;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    ApiService apiBanGiay;
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_size_managment);
        apiBanGiay = RetrofitClient.getInstance(Utils.BASE_URL).create(ApiService.class);
        Init();
        actionToolbar();
        //InitControl();
        getSizeTable();
    }

    private void getSizeTable() {
        compositeDisposable.add(apiBanGiay.getSizeManagement()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        sizeManagmentModel -> {
                            if(sizeManagmentModel.isSuccess()){
                                arrSize = sizeManagmentModel.getResult();
                                sizeManagmentAdapter = new SizeManagmentAdapter(getApplicationContext(),arrSize);
                                recyclerViewSize.setAdapter(sizeManagmentAdapter);
                            }
                        }
                ));
    }

    private void actionToolbar() {
        setSupportActionBar(toolbar);
        androidx.appcompat.app.ActionBar actionBar=getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(v -> finish());
    }

    private void Init() {
        toolbar =  findViewById(R.id.toolbarSizeManagment);
        recyclerViewSize = findViewById(R.id.recyclerViewSizeManagment);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerViewSize.setLayoutManager(layoutManager);
        recyclerViewSize.setHasFixedSize(true);
        arrSize = new ArrayList<>();
        sizeManagmentAdapter = new SizeManagmentAdapter(getApplicationContext(),arrSize);
        recyclerViewSize.setAdapter(sizeManagmentAdapter);

    }

}