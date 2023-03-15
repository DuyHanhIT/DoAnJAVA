package com.example.shopsneaker.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.shopsneaker.R;
import com.example.shopsneaker.model.Market;
import com.example.shopsneaker.retrofit.ApiService;
import com.example.shopsneaker.retrofit.RetrofitClient;
import com.example.shopsneaker.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class UpdatePostsActivity extends AppCompatActivity {
    TextView txtname,txtshoesname,txtprice,txtsize,txtdescription;
    ImageView img1,img2,img3,img4,img5,img6;
    Spinner spnStatus;
    Button btnAccept;
    Market postUp;
    private int id,id2;
    private String status,name;
    Toolbar toolbar;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    ApiService apiBanGiay;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_posts);
        apiBanGiay = RetrofitClient.getInstance(Utils.BASE_URL).create(ApiService.class);
        Init();
        //actionToolbar();
        Intent intent = getIntent();
        postUp = (Market) intent.getSerializableExtra("updateposts");
        id = postUp.getId();

        txtname.setText(postUp.getName());
        txtshoesname.setText(postUp.getShoesname());
        txtprice.setText(postUp.getPrice()+" USD");
        txtsize.setText("Size: "+postUp.getSize());
        txtdescription.setText(postUp.getDescription());
        InitData();
    }

    private void InitData() {
        List<String> item = new ArrayList<>();
        item.add("Đang chờ duyệt");
        item.add("Đã duyệt");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(UpdatePostsActivity.this,android.R.layout.simple_spinner_item,item);
        spnStatus.setAdapter(adapter);
        spnStatus.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                status = spnStatus.getSelectedItem().toString().trim();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        btnAccept.setOnClickListener(v -> CapNhat());
    }

    private void actionToolbar() {
        setSupportActionBar(toolbar);
        androidx.appcompat.app.ActionBar actionBar=getSupportActionBar();
        //actionBar.setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(v -> finish());
    }

    private void Init() {
        toolbar = findViewById(R.id.toolbarupdatePosts);
        txtname = findViewById(R.id.txtCustomerName2);
        txtshoesname = findViewById(R.id.txtShoesName2);
        txtprice = findViewById(R.id.txtPrice2);
        txtsize = findViewById(R.id.txtSize2);
        txtdescription = findViewById(R.id.txtDescription3);
        spnStatus = findViewById(R.id.spnstatus1);
        btnAccept = findViewById(R.id.btnaccept);
    }
    private void CapNhat(){
        compositeDisposable.add(apiBanGiay.updatePosts(id, status)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        marketModel -> {
                            if(marketModel.isSuccess()){
                                Toast.makeText(UpdatePostsActivity.this,"Cập nhật thành công",Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(getApplicationContext(), AdminActivity.class);
                                startActivity(intent);
                            }else {
                                Toast.makeText(UpdatePostsActivity.this,"Cập nhật thất bại",Toast.LENGTH_LONG).show();
                            }
                        },
                        throwable -> {
                            Toast.makeText(UpdatePostsActivity.this,"Không kết nối được server",Toast.LENGTH_LONG).show();
                        }
                ));
    }
}