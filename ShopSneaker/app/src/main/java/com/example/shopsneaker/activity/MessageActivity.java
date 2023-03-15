package com.example.shopsneaker.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shopsneaker.R;
import com.example.shopsneaker.adapter.MessageAdapter;
import com.example.shopsneaker.model.Message;
import com.example.shopsneaker.model.User;
import com.example.shopsneaker.retrofit.ApiService;
import com.example.shopsneaker.retrofit.RetrofitClient;
import com.example.shopsneaker.utils.Utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MessageActivity extends AppCompatActivity {
    Toolbar toolbarMessage;
    User user;
    RecyclerView recyclerviewMessage;
    List<Message> messageList;
    MessageAdapter messageAdapter;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private ApiService apiBanGiay;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        apiBanGiay = RetrofitClient.getInstance(Utils.BASE_URL).create(ApiService.class);
        user = Utils.user_current;
        Init();
        actionToolbar();
        getListChat();
    }
    private void Init(){
        toolbarMessage = findViewById(R.id.toolbarMessage);
        toolbarMessage.setTitle(user.getName());

        recyclerviewMessage = findViewById(R.id.recyclerviewMessage);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerviewMessage.setLayoutManager(linearLayoutManager);
        messageList = new ArrayList<>();
        messageAdapter =new MessageAdapter(getApplicationContext(),messageList);
        recyclerviewMessage.setAdapter(messageAdapter);
    }

    private void actionToolbar() {
        setSupportActionBar(toolbarMessage);
        ActionBar actionBar=getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        toolbarMessage.setNavigationOnClickListener(v -> {
            Intent intent= new Intent(getApplicationContext(), MarketPlaceActivity.class);
            startActivity(intent);
            finish();
        });
    }

    private void getListChat() {
        compositeDisposable.add(apiBanGiay.getListChat(user.getAccountid())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        messageModel -> {
                            if (messageModel.isSuccess()){
                                messageList = messageModel.getResult();
                                Collections.sort(messageList, (o1, o2) -> o2.getMesid()-o1.getMesid());
                                for (int i = 0; i<messageList.size()-1;i++){
                                    for (int j = i+1; j<messageList.size();j++){
                                        if(messageList.get(i).getName().equals(messageList.get(j).getName())){
                                            if(messageList.get(i).getMesid()>=messageList.get(j).getMesid()){
                                                messageList.remove(j);
                                            }else{
                                                messageList.remove(i);
                                            }
                                        }
                                    }
                                }
                                messageAdapter = new MessageAdapter(getApplicationContext(),messageList);
                                recyclerviewMessage.setAdapter(messageAdapter);
                            }
                        },
                        throwable -> {
                            Toast.makeText(getApplicationContext(),"Load Failed", Toast.LENGTH_LONG).show();
                        }
                ));
    }

}
