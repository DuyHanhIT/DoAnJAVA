package com.example.shopsneaker.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.shopsneaker.R;
import com.example.shopsneaker.model.User;
import com.example.shopsneaker.retrofit.ApiService;
import com.example.shopsneaker.retrofit.RetrofitClient;
import com.example.shopsneaker.utils.Utils;

import io.paperdb.Paper;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class SplashActivity extends AppCompatActivity {
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    ApiService apiBanGiay;
    Intent intent;
    String username;
    User x;
    User user;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        apiBanGiay = RetrofitClient.getInstance(Utils.BASE_URL).create(ApiService.class);
        Paper.init(this);
        user = Paper.book().read("user");
        if(user !=null){
            username  = user.getUsername();
            getUser();
        }

        setContentView(R.layout.activity_splash);
        Handler handler = new Handler();
        handler.postDelayed(() -> nextActivity(), 2000);
    }

    private void getUser(){
        compositeDisposable.add(apiBanGiay.getUser(username).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        userModel -> {
                            if(userModel.isSuccess()){
                                x = userModel.getResult().get(0);
                            }else {
                                Toast.makeText(getApplicationContext(),"Thất bại", Toast.LENGTH_LONG).show();
                            }
                        },
                        throwable -> {
                            Toast.makeText(getApplicationContext(),"Không kết nối được server", Toast.LENGTH_LONG).show();
                        }
                ));
    }

    private void nextActivity() {
        String s = "admin";

        if (Paper.book().read("user") == null){
            intent = new Intent(this, LoginActivity.class);
        }
        else{
            if(x.getEnabled()==0){
                intent = new Intent(this, LoginActivity.class);
            }
            else if(s.equals(user.getUsername())||user.getRolesid()==2) {
                Utils.user_current.setRolesid(user.getRolesid());
                intent = new Intent(getApplicationContext(), AdminActivity.class);
            }
            else{
                intent = new Intent(this, MainActivity.class);
            }
        }startActivity(intent);

    }
}
