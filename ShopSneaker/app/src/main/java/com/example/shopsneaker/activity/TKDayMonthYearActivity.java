package com.example.shopsneaker.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.shopsneaker.R;
import com.example.shopsneaker.model.TKDayMonthYear;
import com.example.shopsneaker.retrofit.ApiService;
import com.example.shopsneaker.retrofit.RetrofitClient;
import com.example.shopsneaker.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.disposables.CompositeDisposable;

public class TKDayMonthYearActivity extends AppCompatActivity {
    Toolbar toolbar;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private ApiService apiBanGiay;
    List<TKDayMonthYear> tkDayMonthYearList;
    TextView ngay,Thang,Nam,countDHNgay, countDHThang, countDHNam, countDHDTT, countDHCTT, countDHDH, countNgay, countThang,countNam,countDTT,countCTT,countDH;
    LinearLayout linearLayoutNgay, linearLayoutThang, linearLayoutNam,linearLayoutCTT, linearLayoutDTT, linearLayoutDaHuy,linearLayoutBrand,linearLayoutSP;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tkntn);
        apiBanGiay = RetrofitClient.getInstance(Utils.BASE_URL).create(ApiService.class);
        Init();
        ActionToolBar();
        GetThongKeTinhTrangDonHang();
        EventClick();
    }

    private void EventClick() {
        android.content.Intent intent = new android.content.Intent();
        linearLayoutNgay.setOnClickListener(view -> {
            intent.setClass(getApplicationContext(),StatisticsByDate.class);
            startActivity(intent);
        });
        linearLayoutThang.setOnClickListener(view -> {
            intent.setClass(getApplicationContext(),StatisticsMonth.class);
            startActivity(intent);
        });
        linearLayoutNam.setOnClickListener(view -> {
            intent.setClass(getApplicationContext(),StatisticsByYear.class);
            startActivity(intent);
        });
        linearLayoutBrand.setOnClickListener(view -> {
            intent.setClass(getApplicationContext(),BrandStatisticalActivity.class);
            startActivity(intent);
        });
        linearLayoutSP.setOnClickListener(view -> {
            intent.setClass(getApplicationContext(),StatisticsById.class);
            intent.putExtra("brandid",0);
            intent.setFlags(android.content.Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        });

    }

    private void getGiaTri() {
        String day = tkDayMonthYearList.get(0).getDay();
        String month = tkDayMonthYearList.get(0).getMonth();
        String year = tkDayMonthYearList.get(0).getYear();
        int orderday = tkDayMonthYearList.get(0).getOrderday();
        int ordermonth = tkDayMonthYearList.get(0).getOrdermonth();
        int orderyear = tkDayMonthYearList.get(0).getOrderyear();
        int orderdtt = tkDayMonthYearList.get(0).getOrderdtt();
        int orderctt = tkDayMonthYearList.get(0).getOrderctt();
        int orderdh = tkDayMonthYearList.get(0).getOrderdh();
        Double totalday = tkDayMonthYearList.get(0).getTotalday();
        Double totalmonth = tkDayMonthYearList.get(0).getTotalmonth();
        Double totalyear = tkDayMonthYearList.get(0).getTotalyear();
        Double totaldtt = tkDayMonthYearList.get(0).getTotaldtt();
        Double totalctt = tkDayMonthYearList.get(0).getTotalctt();
        Double totaldh = tkDayMonthYearList.get(0).getTotaldh();

        ngay.setText("Ngày: "+day);
        Thang.setText("Tháng: "+month);
        Nam.setText("Năm: "+year);
        countDHNgay.setText("Số ĐH: "+orderday);
        countDHThang.setText("Số ĐH: "+ordermonth);
        countDHNam.setText("Số ĐH: "+orderyear);
        countDHDTT.setText("Số ĐH: "+orderdtt);
        countDHCTT.setText("Số ĐH: "+orderctt);
        countDHDH.setText("Số ĐH: "+orderdh);
        countNgay.setText("Doanh thu: "+totalday+"USD");
        countThang.setText("Doanh thu: "+totalmonth+"USD");
        countNam.setText("Doanh thu: "+totalyear+"USD");
        countDTT.setText("Doanh thu: "+totaldtt+"USD");
        countCTT.setText("Doanh thu: "+totalctt+"USD");
        countDH.setText("Doanh thu: "+totaldh+"USD");

    }

    private void GetThongKeTinhTrangDonHang() {

        compositeDisposable.add(apiBanGiay.getTKDayMonthYear().subscribeOn(io.reactivex.rxjava3.schedulers.Schedulers.io())
                .observeOn(io.reactivex.rxjava3.android.schedulers.AndroidSchedulers.mainThread())
                .subscribe(
                        tkDayMonthYearModel -> {
                            if(tkDayMonthYearModel.isSuccess()){
                                tkDayMonthYearList = tkDayMonthYearModel.getResult();
                                getGiaTri();
                            }else {
                                android.widget.Toast.makeText(getApplicationContext(),"Thất bại", android.widget.Toast.LENGTH_LONG).show();
                            }
                        },
                        throwable -> {
                            android.widget.Toast.makeText(getApplicationContext(),"Không kết nối được server", android.widget.Toast.LENGTH_LONG).show();
                        }
                ));

    }


    private void ActionToolBar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(view -> {
            android.content.Intent intent =new android.content.Intent(getApplicationContext(),AdminActivity.class);
            startActivity(intent);
            finish();
        });

    }

    private void Init() {
        toolbar= findViewById(R.id.ToolbarQLTinhTrangDonHang);
        ngay = findViewById(R.id.ngay);
        Thang = findViewById(R.id.Thang);
        Nam = findViewById(R.id.Nam);
        //
        countDHNgay = findViewById(R.id.countDHNgay);
        countDHThang = findViewById(R.id.countDHThang);
        countDHNam = findViewById(R.id.countDHNam);
        countDHDTT = findViewById(R.id.countDHDTT);
        countDHCTT = findViewById(R.id.countDHCTT);
        countDHDH = findViewById(R.id.countDHDH);
        //
        countNgay = findViewById(R.id.countNgay);
        countThang = findViewById(R.id.countThang);
        countNam = findViewById(R.id.countNam);
        countDH = findViewById(R.id.countDH);
        countCTT = findViewById(R.id.countCTT);
        countDTT = findViewById(R.id.countDTT);
        //
        linearLayoutNgay=findViewById(R.id.linearLayoutNgay);
        linearLayoutThang= findViewById(R.id.linearLayoutThang);
        linearLayoutNam = findViewById(R.id.linearLayoutNam);
        linearLayoutCTT = findViewById(R.id.linearLayoutCTT);
        linearLayoutDTT = findViewById(R.id.linearLayoutDTT);
        linearLayoutDaHuy = findViewById(R.id.linearLayoutDH);
        linearLayoutBrand= findViewById(R.id.linearLayoutBrand);
        linearLayoutSP = findViewById(com.example.shopsneaker.R.id.linearLayoutSP);

        tkDayMonthYearList = new ArrayList<>();
    }
}