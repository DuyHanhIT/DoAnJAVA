package com.example.shopsneaker.model;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.shopsneaker.R;
import com.example.shopsneaker.activity.AdminActivity;
import com.example.shopsneaker.activity.LoginActivity;
import com.example.shopsneaker.activity.MainActivity;
import com.example.shopsneaker.model.User;

import io.paperdb.Paper;

public class SplashActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Paper.init(this);
        setContentView(R.layout.activity_splash);
        Handler handler = new Handler();
        handler.postDelayed(() -> nextActivity(), 2000);
    }
    private void nextActivity() {
        Intent intent;
        String s = "admin";
        if (Paper.book().read("username") == null){
            intent = new Intent(this, LoginActivity.class);
        }
        else{
            User user = Paper.book().read("username");
            if(s.equals(user.getUsername())) {
                intent = new Intent(getApplicationContext(), AdminActivity.class);
            }
            else{
                intent = new Intent(this, MainActivity.class);
            }
        }
        startActivity(intent);
    }
}
