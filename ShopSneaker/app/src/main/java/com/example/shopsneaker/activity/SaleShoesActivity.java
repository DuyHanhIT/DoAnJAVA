package com.example.shopsneaker.activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.example.shopsneaker.Fragment.ShoesSalesFragment;
import com.example.shopsneaker.R;
import com.example.shopsneaker.R.id;
import com.example.shopsneaker.adapter.ShoesSalePagerAdapter;
import com.example.shopsneaker.model.Sales;


public class SaleShoesActivity extends AppCompatActivity {
    private androidx.appcompat.widget.Toolbar toolbarShoesSale;
    private com.google.android.material.tabs.TabLayout tableLayoutShoesSale;
    private ViewPager2 viewPagerShoesSale;
    private ShoesSalePagerAdapter shoesPagerAdapterShoesSale;
    public static int saleid;
    String salename;
    @Override
    protected void onCreate(@androidx.annotation.Nullable android.os.Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sale_shoes);
        Intent intent = getIntent();
        saleid = intent.getIntExtra("saleid",0);
        salename = intent.getStringExtra("salename");


        Sales sv = new Sales();
        sv.setSalesid(saleid);
    //truyen qua bundle
        Bundle bundle = new Bundle();
        bundle.putSerializable("object_sale", sv);
        ShoesSalesFragment svInfo = new ShoesSalesFragment();
        svInfo.setArguments(bundle);
        androidx.fragment.app.FragmentTransaction trans =
                getSupportFragmentManager().beginTransaction();
        trans.replace(id.lnSaleShoess, svInfo);
        trans.commit();


        Init();
        ActionToolBar();
    }
    @Override
    public boolean onOptionsItemSelected(@androidx.annotation.NonNull android.view.MenuItem item) {
        switch (item.getItemId()){
            case id.menugiohang:
                android.content.Intent intent =new android.content.Intent(getApplicationContext(), com.example.shopsneaker.activity.GioHangActivity.class);
                startActivity(intent);
                break;
            case id.profile:
                android.content.Intent intentLogout =new android.content.Intent(getApplicationContext(), com.example.shopsneaker.activity.ProfileActivity.class);
                startActivity(intentLogout);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
    private void ActionToolBar() {
        setSupportActionBar(toolbarShoesSale);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        if (saleid==0){
            getSupportActionBar().setTitle(salename);
        }else {
            getSupportActionBar().setTitle("Khuyễn mãi ("+salename+")");
        }
        toolbarShoesSale.setNavigationOnClickListener(view -> {
            android.content.Intent intent =new android.content.Intent(getApplicationContext(), com.example.shopsneaker.activity.MainActivity.class);
            startActivity(intent);
            finish();
        });

    }
    private void Init() {
        try {

            toolbarShoesSale = findViewById(id.ToolbarSale);
            tableLayoutShoesSale = findViewById(R.id.tab_layoutShoesSale);
            viewPagerShoesSale= findViewById(R.id.viewPager2ShoesSale);
            shoesPagerAdapterShoesSale = new ShoesSalePagerAdapter(this);
            viewPagerShoesSale.setAdapter(shoesPagerAdapterShoesSale);
            new com.google.android.material.tabs.TabLayoutMediator(tableLayoutShoesSale, viewPagerShoesSale, (tab, position) -> {

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


                }

            }).attach();
        }catch (Exception ex){

        }
    }
    /*Toolbar toolbar;
    RecyclerView recyclerView;
    ApiBanGiay apiBanGiay;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    List<Shoes> shoesList;
    SearchView searchView;
    private ShoesAdapter shoesAdapter;
    com.example.shopsneaker.model.SearchHistoryModel searchHistoryModel;
    android.widget.TextView txtcountsp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sale_shoes);
        apiBanGiay = RetrofitClient.getInstance(Utils.BASE_URL).create(ApiBanGiay.class);
        Init();
        ActionToolBar();
        getSaleShoes();
    }
    private void getSaleShoes() {
        compositeDisposable.add(apiBanGiay.getSaleShoes()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        shoesModel -> {
                            if (shoesModel.isSuccess()){
                                shoesList = shoesModel.getResult();

                                shoesAdapter = new ShoesAdapter(getApplicationContext(),shoesList);
                                recyclerView.setAdapter(shoesAdapter);
                                int countSP= shoesList.size();
                                txtcountsp.setText("("+countSP+" sản phẩm)");
                            }
                        },
                        throwable -> {
                            Toast.makeText(getApplicationContext(),"Load sale shoes fail", Toast.LENGTH_LONG).show();

                        }
                ));
    }

    @Override
    public boolean onOptionsItemSelected(@androidx.annotation.NonNull android.view.MenuItem item) {
        switch (item.getItemId()){
            case R.id.menugiohang:
                android.content.Intent intent =new android.content.Intent(getApplicationContext(),GioHangActivity.class);
                startActivity(intent);
                break;
            case R.id.profile:
                android.content.Intent intentLogout =new android.content.Intent(getApplicationContext(),ProfileActivity.class);
                startActivity(intentLogout);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        SearchManager searchManager =(SearchManager) getSystemService(android.content.Context.SEARCH_SERVICE);
        searchView = (SearchView) menu.findItem(R.id.menuSearch).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setMaxWidth(Integer.MAX_VALUE);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                shoesAdapter.getFilter().filter(query);
                int countSP= shoesAdapter.getItemCount();
                txtcountsp.setText("("+countSP+" sản phẩm)");
                String keyword = query.toString();
                if (shoesAdapter.getItemCount()>0) {
                    int accountid = Utils.user_current.getAccountid();
                    compositeDisposable.add(apiBanGiay.SearchHistory(accountid,keyword).subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(searchHistoryModel -> {

                                    },
                                    throwable -> {
                                        Toast.makeText(getApplicationContext(),searchHistoryModel.getMessage(), Toast.LENGTH_LONG).show();
                                    }

                            ));
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                shoesAdapter.getFilter().filter(newText);
                int countSP= shoesAdapter.getItemCount();
                txtcountsp.setText("("+countSP+" sản phẩm)");
                return false;
            }
        });
        return true;
    }
    private void ActionToolBar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(view -> {
            Intent intent =new Intent(getApplicationContext(),MainActivity.class);
            startActivity(intent);
            finish();
        });

    }
    private void Init() {
        toolbar = findViewById(R.id.ToolbarSale);
        recyclerView = findViewById(R.id.recycleviewSaleShoes);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this,2);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        shoesList = new ArrayList<>();
        txtcountsp= findViewById(com.example.shopsneaker.R.id.txtcountsp);

    }
    @Override
    public void onBackPressed() {
        if (!searchView.isIconified()){
            searchView.setIconified(true);
            return;
        }
        super.onBackPressed();
    }

    @Override
    protected void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
    }*/
}