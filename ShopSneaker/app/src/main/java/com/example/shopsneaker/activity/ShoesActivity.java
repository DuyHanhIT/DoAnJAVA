package com.example.shopsneaker.activity;

import static com.example.shopsneaker.R.id.tab_layoutShoes;
import static com.example.shopsneaker.R.id.viewPager2Shoes;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;


public class ShoesActivity extends AppCompatActivity {
    private androidx.appcompat.widget.Toolbar toolbar;
    private com.google.android.material.tabs.TabLayout tableLayout;
    private androidx.viewpager2.widget.ViewPager2 viewPager;
    private com.example.shopsneaker.adapter.ShoesPagerAdapter shoesPagerAdapter;
    String brandName;
    androidx.appcompat.widget.SearchView searchView;
    @Override
    protected void onCreate(android.os.Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.example.shopsneaker.R.layout.activity_shoes);
        Init();

        /*ShoesFragment brandInfo = new ShoesFragment();
        brandInfo.setArguments(getIntent().getExtras());
        FragmentTransaction trans =
                getSupportFragmentManager().beginTransaction();
        trans.replace(viewPager2Shoes, brandInfo);
        trans.commit();*/
        Bundle bundle = getIntent().getExtras();
        com.example.shopsneaker.model.Brand obj =(com.example.shopsneaker.model.Brand) bundle.getSerializable("KEY_SER_SV");
        brandName = obj.getBrandname();
        ActionToolBar();
    }
    private void ActionToolBar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(brandName);
        toolbar.setNavigationOnClickListener(view -> {
            android.content.Intent intent =new android.content.Intent(getApplicationContext(), com.example.shopsneaker.activity.MainActivity.class);
            startActivity(intent);
            finish();
        });

    }

    @Override
    public boolean onOptionsItemSelected(@androidx.annotation.NonNull android.view.MenuItem item) {
        switch (item.getItemId()){
            case com.example.shopsneaker.R.id.menugiohang:
                android.content.Intent intent =new android.content.Intent(getApplicationContext(), com.example.shopsneaker.activity.GioHangActivity.class);
                startActivity(intent);
                break;
            case com.example.shopsneaker.R.id.profile:
                android.content.Intent intentLogout =new android.content.Intent(getApplicationContext(), com.example.shopsneaker.activity.ProfileActivity.class);
                startActivity(intentLogout);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
    private void Init() {
        try {
            toolbar = findViewById(com.example.shopsneaker.R.id.Toolbar);
            tableLayout= findViewById(tab_layoutShoes);
            viewPager= findViewById(viewPager2Shoes);
            shoesPagerAdapter = new com.example.shopsneaker.adapter.ShoesPagerAdapter(this);
            viewPager.setAdapter(shoesPagerAdapter);
            new com.google.android.material.tabs.TabLayoutMediator(tableLayout, viewPager, (tab, position) -> {

                switch (position) {
                    case 0:
                        tab.setText("Bán chạy");
                        break;
                    case 1:
                        tab.setText("Mới");
                        break;
                    case 2:
                        tab.setText("Giá ^");
                        break;
                    case 3:
                        tab.setText("Khuyến mãi");
                        break;

                }

            }).attach();
        }catch (Exception ex){

        }

    }
}