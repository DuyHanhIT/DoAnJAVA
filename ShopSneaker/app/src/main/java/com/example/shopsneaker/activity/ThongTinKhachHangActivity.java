package com.example.shopsneaker.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.shopsneaker.R;
import com.example.shopsneaker.model.EventBus.tinhtongtien;
import com.example.shopsneaker.retrofit.ApiService;
import com.example.shopsneaker.retrofit.RetrofitClient;
import com.example.shopsneaker.utils.Utils;
import com.example.shopsneaker.utils.checkconnect;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class ThongTinKhachHangActivity extends AppCompatActivity {
    EditText edtPhone, edtAddress,edtName;
    Button btxacnhan,bttrove;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private ApiService apiBanGiay;
    String username;
    long tongtien;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thanhtoan);
        apiBanGiay = RetrofitClient.getInstance(Utils.BASE_URL).create(ApiService.class);
        username = Utils.user_current.getUsername();
        initUi();
        evenUltil();
        if(checkconnect.isNetworkAvailable(getApplicationContext())){
            eventButton();
        }
        else{
            checkconnect.ShowToast_Short(getApplicationContext(),"Bạn hãy kiểm tra lại kết nối");
        }
    }

    private void eventButton(){
        bttrove.setOnClickListener(v ->
        {
            Intent intent =new Intent(getApplicationContext(),PaymentActivity.class);
            intent.putExtra("tongtien",tongtien);
            finish();
        });
        btxacnhan.setOnClickListener(v -> EditInFor());
    }

    private void getUser(){
        compositeDisposable.add(apiBanGiay.getUser(username).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        userModel -> {
                            if(userModel.isSuccess()){
                                Utils.user_current = userModel.getResult().get(0);
                            }else {
                                Toast.makeText(getApplicationContext(),"Thất bại", Toast.LENGTH_LONG).show();
                            }
                        },
                        throwable -> {
                            Toast.makeText(getApplicationContext(),"Không kết nối được server", Toast.LENGTH_LONG).show();
                        }
                ));
    }

    private void EditInFor() {
        String strname = edtName.getText().toString().trim();
        String straddress = edtAddress.getText().toString().trim();
        String strphone = edtPhone.getText().toString().trim();
        if(TextUtils.isEmpty(strname)){
            Toast.makeText(getApplicationContext(),"Bạn chưa nhập họ tên", Toast.LENGTH_LONG).show();
        }
        else if(TextUtils.isEmpty(strphone)){
            Toast.makeText(getApplicationContext(),"Bạn chưa nhập số điện thoại", Toast.LENGTH_LONG).show();
        }
        else if(TextUtils.isEmpty(straddress)){
            Toast.makeText(getApplicationContext(),"Bạn chưa nhập địa chỉ", Toast.LENGTH_LONG).show();
        }
        else {
            compositeDisposable.add(apiBanGiay.AddInfor(username,strname,straddress,strphone).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            userModel -> {
                                if(userModel.isSuccess()){
                                    getUser();
                                    Toast.makeText(getApplicationContext(),"Cập nhật thành công", Toast.LENGTH_LONG).show();
                                    Intent intent = new Intent(getApplicationContext(), PaymentActivity.class);
                                    intent.putExtra("tongtien",tongtien);
                                    finish();
                                }else {
                                    Toast.makeText(getApplicationContext(),"Thất bại", Toast.LENGTH_LONG).show();
                                }
                            },
                            throwable -> {
                                Toast.makeText(getApplicationContext(),"Không kết nối được server", Toast.LENGTH_LONG).show();
                            }
                    ));
        }
    }

    public void evenUltil() {
        tongtien=0;
        for(int i=0;i<Utils.manggiohang.size();i++){
            tongtien+=Utils.manggiohang.get(i).getPurchased()*Utils.manggiohang.get(i).getPrice();
        }
    }

    private void initUi(){
        edtName=findViewById(R.id.edittexttenkhachhang);
        edtPhone=findViewById(R.id.edittextsodienthoai);
        edtAddress=findViewById(R.id.edittexdiachi);
        btxacnhan=findViewById(R.id.buttonxacnhan);
        bttrove=findViewById(R.id.buttontrove);
    }

    protected void onResume () {
        super.onResume();
        if (Utils.user_current.getUsername() != null && Utils.user_current.getPassword() != null ){
            edtAddress.setText(Utils.user_current.getAddress());
            edtName.setText(Utils.user_current.getName());
            edtPhone.setText(Utils.user_current.getPhone());
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
    @Subscribe(sticky = true,threadMode = ThreadMode.MAIN)
    public void eventTinhTien(tinhtongtien event){
        if(event!=null){
            evenUltil();
        }
    }

    @Override
    protected void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
    }

}
