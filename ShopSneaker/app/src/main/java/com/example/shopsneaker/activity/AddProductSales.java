package com.example.shopsneaker.activity;

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
import com.example.shopsneaker.adapter.AddSaleDetailsAdapter;
import com.example.shopsneaker.model.SaleDetails;
import com.example.shopsneaker.retrofit.ApiService;
import com.example.shopsneaker.retrofit.RetrofitClient;
import com.example.shopsneaker.utils.Utils;
import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.List;
import io.reactivex.rxjava3.disposables.CompositeDisposable;

public class AddProductSales extends AppCompatActivity {
    private RecyclerView recyclerviewSales;
    private Toolbar toolbarSales;
    private AddSaleDetailsAdapter salesDetailsAdapter;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private ApiService apiBanGiay;
    private List<SaleDetails> listSales;
    private int countList;
    private TextView txtcountsp;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sales);
        initUi();
        actionToolbar();
        addProductSales();
    }
    private void actionToolbar() {
        setSupportActionBar(toolbarSales);
        ActionBar actionBar=getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        toolbarSales.setNavigationOnClickListener(v ->{
            Utils.ListSaleDetails.clear();
            finish();
        });
    }

    public void initUi() {
        apiBanGiay = RetrofitClient.getInstance(Utils.BASE_URL).create(ApiService.class);
        toolbarSales = findViewById(R.id.toolbarSales);
        toolbarSales.setTitle("Thêm sản phẩm sales");
        txtcountsp = findViewById(R.id.txtcountsp);
        //recyclerview
        recyclerviewSales = findViewById(R.id.recyclerViewSales);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerviewSales.setLayoutManager(linearLayoutManager);
        listSales = new ArrayList<>();
        salesDetailsAdapter =new AddSaleDetailsAdapter(getApplicationContext(),listSales);
        recyclerviewSales.setAdapter(salesDetailsAdapter);

    }

    private void addProductSales() {
        compositeDisposable.add(apiBanGiay.getShoesNotSales()
                .subscribeOn(io.reactivex.rxjava3.schedulers.Schedulers.io())
                .observeOn(io.reactivex.rxjava3.android.schedulers.AndroidSchedulers.mainThread())
                .subscribe(
                        salesModel -> {
                            if (salesModel.isSuccess()){
                                listSales = salesModel.getResult();
                                salesDetailsAdapter = new AddSaleDetailsAdapter(getApplicationContext(),listSales);
                                recyclerviewSales.setAdapter(salesDetailsAdapter);
                                txtcountsp.setText("Tổng: "+listSales.size());
                                countList = listSales.size();
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
        MenuItem item = menu.findItem(R.id.menuDelete);
        MenuItem item1 = menu.findItem(R.id.menuCehckedAll);
        item.setVisible(false);
        item1.setVisible(false);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.menuAdd:
                int salesId = getIntent().getIntExtra("salesid",-1);
                int accountid = Utils.user_current.getAccountid();
                Log.d("test", new Gson().toJson(Utils.ListSaleDetails));
                compositeDisposable.add(apiBanGiay.insertSaleDetails(salesId,accountid,new Gson().toJson(Utils.ListSaleDetails))
                        .subscribeOn(io.reactivex.rxjava3.schedulers.Schedulers.io())
                        .observeOn(io.reactivex.rxjava3.android.schedulers.AndroidSchedulers.mainThread())
                        .subscribe(
                                salesModel -> {
                                    Toast.makeText(this, "Thành Công", Toast.LENGTH_SHORT).show();
                                    Utils.ListSaleDetails.clear();
                                    finish();
                                    startActivity(getIntent());
                                },
                                throwable -> {
                                    android.widget.Toast.makeText(getApplicationContext(), "Khong ket noi duoc voi server", android.widget.Toast.LENGTH_LONG).show();
                                }
                        ));
                break;
        }
        return super.onOptionsItemSelected(item);

    }
}
