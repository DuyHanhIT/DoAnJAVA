package com.example.shopsneaker.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.example.shopsneaker.R;
import com.example.shopsneaker.adapter.OrderManagerAdapter;
import com.example.shopsneaker.model.EventBus.SuaOrder;
import com.example.shopsneaker.model.Order;
import com.example.shopsneaker.retrofit.ApiService;
import com.example.shopsneaker.retrofit.RetrofitClient;
import com.example.shopsneaker.utils.Utils;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import io.reactivex.rxjava3.disposables.CompositeDisposable;

public class OrderManagerActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private RecyclerView recyclerviewOrder;
    private Toolbar toolbarOrder;
    private int statusid;
    private String Title;
    androidx.appcompat.widget.SearchView searchView;

    private OrderManagerAdapter orderManagerAdapter;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private ApiService apiBanGiay;
    private List<Order> mangdonhang;
    private Order orderUp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_manager);
        apiBanGiay = RetrofitClient.getInstance(Utils.BASE_URL).create(ApiService.class);
        statusid = getIntent().getIntExtra("status",0);
        Title = getIntent().getStringExtra("Title");
        Init();
        actionToolbar();
        getDonHang();

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

                orderManagerAdapter.getFilter().filter(query);

                return false;

            }

            @Override
            public boolean onQueryTextChange(String newText) {
                orderManagerAdapter.getFilter().filter(newText);

                return false;
            }
        });
        return true;
    }

    private void actionToolbar() {
        setSupportActionBar(toolbarOrder);
        androidx.appcompat.app.ActionBar actionBar=getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle(Title);
        toolbarOrder.setNavigationOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), QLDonHangActivity.class);
            startActivity(intent);
            finish();
        });
    }
    private void getDonHang() {
        compositeDisposable.add(apiBanGiay.getOrder(statusid)
                .subscribeOn(io.reactivex.rxjava3.schedulers.Schedulers.io())
                .observeOn(io.reactivex.rxjava3.android.schedulers.AndroidSchedulers.mainThread())
                .subscribe(
                        orderModel -> {
                            if (orderModel.isSuccess()){
                                mangdonhang = orderModel.getResult();
                                orderManagerAdapter = new OrderManagerAdapter(getApplicationContext(),mangdonhang);
                                recyclerviewOrder.setAdapter(orderManagerAdapter);
                            }
                        },
                        throwable -> {
                            android.widget.Toast.makeText(getApplicationContext(), "Khong ket noi duoc voi server", android.widget.Toast.LENGTH_LONG).show();
                        }
                ));
    }

    private void Init() {
        toolbarOrder = findViewById(R.id.toolbarOrderManament);
        //recyclerview
        recyclerviewOrder = findViewById(R.id.recyclerviewOrderManager);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerviewOrder.setLayoutManager(layoutManager);
        recyclerviewOrder.setHasFixedSize(true);
        mangdonhang = new java.util.ArrayList<>();
        orderManagerAdapter =new OrderManagerAdapter(getApplicationContext(),mangdonhang);
        recyclerviewOrder.setAdapter(orderManagerAdapter);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String choice = parent.getItemAtPosition(position).toString();
        Toast.makeText(getApplicationContext(),choice, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
    @Subscribe(sticky = true,threadMode = ThreadMode.MAIN)
    public void eventSuaXoa(SuaOrder event){
        if (event != null){
            orderUp = event.getOrder();
        }
    }
}