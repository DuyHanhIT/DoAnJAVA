package com.example.shopsneaker.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shopsneaker.R;
import com.example.shopsneaker.adapter.statisticsAdapter;
import com.example.shopsneaker.model.statistics;
import com.example.shopsneaker.retrofit.ApiService;
import com.example.shopsneaker.retrofit.RetrofitClient;
import com.example.shopsneaker.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.disposables.CompositeDisposable;

public class StatisticsMonth extends AppCompatActivity {
    Toolbar toolbar;
    RecyclerView recyclerViewMonth;
    com.example.shopsneaker.adapter.statisticsAdapter statisticsAdapter;
    List<statistics> statisticsarray;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    ApiService apiBanGiay;
    EditText edtmonth,edtDay,edtYear;
    ImageButton imgsearch;
    String month, year;
    TextView textCountSPTK;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);
        apiBanGiay = RetrofitClient.getInstance(Utils.BASE_URL).create(ApiService.class);
        Init();
        actionToolbar();
        eventClick();
    }

    private void eventClick() {
        imgsearch.setOnClickListener(v -> getStatisticsMoth());
    }

    private void getStatisticsMoth() {
        month = edtDay.getText().toString();
        year = edtmonth.getText().toString();
        compositeDisposable.add(apiBanGiay.getStatisticsMonth(month,year)
                .subscribeOn(io.reactivex.rxjava3.schedulers.Schedulers.io())
                .observeOn(io.reactivex.rxjava3.android.schedulers.AndroidSchedulers.mainThread())
                .subscribe(
                        statisticsModel -> {
                            if (statisticsModel.isSuccess()){
                                statisticsarray = statisticsModel.getResult();
                                statisticsAdapter = new statisticsAdapter(getApplicationContext(),statisticsarray);
                                recyclerViewMonth.setAdapter(statisticsAdapter);
                            }
                        },
                        throwable -> {
                            android.widget.Toast.makeText(getApplicationContext(), "Khong ket noi duoc voi server", android.widget.Toast.LENGTH_LONG).show();
                        }
                ));
    }

    private void actionToolbar() {
        toolbar.setTitle("Doanh thu tháng");
        setSupportActionBar(toolbar);
        androidx.appcompat.app.ActionBar actionBar=getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(v -> finish());
    }
    private void Init() {
        textCountSPTK = findViewById(R.id.textCountSPTK);
        textCountSPTK.setVisibility(View.INVISIBLE);
        toolbar = findViewById(R.id.toolbarYear);
        recyclerViewMonth = findViewById(R.id.recyclerViewYear);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerViewMonth.setLayoutManager(layoutManager);
        statisticsarray = new ArrayList<>();
        edtmonth = findViewById(R.id.edtMonth);
        edtYear = findViewById(R.id.edtYear);
        edtDay = findViewById(R.id.edtDay);
        edtDay.setHint("Tháng");
        edtmonth.setHint("Năm");
        edtYear.setVisibility(View.INVISIBLE);
        imgsearch = findViewById(R.id.imgSearchYear);
    }
}