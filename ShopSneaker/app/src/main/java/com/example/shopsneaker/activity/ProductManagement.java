package com.example.shopsneaker.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shopsneaker.R;
import com.example.shopsneaker.adapter.ShoesAdapter;
import com.example.shopsneaker.model.EventBus.SuaXoaEvent;
import com.example.shopsneaker.model.Shoes;
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

public class ProductManagement extends AppCompatActivity {
    RecyclerView recyclerViewSp;
    ShoesAdapter sanPhamMoiAdapter;
    List<Shoes> arrSp;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    ApiService apiBanGiay;
    ImageView imgAdd;
    Shoes shoesUpDe;
    Toolbar toolbarProduct;
    TextView txtcountsp;
    int brandId;
    String title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_managment);
        apiBanGiay = RetrofitClient.getInstance(Utils.BASE_URL).create(ApiService.class);
        brandId = getIntent().getIntExtra("brandId",1);
        title = getIntent().getStringExtra("Title");
        Init();
        actionToolbar();
        InitControl();
        getSP();
    }

    private void actionToolbar() {
        setSupportActionBar(toolbarProduct);
        androidx.appcompat.app.ActionBar actionBar=getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        toolbarProduct.setNavigationOnClickListener(v -> {
            Intent intent = new Intent(ProductManagement.this, QLSPActivity.class);
            startActivity(intent);
            finish();
        });
    }

    private void InitControl() {
        imgAdd.setOnClickListener(v -> {
            Intent intent = new Intent(ProductManagement.this, AddProduct.class);
            intent.putExtra("brandId",brandId);
            startActivity(intent);
            finish();
        });
    }
    private void getSP(){
        compositeDisposable.add(apiBanGiay.getBrandStatisticalDetails(brandId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        sanPhamMoiModel -> {
                            if(sanPhamMoiModel.isSuccess()){
                                arrSp = sanPhamMoiModel.getResult();
                                sanPhamMoiAdapter = new ShoesAdapter(getApplicationContext(),arrSp);
                                recyclerViewSp.setAdapter(sanPhamMoiAdapter);
                                txtcountsp.setText("Tổng: "+arrSp.size());
                            }
                        },
                        throwable -> {
                            Toast.makeText(getApplicationContext(),"Không có kết nối", Toast.LENGTH_LONG).show();
                        }
                ));
    }
    private void Init() {
        txtcountsp = findViewById(R.id.txtcountsp);
        toolbarProduct = findViewById(R.id.toolbarProduct);
        toolbarProduct.setTitle("Sản phẩm "+title);
        recyclerViewSp = findViewById(R.id.recyclerViewItems);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerViewSp.setLayoutManager(layoutManager);
        recyclerViewSp.setHasFixedSize(true);
        arrSp= new ArrayList<>();
        imgAdd = findViewById(R.id.imgAdd);
        sanPhamMoiAdapter = new ShoesAdapter(getApplicationContext(),arrSp);
        recyclerViewSp.setAdapter(sanPhamMoiAdapter);
    }
    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        if(item.getTitle().equals("sửa")) {
            suaSanPham();
        }

        return super.onContextItemSelected(item);
    }

    private void suaSanPham() {
        Intent intent = new Intent(ProductManagement.this, AddProduct.class);
        intent.putExtra("sua",shoesUpDe);
        intent.putExtra("brandId",brandId);
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
    }
    @Subscribe(sticky = true,threadMode = ThreadMode.MAIN)
    public void eventSuaXoa(SuaXoaEvent event){
        if (event != null){
            shoesUpDe = event.getShoes();
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