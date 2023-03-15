package com.example.shopsneaker.activity;

import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shopsneaker.R;
import com.example.shopsneaker.adapter.OrderDetailsAdapter;
import com.example.shopsneaker.model.Order;
import com.example.shopsneaker.model.OrderDetails;
import com.example.shopsneaker.retrofit.ApiService;
import com.example.shopsneaker.retrofit.RetrofitClient;
import com.example.shopsneaker.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class UpdateOrder extends AppCompatActivity {
    TextView orderId;
    int orderid;
    Spinner spnStatus;
    Button btnAccept;
    Order orderUp;
    OrderDetailsAdapter orderDetailsAdapter;
    List<OrderDetails> list;
    RecyclerView recyclerView;
    private int OrderID;
    private String tt;
    private String status;
    int statusid;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    ApiService apiBanGiay;
    Toolbar toolbarOrder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_order);
        Init();
        actionToolbar();
        InitData();
        getSanPham();
    }

    private void actionToolbar() {
        setSupportActionBar(toolbarOrder);
        androidx.appcompat.app.ActionBar actionBar=getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        toolbarOrder.setNavigationOnClickListener(v -> {
            finish();
        });
    }

    private void InitData() {
        List<String> item = new ArrayList<>();
        item.add("Đang chờ duyệt");
        item.add("Đã duyệt");
        item.add("Đang giao");
        item.add("Giao thành công");
        item.add("Đã hủy");
        if (statusid==1){
            btnAccept.setVisibility(INVISIBLE);
            btnAccept.setEnabled(false);
        }
        if (statusid==2){
            item.remove(0);
        }else if (statusid==3){
            item.remove(0);
            item.remove(0);
        }


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(UpdateOrder.this,android.R.layout.simple_spinner_item,item);
        spnStatus.setAdapter(adapter);
        int spinnerPosition = adapter.getPosition(tt);
        spnStatus.setSelection(spinnerPosition);
        spnStatus.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                status = spnStatus.getSelectedItem().toString().trim();
                if (status.equals("Đang chờ duyệt") ||status.equals("Giao thành công")||status.equals("Đã hủy")){
                    btnAccept.setVisibility(INVISIBLE);
                    btnAccept.setEnabled(false);
                }else {
                    btnAccept.setVisibility(VISIBLE);
                    btnAccept.setEnabled(true);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        btnAccept.setOnClickListener(v -> {
            if(tt.equals(status)){
            btnAccept.setEnabled(false);
            }else{
                    CapNhat();
            }
        });

    }

    private void getSanPham() {
        compositeDisposable.add(apiBanGiay.getChiTietDH(orderid)
                .subscribeOn(io.reactivex.rxjava3.schedulers.Schedulers.io())
                .observeOn(io.reactivex.rxjava3.android.schedulers.AndroidSchedulers.mainThread())
                .subscribe(
                        orderDetailsModel -> {
                            if (orderDetailsModel.isSuccess()){
                                list = orderDetailsModel.getResult();
                                orderDetailsAdapter = new OrderDetailsAdapter(getApplicationContext(),list);
                                recyclerView.setAdapter(orderDetailsAdapter);
                            }
                        },
                        throwable -> {
                            android.widget.Toast.makeText(getApplicationContext(), "Khong ket noi duoc voi server", android.widget.Toast.LENGTH_LONG).show();
                        }
                ));
    }

    private void Init() {
        Intent intent = getIntent();
        orderUp = (Order) intent.getSerializableExtra("update");
        OrderID = ((Order) intent.getSerializableExtra("update")).getOrderid();
        orderid = orderUp.getOrderid();
        statusid = orderUp.getStatusid();
        tt = orderUp.getStatusname();
        apiBanGiay = RetrofitClient.getInstance(Utils.BASE_URL).create(ApiService.class);
        toolbarOrder = findViewById(R.id.toorupdateorder);
        orderId = findViewById(R.id.OrderId);
        orderId.setText("MDH: "+orderid);
        spnStatus = findViewById(R.id.spnstatus);
        btnAccept = findViewById(R.id.btnaccept);
        if(tt.equals("Giao thành công") || tt.equals("Đã hủy")){
            btnAccept.setVisibility(INVISIBLE);
            spnStatus.setVisibility(INVISIBLE);
        }
        recyclerView = findViewById(R.id.rcvOrderDetails);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        list = new ArrayList<>();
        orderDetailsAdapter =new OrderDetailsAdapter(getApplicationContext(),list);
        recyclerView.setAdapter(orderDetailsAdapter);

    }



    private void CapNhat(){
        compositeDisposable.add(apiBanGiay.updateOrder(OrderID, status)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        updateOrderModel -> {
                            if(updateOrderModel.isSuccess()){
                                Toast.makeText(UpdateOrder.this,"Cập nhật thành công",Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(getApplicationContext(), QLDonHangActivity.class);
                                startActivity(intent);
                                finish();
                            }else {
                                Toast.makeText(UpdateOrder.this,"Cập nhật thất bại",Toast.LENGTH_LONG).show();
                            }
                        },
                        throwable -> {
                            Toast.makeText(UpdateOrder.this,"Không kết nối được server",Toast.LENGTH_LONG).show();
                        }
                ));
    }
}