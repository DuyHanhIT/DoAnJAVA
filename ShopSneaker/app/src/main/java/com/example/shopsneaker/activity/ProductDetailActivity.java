package com.example.shopsneaker.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatRatingBar;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.shopsneaker.R;
import com.example.shopsneaker.adapter.CommentAdapter;
import com.example.shopsneaker.adapter.SizeTableAdapter;
import com.example.shopsneaker.model.Comment;
import com.example.shopsneaker.model.GioHang;
import com.example.shopsneaker.model.Shoes;
import com.example.shopsneaker.model.SizeTable;
import com.example.shopsneaker.model.User;
import com.example.shopsneaker.retrofit.ApiService;
import com.example.shopsneaker.retrofit.RetrofitClient;
import com.example.shopsneaker.utils.Utils;
import com.nex3z.notificationbadge.NotificationBadge;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class ProductDetailActivity extends AppCompatActivity {
    TextView proname, price, description;
    EditText txtviet;
    Button btnAdd;
    AppCompatRatingBar ratingbar;
    RadioButton btnS38,btnS39,btnS40,btnS41,btnS42,btnS43,btnS44,btnS45,btnS46,btnS47,btnS48;
    List<SizeTable> arrSize;
    SizeTableAdapter adapter;
    RecyclerView recyclerViewcomment;
    private CommentAdapter commentAdapter;
    private List<Comment> arrComment;
    RadioGroup radioGroup;
    EditText edtwrcmt;
    AppCompatButton btnsend;
    ImageView imgproduct;
    Spinner spinner;
    Toolbar toolbar;
    Shoes shoes;
    Comment comment;
    User user;
    ApiService apiBanGiay;
    String size = null;
    RatingBar ratingBar;
    NotificationBadge badge;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    java.util.Date date = new java.util.Date();
    int id = 0;
    float rate;
    private FrameLayout frameLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);
        apiBanGiay = RetrofitClient.getInstance(Utils.BASE_URL).create(ApiService.class);
        Init();
        ActionBar();
        InitData();
        getComment();
        getRateShoes();
        initControll();
        getSize();
        EventRadioGroup();
        EventButton();
    }

    private void InitData() {
        shoes = (Shoes) getIntent().getSerializableExtra("chitiet");
        id = shoes.getShoeId();

        if(shoes.getSaleprice() == 0|| shoes.getStartday().after(date) || shoes.getEndday().before(date)){
            price.setText("Giá: "+shoes.getPrice()+"USD");
        }
        else{
            price.setText("Giá: "+shoes.getSaleprice()+"USD");
        }
        proname.setText(shoes.getShoeName());
        description.setText(shoes.getDescription());
        if (shoes.getImages().contains("http")){
            Glide.with(getApplicationContext()).load(shoes.getImages()).into(imgproduct);
        }else {
            String hinh = Utils.BASE_URL+"images/" + shoes.getImages();
            Glide.with(getApplicationContext()).load(hinh).into(imgproduct);
        }
        Integer[] quantity = new Integer[]{1,2,3,4,5,6,7,8,9,10};
        ArrayAdapter<Integer> adapterspin = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item,quantity);
        spinner.setAdapter(adapterspin);
    }

    private void Init() {

        arrComment = new ArrayList<>();
        commentAdapter = new CommentAdapter(getApplicationContext(),arrComment);
        ratingbar = findViewById(R.id.ratingBarOder);
        recyclerViewcomment = findViewById(R.id.recycleviewComment);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerViewcomment.setLayoutManager(layoutManager);
        recyclerViewcomment.setHasFixedSize(true);

        edtwrcmt = findViewById(R.id.txtwritecm);
        btnsend = findViewById(R.id.btnSend);
        proname = findViewById(R.id.txtproductname);
        price = findViewById(R.id.txtprice);
        description = findViewById(R.id.txtDescription);
        txtviet = findViewById(R.id.txtwritecm);
        ratingbar = findViewById(R.id.ratingBarOder);
        //radio


        radioGroup = findViewById(R.id.radioGroup);
        btnS38 = findViewById(R.id.btnS38);
        btnS39 = findViewById(R.id.btnS39);
        btnS40 = findViewById(R.id.btnS40);
        btnS41 = findViewById(R.id.btnS41);
        btnS42 = findViewById(R.id.btnS42);
        btnS43 = findViewById(R.id.btnS43);
        btnS44 = findViewById(R.id.btnS44);
        btnS45 = findViewById(R.id.btnS45);
        btnS46 = findViewById(R.id.btnS46);
        btnS47 = findViewById(R.id.btnS47);
        btnS48 = findViewById(R.id.btnS48);
        btnAdd = findViewById(R.id.btnAddCart);
        imgproduct = findViewById(R.id.imgProduct);
        arrSize = new ArrayList<>();
        adapter =new SizeTableAdapter(getApplicationContext(),arrSize);
        spinner = findViewById(R.id.spiner);
        toolbar = findViewById(R.id.toolbarProductDetail);
        badge = findViewById(R.id.menu_sl);
        if(Utils.manggiohang.size()>0){
            badge.setText(String.valueOf(Utils.manggiohang.size()));
        }
        frameLayout = findViewById(R.id.frametoolbar);
        frameLayout.setOnClickListener(v -> {
            Intent intent =new Intent(getApplicationContext(),GioHangActivity.class);
            startActivity(intent);
        });

    }private void setBackground_when_sizeSoldOut(RadioButton radioButton){
        radioButton.setBackgroundResource(com.example.shopsneaker.R.color.Xam);
    }
    private void setBackground_unchecked(RadioButton a ,RadioButton b,RadioButton c,RadioButton d,RadioButton e,RadioButton f,RadioButton g,RadioButton h,RadioButton i,RadioButton j ){
        a.setBackgroundResource(com.example.shopsneaker.R.drawable.button_radio);
        b.setBackgroundResource(com.example.shopsneaker.R.drawable.button_radio);
        c.setBackgroundResource(com.example.shopsneaker.R.drawable.button_radio);
        d.setBackgroundResource(com.example.shopsneaker.R.drawable.button_radio);
        e.setBackgroundResource(com.example.shopsneaker.R.drawable.button_radio);
        f.setBackgroundResource(com.example.shopsneaker.R.drawable.button_radio);
        g.setBackgroundResource(com.example.shopsneaker.R.drawable.button_radio);
        h.setBackgroundResource(com.example.shopsneaker.R.drawable.button_radio);
        i.setBackgroundResource(com.example.shopsneaker.R.drawable.button_radio);
        j.setBackgroundResource(com.example.shopsneaker.R.drawable.button_radio);
    }
    private void getSize() {
        compositeDisposable.add(apiBanGiay.getSizeTable(id)
                .subscribeOn(io.reactivex.rxjava3.schedulers.Schedulers.io())
                .observeOn(io.reactivex.rxjava3.android.schedulers.AndroidSchedulers.mainThread())
                .subscribe(
                        sizeTableModel -> {
                            if (sizeTableModel.isSuccess()){
                                arrSize = sizeTableModel.getResult();
                                adapter = new SizeTableAdapter(getApplicationContext(),arrSize);
                                DataSize();
                            }
                        },
                        throwable -> {
                            android.widget.Toast.makeText(getApplicationContext(), "Khong ket noi duoc voi server", android.widget.Toast.LENGTH_LONG).show();
                        }
                ));
    }

    private void getComment() {

        compositeDisposable.add(apiBanGiay.getComment(id)
                .subscribeOn(io.reactivex.rxjava3.schedulers.Schedulers.io())
                .observeOn(io.reactivex.rxjava3.android.schedulers.AndroidSchedulers.mainThread())
                .subscribe(
                        commentModel -> {
                            if (commentModel.isSuccess()){
                                arrComment = commentModel.getResult();
                                commentAdapter = new CommentAdapter(getApplicationContext(),arrComment);
                                recyclerViewcomment.setAdapter(commentAdapter);
                            }
                        },
                        throwable -> {
                            android.widget.Toast.makeText(getApplicationContext(), "Khong ket noi duoc voi server", android.widget.Toast.LENGTH_LONG).show();
                        }
                ));
    }

    private void getRateShoes() {
        compositeDisposable.add(apiBanGiay.getRateShoes(id)
                .subscribeOn(io.reactivex.rxjava3.schedulers.Schedulers.io())
                .observeOn(io.reactivex.rxjava3.android.schedulers.AndroidSchedulers.mainThread())
                .subscribe(
                        sizeTableModel -> {
                            if (sizeTableModel.isSuccess()){
                                rate = sizeTableModel.getResult().get(0).getRate();
                                ratingbar.setRating(rate);
                            }
                        },
                        throwable -> {

                        }
                ));
    }

    private void initControll()
    {
        btnsend.setOnClickListener(v -> inserCmt());
        imgproduct.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(),FullImageView.class);
            intent.putExtra("image",shoes.getImages());
            startActivity(intent);
        });
    }
    private void inserCmt() {
        int accid = Utils.user_current.getAccountid();
        shoes = (Shoes) getIntent().getSerializableExtra("chitiet");
        id = shoes.getShoeId();
        String content = edtwrcmt.getText().toString().trim();
        compositeDisposable.add(apiBanGiay.insertComment(id,accid,content).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        commentModel -> {
                            if(commentModel.isSuccess()){
                                finish();
                                overridePendingTransition(0, 0);
                                startActivity(getIntent());
                                overridePendingTransition(0, 0);
                                Toast.makeText(getApplicationContext(),"Thành công",Toast.LENGTH_LONG).show();
                            }else {
                                Toast.makeText(getApplicationContext(),"Thất bại", Toast.LENGTH_LONG).show();
                            }
                        },
                        throwable -> {
                        }
                ));
    }


    private void EventRadioGroup(){
        radioGroup.setOnCheckedChangeListener((group, i) -> {

            //i trả về giá trị id của radiobutton
            switch (i){
                case R.id.btnS38:
                    btnS38.setBackgroundResource(com.example.shopsneaker.R.drawable.button_radio_checked);
                    setBackground_unchecked(btnS39,btnS40,btnS41,btnS42,btnS43,btnS44,btnS45,btnS46,btnS47,btnS48);
                    DataSize();
                    break;
                case R.id.btnS39:
                    btnS39.setBackgroundResource(com.example.shopsneaker.R.drawable.button_radio_checked);
                    setBackground_unchecked(btnS38,btnS40,btnS41,btnS42,btnS43,btnS44,btnS45,btnS46,btnS47,btnS48);
                    DataSize();
                    break;
                case R.id.btnS40:
                    btnS40.setBackgroundResource(com.example.shopsneaker.R.drawable.button_radio_checked);
                    setBackground_unchecked(btnS39,btnS38,btnS41,btnS42,btnS43,btnS44,btnS45,btnS46,btnS47,btnS48);
                    DataSize();
                    break;
                case R.id.btnS41:
                    btnS41.setBackgroundResource(com.example.shopsneaker.R.drawable.button_radio_checked);
                    setBackground_unchecked(btnS39,btnS40,btnS38,btnS42,btnS43,btnS44,btnS45,btnS46,btnS47,btnS48);
                    DataSize();
                    break;
                case R.id.btnS42:
                    btnS42.setBackgroundResource(com.example.shopsneaker.R.drawable.button_radio_checked);
                    setBackground_unchecked(btnS39,btnS40,btnS41,btnS38,btnS43,btnS44,btnS45,btnS46,btnS47,btnS48);
                    DataSize();
                    break;
                case R.id.btnS43:
                    btnS43.setBackgroundResource(com.example.shopsneaker.R.drawable.button_radio_checked);
                    setBackground_unchecked(btnS39,btnS40,btnS41,btnS42,btnS38,btnS44,btnS45,btnS46,btnS47,btnS48);
                    DataSize();
                    break;
                case R.id.btnS44:
                    btnS44.setBackgroundResource(com.example.shopsneaker.R.drawable.button_radio_checked);
                    setBackground_unchecked(btnS39,btnS40,btnS41,btnS42,btnS43,btnS38,btnS45,btnS46,btnS47,btnS48);
                    DataSize();
                    break;
                case R.id.btnS45:
                    btnS45.setBackgroundResource(com.example.shopsneaker.R.drawable.button_radio_checked);
                    setBackground_unchecked(btnS39,btnS40,btnS41,btnS42,btnS43,btnS44,btnS38,btnS46,btnS47,btnS48);
                    DataSize();
                    break;
                case R.id.btnS46:
                    btnS46.setBackgroundResource(com.example.shopsneaker.R.drawable.button_radio_checked);
                    setBackground_unchecked(btnS39,btnS40,btnS41,btnS42,btnS43,btnS44,btnS45,btnS38,btnS47,btnS48);
                    DataSize();
                    break;
                case R.id.btnS47:
                    btnS47.setBackgroundResource(com.example.shopsneaker.R.drawable.button_radio_checked);
                    setBackground_unchecked(btnS39,btnS40,btnS41,btnS42,btnS43,btnS44,btnS45,btnS46,btnS38,btnS48);
                    DataSize();
                    break;
                case R.id.btnS48:
                    btnS48.setBackgroundResource(com.example.shopsneaker.R.drawable.button_radio_checked);
                    setBackground_unchecked(btnS39,btnS40,btnS41,btnS42,btnS43,btnS44,btnS45,btnS46,btnS47,btnS38);
                    DataSize();
                    break;
            }
        });
    }private void DataSize(){
        //SizeTable sizeTable = new SizeTable();
        if(id == arrSize.get(0).getId()){
            if (arrSize.get(0).getS38() > 0) {
                btnS38.setEnabled(true);
            }
            else {setBackground_when_sizeSoldOut(btnS38);}
            if (arrSize.get(0).getS39() > 0) {
                btnS39.setEnabled(true);
            }else {setBackground_when_sizeSoldOut(btnS39);}
            if (arrSize.get(0).getS40() > 0) {
                btnS40.setEnabled(true);
            }else {setBackground_when_sizeSoldOut(btnS40);}
            if (arrSize.get(0).getS41() > 0) {
                btnS41.setEnabled(true);
            }else {setBackground_when_sizeSoldOut(btnS41);}
            if (arrSize.get(0).getS42() > 0) {
                btnS42.setEnabled(true);
            }else {setBackground_when_sizeSoldOut(btnS42);}
            if (arrSize.get(0).getS43() > 0) {
                btnS43.setEnabled(true);
            }else {setBackground_when_sizeSoldOut(btnS43);}
            if (arrSize.get(0).getS44() > 0) {
                btnS44.setEnabled(true);
            }else {setBackground_when_sizeSoldOut(btnS44);}
            if (arrSize.get(0).getS45() > 0) {
                btnS45.setEnabled(true);
            }else {setBackground_when_sizeSoldOut(btnS45);}
            if (arrSize.get(0).getS46() > 0) {
                btnS46.setEnabled(true);
            }else {setBackground_when_sizeSoldOut(btnS46);}
            if (arrSize.get(0).getS47() > 0) {
                btnS47.setEnabled(true);
            }else {setBackground_when_sizeSoldOut(btnS47);}
            if (arrSize.get(0).getS48() > 0) {
                btnS48.setEnabled(true);
            }else {setBackground_when_sizeSoldOut(btnS48);}
        }
    }

    private void ActionBar(){
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(v -> finish());
    }

    private void EventButton() {
        btnAdd.setOnClickListener(v -> {
            if(btnS38.isChecked()){
                size = (String) btnS38.getText();
            }
            if(btnS39.isChecked()){
                size = (String) btnS39.getText();
            }
            if(btnS40.isChecked()){
                size = (String) btnS40.getText();
            }
            if(btnS41.isChecked()){
                size = (String) btnS41.getText();
            }
            if(btnS42.isChecked()){
                size = (String) btnS42.getText();
            }
            if(btnS43.isChecked()){
                size = (String) btnS43.getText();
            }
            if(btnS44.isChecked()){
                size = (String) btnS44.getText();
            }
            if(btnS45.isChecked()){
                size = (String) btnS45.getText();
            }
            if(btnS46.isChecked()){
                size = (String) btnS46.getText();
            }
            if(btnS47.isChecked()){
                size = (String) btnS47.getText();
            }
            if(btnS48.isChecked()){
                size = (String) btnS48.getText();
            }
            themGioHang();
        });
    }

    private void themGioHang() {
        Integer gia;
        if(Utils.manggiohang.size()>0){
            boolean flag = false;
            int soluong = Integer.parseInt((spinner.getSelectedItem().toString()));
            for(int i=0;i<Utils.manggiohang.size();i++){
                if(Utils.manggiohang.get(i).getId() == shoes.getShoeId() && size == Utils.manggiohang.get(i).getSize()){
                    Utils.manggiohang.get(i).setPurchased(soluong + Utils.manggiohang.get(i).getPurchased());
                    flag = true;
                }
            }
            if(flag == false){

                if(shoes.getSaleprice() == 0|| shoes.getStartday().after(date) || shoes.getEndday().before(date)){
                    gia  = shoes.getPrice();
                }
                else{
                    gia = shoes.getSaleprice();
                }
                GioHang gioHang = new GioHang();
                gioHang.setPrice(gia);
                gioHang.setSize(size);
                gioHang.setPurchased(soluong);
                gioHang.setId(shoes.getShoeId());
                gioHang.setName(shoes.getShoeName());
                gioHang.setImages(shoes.getImages());
                Utils.manggiohang.add(gioHang);
            }
        }else{
            if(size == null){
                Toast.makeText(this, "Chưa chọn size", Toast.LENGTH_SHORT).show();
            }
            else {
                int soluong = Integer.parseInt((spinner.getSelectedItem().toString()));
                if (shoes.getSaleprice() == 0|| shoes.getStartday().after(date) || shoes.getEndday().before(date)) {
                    gia = shoes.getPrice();
                } else {
                    gia = shoes.getSaleprice();
                }
                GioHang gioHang = new GioHang();
                gioHang.setSize(size);
                gioHang.setPrice(gia);
                gioHang.setPurchased(soluong);
                gioHang.setId(shoes.getShoeId());
                gioHang.setName(shoes.getShoeName());
                gioHang.setImages(shoes.getImages());
                Utils.manggiohang.add(gioHang);
            }
        }
        badge.setText(String.valueOf(Utils.manggiohang.size()));
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(Utils.manggiohang.size()>0){
            badge.setText(String.valueOf(Utils.manggiohang.size()));
        }
        else{
            badge.setText(String.valueOf(0));
        }
    }
}