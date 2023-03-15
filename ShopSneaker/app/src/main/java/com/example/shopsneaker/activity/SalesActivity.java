package com.example.shopsneaker.activity;

import android.content.Intent;
import android.os.Bundle;
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
import com.example.shopsneaker.adapter.SalesAdapter;
import com.example.shopsneaker.model.EventBus.UDSales;
import com.example.shopsneaker.model.Sales;
import com.example.shopsneaker.retrofit.ApiService;
import com.example.shopsneaker.retrofit.RetrofitClient;
import com.example.shopsneaker.utils.Utils;


import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class SalesActivity extends AppCompatActivity {
    private RecyclerView recyclerviewSales;
    private Toolbar toolbarSales;
    private SalesAdapter salesAdapter;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private ApiService apiBanGiay;
    private List<Sales> listSales;
    private TextView txtcountsp;
    //private ImageView imageView,btnDelete;
    private Sales salesUD;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sales);
        initUi();
        actionToolbar();
        getSales();
    }
    private void actionToolbar() {
        setSupportActionBar(toolbarSales);
        ActionBar actionBar=getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        toolbarSales.setNavigationOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(),AdminActivity.class);
            startActivity(intent);
            finish();
        });
    }

    public void initUi() {
        apiBanGiay = RetrofitClient.getInstance(Utils.BASE_URL).create(ApiService.class);
        toolbarSales = findViewById(R.id.toolbarSales);
        txtcountsp = findViewById(R.id.txtcountsp);
        //recyclerview
        recyclerviewSales = findViewById(R.id.recyclerViewSales);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerviewSales.setLayoutManager(linearLayoutManager);
        listSales = new ArrayList<>();
        salesAdapter =new SalesAdapter(getApplicationContext(),listSales);
        recyclerviewSales.setAdapter(salesAdapter);
    }

    private void getSales() {
        compositeDisposable.add(apiBanGiay.getSales()
                .subscribeOn(io.reactivex.rxjava3.schedulers.Schedulers.io())
                .observeOn(io.reactivex.rxjava3.android.schedulers.AndroidSchedulers.mainThread())
                .subscribe(
                        salesModel -> {
                            if (salesModel.isSuccess()){
                                listSales = salesModel.getResult();
                                salesAdapter = new SalesAdapter(getApplicationContext(),listSales);
                                recyclerviewSales.setAdapter(salesAdapter);
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
                Intent intent = new Intent(getApplicationContext(), AddSalesActivity.class);
                startActivity(intent);
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        if(item.getTitle().equals("sửa")) {
            updateSales();
        }
        if(item.getTitle().equals("xóa")) {
            deSales();
        }

        return super.onContextItemSelected(item);
    }
    private void updateSales() {
        Intent intent = new Intent(getApplicationContext(), AddSalesActivity.class);
        intent.putExtra("sua",salesUD);
        startActivity(intent);
    }

    private void deSales() {
        compositeDisposable.add(apiBanGiay.deleteSales(salesUD.getSalesid())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        addModel -> {
                            Toast.makeText(this, "Thành Công", Toast.LENGTH_SHORT).show();
                            finish();
                            startActivity(getIntent());
                        },
                        throwable -> {
                            android.widget.Toast.makeText(getApplicationContext(), "Khong ket noi duoc voi server", android.widget.Toast.LENGTH_LONG).show();
                        }
                ));
    }

    @Override
    protected void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
    }
    @Subscribe(sticky = true,threadMode = ThreadMode.MAIN)
    public void eventSuaXoa(UDSales event){
        if (event != null){
            salesUD = event.getSales();
        }
    }
    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }
}
