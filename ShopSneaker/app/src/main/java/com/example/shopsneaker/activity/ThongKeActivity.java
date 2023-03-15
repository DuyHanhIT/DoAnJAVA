package com.example.shopsneaker.activity;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import com.example.shopsneaker.R;
import com.example.shopsneaker.retrofit.ApiService;

public class ThongKeActivity extends AppCompatActivity {
    android.widget.ListView listView;
    androidx.appcompat.widget.Toolbar toolbar;
    java.util.ArrayList<com.example.shopsneaker.model.Admin> arrayList;
    com.example.shopsneaker.adapter.AdminAdapter adminAdapter;

    ApiService apiBanGiay;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.example.shopsneaker.R.layout.activity_thong_ke);
        apiBanGiay = com.example.shopsneaker.retrofit.RetrofitClient.getInstance(com.example.shopsneaker.utils.Utils.BASE_URL).create(ApiService.class);
        Init();
        ActionBar();
        EventClick();
    }

    private void ActionBar(){
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(v -> finish());
    }
    private void Init() {
        toolbar = findViewById(R.id.toolbarThongKe);
        listView = findViewById(R.id.lv_thongke);
        arrayList = new java.util.ArrayList<>();
        arrayList.add(new com.example.shopsneaker.model.Admin("Thống kê theo tháng", "https://img.icons8.com/external-xnimrodx-blue-xnimrodx/344/external-statistic-content-creator-xnimrodx-blue-xnimrodx-2.png"));
        arrayList.add(new com.example.shopsneaker.model.Admin("Thống kê theo năm", "https://img.icons8.com/external-inipagistudio-mixed-inipagistudio/344/external-statistic-finances-inipagistudio-mixed-inipagistudio.png"));
        arrayList.add(new com.example.shopsneaker.model.Admin("Thông kê doanh thu từng sản phẩm","https://img.icons8.com/external-itim2101-lineal-color-itim2101/344/external-statistics-network-technology-itim2101-lineal-color-itim2101.png"));
        adminAdapter = new com.example.shopsneaker.adapter.AdminAdapter(ThongKeActivity.this, com.example.shopsneaker.R.layout.item_manager,arrayList);
        listView.setAdapter(adminAdapter);

    }
    private void EventClick() {
        listView.setOnItemClickListener((parent, view, position, id) -> {
            if(position == 0){
                android.content.Intent intent = new android.content.Intent();
                intent.setClass(ThongKeActivity.this, StatisticsMonth.class);
                startActivity(intent);
            }
            if(position == 1){
                android.content.Intent intent = new android.content.Intent();
                intent.setClass(ThongKeActivity.this, StatisticsByYear.class);
                startActivity(intent);
            }
            if(position == 2){
                android.content.Intent intent = new android.content.Intent();
                intent.setClass(ThongKeActivity.this, StatisticsById.class);
                startActivity(intent);
            }
        });
    }
}