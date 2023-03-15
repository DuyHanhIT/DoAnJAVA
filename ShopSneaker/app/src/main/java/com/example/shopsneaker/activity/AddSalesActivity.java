package com.example.shopsneaker.activity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.shopsneaker.R;
import com.example.shopsneaker.databinding.ActivityAddsalesBinding;
import com.example.shopsneaker.model.Sales;
import com.example.shopsneaker.retrofit.ApiService;
import com.example.shopsneaker.retrofit.RetrofitClient;
import com.example.shopsneaker.utils.Utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class AddSalesActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private Button button;
    private EditText startDay, endDay;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private ApiService apiBanGiay;
    private ActivityAddsalesBinding binding;
    boolean flag = false;
    private Sales salesUD;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddsalesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initUi();
        actionToolbar();
        EventClick();
        Intent intent = getIntent();
        salesUD = (Sales) intent.getSerializableExtra("sua");
        if(salesUD == null){
            //them
            flag = false;
        }else {
            //sua
            flag = true;
            binding.toolbarAdd.setTitle("Sửa mục khuyến mãi");
            //show data
            binding.salesName.setText(salesUD.getSalesname());
            binding.percent.setText(salesUD.getPercent()+"%");
            binding.content.setText(salesUD.getContent());
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            binding.startDay.setText(simpleDateFormat.format(salesUD.getStartday()));
            binding.endDay.setText(simpleDateFormat.format(salesUD.getEndday()));
        }
    }
    private void actionToolbar() {
        setSupportActionBar(toolbar);
        ActionBar actionBar=getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(),SalesActivity.class);
            startActivity(intent);
            finish();
        });
    }

    public void initUi() {
        apiBanGiay = RetrofitClient.getInstance(Utils.BASE_URL).create(ApiService.class);
        toolbar = findViewById(R.id.toolbarAdd);
        startDay = findViewById(R.id.startDay);
        endDay = findViewById(R.id.endDay);
        button = findViewById(R.id.btnAdd);
    }

    private void setStartDay(){
        final Calendar calendar = Calendar.getInstance();
        int ngay = calendar.get(Calendar.DATE);
        int thang = calendar.get(Calendar.MONTH);
        int nam = calendar.get(Calendar.YEAR);
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, (view, year, month, dayOfMonth) -> {
            calendar.set(year,month,dayOfMonth);
            SimpleDateFormat simpleDateFormat =  new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            startDay.setText(simpleDateFormat.format(calendar.getTime()));
        }, nam,thang,ngay);
        datePickerDialog.show();
    }

    private void setEndDay(){
        final Calendar calendar = Calendar.getInstance();
        int ngay = calendar.get(Calendar.DATE);
        int thang = calendar.get(Calendar.MONTH);
        int nam = calendar.get(Calendar.YEAR);
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, (view, year, month, dayOfMonth) -> {
            calendar.set(year,month,dayOfMonth);
            SimpleDateFormat simpleDateFormat =  new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            endDay.setText(simpleDateFormat.format(calendar.getTime()));
        }, nam,thang,ngay);
        datePickerDialog.show();
    }

    private void EventClick(){
        startDay.setOnClickListener(v -> setStartDay());
        endDay.setOnClickListener(v -> setEndDay());
        button.setOnClickListener(v-> {
            if(flag == false){
                addSales();
            }else {
                upSales();
            }
        });
    }

    private void addSales() {
        String str_ten = binding.salesName.getText().toString().trim();
        String str_gia = binding.percent.getText().toString().trim();
        String str_ngaybd = binding.startDay.getText().toString().trim();
        String str_ngaykt = binding.endDay.getText().toString().trim();
        String str_mota = binding.content.getText().toString().trim();
        if(TextUtils.isEmpty(str_ten) || TextUtils.isEmpty(str_gia) || TextUtils.isEmpty(str_ngaybd) || TextUtils.isEmpty(str_ngaykt)||TextUtils.isEmpty(str_mota)){
            Toast.makeText(getApplicationContext(),"Nhập đầy đủ thông tin",Toast.LENGTH_LONG).show();
        }else {
            compositeDisposable.add(apiBanGiay.insertSales(str_ten,str_ngaybd,str_ngaykt,str_mota,str_gia)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            addModel -> {
                                Toast.makeText(this, "Thành Công", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getApplicationContext(), SalesActivity.class);
                                startActivity(intent);
                                finish();
                            },
                            throwable -> {
                                android.widget.Toast.makeText(getApplicationContext(), "Khong ket noi duoc voi server", android.widget.Toast.LENGTH_LONG).show();
                            }
                    ));
        }
    }

    private void upSales() {
        String str_ten = binding.salesName.getText().toString().trim();
        String str_gia = binding.percent.getText().toString().trim();
        String str_ngaybd = binding.startDay.getText().toString().trim();
        String str_ngaykt = binding.endDay.getText().toString().trim();
        String str_mota = binding.content.getText().toString().trim();
        if(TextUtils.isEmpty(str_ten) || TextUtils.isEmpty(str_gia) || TextUtils.isEmpty(str_ngaybd) || TextUtils.isEmpty(str_ngaykt)||TextUtils.isEmpty(str_mota)){
            Toast.makeText(getApplicationContext(),"Nhập đầy đủ thông tin",Toast.LENGTH_LONG).show();
        }else {
            compositeDisposable.add(apiBanGiay.updateSales(salesUD.getSalesid(),str_ten,str_ngaybd,str_ngaykt,str_mota,str_gia)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            addModel -> {
                                Toast.makeText(this, "Thành Công", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getApplicationContext(), SalesActivity.class);
                                startActivity(intent);
                                finish();
                            },
                            throwable -> {
                                android.widget.Toast.makeText(getApplicationContext(), "Khong ket noi duoc voi server", android.widget.Toast.LENGTH_LONG).show();
                            }
                    ));
        }
    }

}