package com.example.shopsneaker.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.shopsneaker.retrofit.ApiService;

public class AccountManagerActivity extends AppCompatActivity {
    androidx.appcompat.widget.Toolbar toolbarAccountManager;
    androidx.recyclerview.widget.RecyclerView recyclerViewAccountManager;
    ApiService apiBanGiay;
    io.reactivex.rxjava3.disposables.CompositeDisposable compositeDisposable=new io.reactivex.rxjava3.disposables.CompositeDisposable();
    com.example.shopsneaker.adapter.AccountAdapter accountAdapter;
    java.util.List<com.example.shopsneaker.model.User> mangUser;
    com.example.shopsneaker.model.User user;
    byte tinhtrang ;
    int Rolesid;
    androidx.appcompat.app.AlertDialog dialog;
    androidx.appcompat.widget.SearchView searchView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.example.shopsneaker.R.layout.activity_account_manager);
        apiBanGiay = com.example.shopsneaker.retrofit.RetrofitClient.getInstance(com.example.shopsneaker.utils.Utils.BASE_URL).create(ApiService.class);

        Init();
        actionToolbar();
        getAccount();

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

                accountAdapter.getFilter().filter(query);

                return false;

            }

            @Override
            public boolean onQueryTextChange(String newText) {
                accountAdapter.getFilter().filter(newText);
                return false;
            }
        });
        return true;
    }

    private void getAccount() {
        compositeDisposable.add(apiBanGiay.getUser("")
                .subscribeOn(io.reactivex.rxjava3.schedulers.Schedulers.io())
                .observeOn(io.reactivex.rxjava3.android.schedulers.AndroidSchedulers.mainThread())
                .subscribe(
                        userModel -> {
                            if (userModel.isSuccess()){
                                mangUser = userModel.getResult();
                                accountAdapter = new com.example.shopsneaker.adapter.AccountAdapter(getApplicationContext(), mangUser);
                                recyclerViewAccountManager.setAdapter(accountAdapter);
                                if (dialog != null){
                                    dialog.dismiss();
                                }
                            }

                        },throwable -> {
                            android.widget.Toast.makeText( getApplicationContext(), "Loi ket noi", android.widget.Toast.LENGTH_LONG).show();
                        }
                ));
    }

    private void actionToolbar() {
        setSupportActionBar(toolbarAccountManager);
        androidx.appcompat.app.ActionBar actionBar=getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        toolbarAccountManager.setNavigationOnClickListener(v -> {
            android.content.Intent intent = new android.content.Intent(getApplicationContext(), AdminActivity.class);
            startActivity(intent);
            finish();
        });
    }

    private void Init() {
        toolbarAccountManager = findViewById(com.example.shopsneaker.R.id.toolbarAccountManament);
        recyclerViewAccountManager = findViewById(com.example.shopsneaker.R.id.recyclerviewAccountManager);

        androidx.recyclerview.widget.RecyclerView.LayoutManager layoutManager = new androidx.recyclerview.widget.LinearLayoutManager(this);
        recyclerViewAccountManager.setLayoutManager(layoutManager);
        recyclerViewAccountManager.setHasFixedSize(true);
        mangUser = new java.util.ArrayList<>();

    }
    @org.greenrobot.eventbus.Subscribe(sticky = true, threadMode = org.greenrobot.eventbus.ThreadMode.MAIN)
    public void eventAccount(com.example.shopsneaker.model.EventBus.AccountEvent accountEvent){
        if (accountEvent!= null){
            user = accountEvent.getUser();
            showCustumDialog();
        }
    }

    private void showCustumDialog() {
        android.view.LayoutInflater inflater = getLayoutInflater();
        android.view.View view = inflater.inflate(com.example.shopsneaker.R.layout.dialog_account,null);
        android.widget.Spinner spinner = view.findViewById(com.example.shopsneaker.R.id.spiner_dialog_account);
        androidx.appcompat.widget.AppCompatButton btnDongY = view.findViewById(com.example.shopsneaker.R.id.dongy_dialog_account);
        java.util.List<String> list = new java.util.ArrayList<>();

        list.add("Khoá");
        list.add("Mở khóa");
        android.widget.ArrayAdapter<String> adapter = new android.widget.ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item,list);
        spinner.setAdapter(adapter);
        spinner.setSelection(user.getEnabled());

        spinner.setOnItemSelectedListener(new android.widget.AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(android.widget.AdapterView<?> adapterView, android.view.View view, int i, long l) {
                tinhtrang = (byte) i;
            }

            @Override
            public void onNothingSelected(android.widget.AdapterView<?> adapterView) {

            }
        });
        android.widget.Spinner spinnerRolesid = view.findViewById(com.example.shopsneaker.R.id.spiner_dialog_account_rolesid);
        java.util.List<String> listquyen = new java.util.ArrayList<>();
        listquyen.add("Admin");
        listquyen.add("Staff");
        listquyen.add("Customer");
        android.widget.ArrayAdapter<String>adapterquyen= new android.widget.ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item,listquyen);
        spinnerRolesid.setAdapter(adapterquyen);
        spinnerRolesid.setSelection(user.getRolesid()-1);
        spinnerRolesid.setOnItemSelectedListener(new android.widget.AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(android.widget.AdapterView<?> adapterView, android.view.View view, int i, long l) {
                Rolesid= i+1;
                //android.widget.Toast.makeText(getApplicationContext(),Rolesid+"", android.widget.Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(android.widget.AdapterView<?> adapterView) {

            }
        });
        btnDongY.setOnClickListener(view1 -> {
            if (user.getAccountid()== com.example.shopsneaker.utils.Utils.user_current.getAccountid()){
                android.widget.Toast.makeText(getApplicationContext(), "Bạn không thể thực hiện thao tác này với tài khoản của bạn", android.widget.Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }else {
                UpdateAccount();

            }
        });
        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(this);
        builder.setView(view);
        dialog = builder.create();
        dialog.show();
    }

    private void UpdateAccount() {
        compositeDisposable.add(apiBanGiay.updateAccount(user.getAccountid(),tinhtrang,Rolesid)
                .subscribeOn(io.reactivex.rxjava3.schedulers.Schedulers.io())
                .observeOn(io.reactivex.rxjava3.android.schedulers.AndroidSchedulers.mainThread())
                .subscribe(
                        userModel -> {
                            getAccount();
                            dialog.dismiss();

                        },throwable -> {
                            android.widget.Toast.makeText(getApplicationContext(),"Cap nhat khong thanh cong", android.widget.Toast.LENGTH_LONG).show();
                        }
                ));
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
    protected void onStart() {
        super.onStart();
        org.greenrobot.eventbus.EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        org.greenrobot.eventbus.EventBus.getDefault().unregister(this);

    }

}