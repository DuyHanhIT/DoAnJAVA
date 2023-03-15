package com.example.shopsneaker.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shopsneaker.R;
import com.example.shopsneaker.adapter.statisticsDateAdapter;
import com.example.shopsneaker.model.statistics;
import com.example.shopsneaker.retrofit.ApiService;
import com.example.shopsneaker.retrofit.RetrofitClient;
import com.example.shopsneaker.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.disposables.CompositeDisposable;

public class StatisticsByDate extends AppCompatActivity {
    Toolbar toolbar;
    RecyclerView recyclerViewYear;
    statisticsDateAdapter statisticsAdapter;
    List<statistics> statisticsarray;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    ApiService apiBanGiay;
    int year, day, month;
    EditText edtyear,edtmonth,edtday;
    TextView textCountSPTK;
    ImageButton imgsearch;
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
        imgsearch.setOnClickListener(v -> getStatisticsDay());
    }

    private void getStatisticsDay() {
        day = Integer.parseInt(edtday.getText().toString());
        month = Integer.parseInt(edtmonth.getText().toString());
        year = Integer.parseInt(edtyear.getText().toString());
        compositeDisposable.add(apiBanGiay.getStatisticsDay(day,month,year)
                .subscribeOn(io.reactivex.rxjava3.schedulers.Schedulers.io())
                .observeOn(io.reactivex.rxjava3.android.schedulers.AndroidSchedulers.mainThread())
                .subscribe(
                        statisticsModel -> {
                            if (statisticsModel.isSuccess()){
                                statisticsarray = statisticsModel.getResult();
                                statisticsAdapter = new statisticsDateAdapter(getApplicationContext(),statisticsarray);
                                recyclerViewYear.setAdapter(statisticsAdapter);
                            }
                        },
                        throwable -> {
                            android.widget.Toast.makeText(getApplicationContext(), "Khong ket noi duoc voi server", android.widget.Toast.LENGTH_LONG).show();
                        }
                ));
    }

    private void actionToolbar() {
        setSupportActionBar(toolbar);
        toolbar.setTitle("Doanh thu ngày");
        ActionBar actionBar=getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(v -> finish());
    }
    private void Init() {
        textCountSPTK = findViewById(R.id.textCountSPTK);
        textCountSPTK.setVisibility(View.INVISIBLE);
        toolbar = findViewById(R.id.toolbarYear);
        recyclerViewYear = findViewById(R.id.recyclerViewYear);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerViewYear.setLayoutManager(layoutManager);
        statisticsarray = new ArrayList<>();
        edtyear = findViewById(R.id.edtYear);
        edtday = findViewById(R.id.edtDay);
        edtmonth = findViewById(R.id.edtMonth);
        imgsearch = findViewById(R.id.imgSearchYear);

    }
}