package com.example.shopsneaker.activity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shopsneaker.R;
import com.example.shopsneaker.adapter.statisticsIdAdapter;
import com.example.shopsneaker.model.statistics;
import com.example.shopsneaker.retrofit.ApiService;
import com.example.shopsneaker.retrofit.RetrofitClient;
import com.example.shopsneaker.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.disposables.CompositeDisposable;

public class StatisticsById extends AppCompatActivity {
    Toolbar toolbar;
    RecyclerView recyclerViewYear;
    statisticsIdAdapter statisticsAdapter;
    List<statistics> statisticsarray;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    ApiService apiBanGiay;
    int year,x = 0;
    EditText edtyear;
    Integer brandid;
    ImageButton imgsearch;
    android.widget.LinearLayout linearLayouttktheodaymonthyear;
    android.widget.TextView countSPTK,textCountSLSPTK;
    androidx.appcompat.widget.SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);
        apiBanGiay = RetrofitClient.getInstance(Utils.BASE_URL).create(ApiService.class);
        brandid = getIntent().getIntExtra("brandid",1);
        int b = brandid;
        Init();
        actionToolbar();
        getStatisticsID1();
//        eventClick();
    }
    @Override
    public boolean onCreateOptionsMenu(android.view.Menu menu) {
        getMenuInflater().inflate(com.example.shopsneaker.R.menu.menu_admin,menu);
        android.app.SearchManager searchManager =(android.app.SearchManager) getSystemService(android.content.Context.SEARCH_SERVICE);
        searchView = (androidx.appcompat.widget.SearchView) menu.findItem(com.example.shopsneaker.R.id.menuSearch_Admin).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setMaxWidth(Integer.MAX_VALUE);
        searchView.setOnQueryTextListener(new androidx.appcompat.widget.SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                statisticsAdapter.getFilter().filter(query);

                countSPTK.setText("Sản phẩm đã bán: "+statisticsAdapter.getItemCount());
                return false;

            }

            @Override
            public boolean onQueryTextChange(String newText) {
                statisticsAdapter.getFilter().filter(newText);
                countSPTK.setText("Sản phẩm đã bán: "+statisticsAdapter.getItemCount());
                return false;
            }
        });
        return true;
    }
    
    private void getStatisticsID1() {


        compositeDisposable.add(apiBanGiay.getStatisticsID(0,brandid)
                .subscribeOn(io.reactivex.rxjava3.schedulers.Schedulers.io())
                .observeOn(io.reactivex.rxjava3.android.schedulers.AndroidSchedulers.mainThread())
                .subscribe(
                        statisticsModel -> {
                            if (statisticsModel.isSuccess()){
                                statisticsarray = statisticsModel.getResult();
                                statisticsAdapter = new statisticsIdAdapter(getApplicationContext(),statisticsarray);
                                recyclerViewYear.setAdapter(statisticsAdapter);
                                for (statistics brand : statisticsarray
                                ) {
                                    x += brand.getSellnumber();
                                }
                                countSPTK.setText("Sản phẩm đã bán: "+statisticsarray.size());
                                textCountSLSPTK.setText("Số lượng sản phẩm đã bán: "+x);
                            }
                        },
                        throwable -> {
                            android.widget.Toast.makeText(getApplicationContext(), "Khong ket noi duoc voi server", android.widget.Toast.LENGTH_LONG).show();
                        }
                ));
    }
    private void actionToolbar() {
        setSupportActionBar(toolbar);
        toolbar.setTitle("Doanh thu sản  phẩm");
        ActionBar actionBar=getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(v -> finish());
    }
    private void Init() {
        toolbar = findViewById(R.id.toolbarYear);
        recyclerViewYear = findViewById(R.id.recyclerViewYear);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerViewYear.setLayoutManager(layoutManager);
        statisticsarray = new ArrayList<>();
        edtyear = findViewById(R.id.edtDay);
        edtyear.setHint("Nhập id");
        imgsearch = findViewById(R.id.imgSearchYear);
        linearLayouttktheodaymonthyear = findViewById(R.id.linearLayouttktheodaymonthyear);
        linearLayouttktheodaymonthyear.setVisibility(android.view.View.INVISIBLE);
        countSPTK = findViewById(R.id.textCountSPTK);
        textCountSLSPTK= findViewById(R.id.textCountSLSPTK);
    }
}