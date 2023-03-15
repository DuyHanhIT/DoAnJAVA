package com.example.shopsneaker.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shopsneaker.R;
import com.example.shopsneaker.adapter.ChatAdapter;
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

public class ChatActivity extends AppCompatActivity {
    Message message;
    int accid;
    String name;
    TextView textName;
    FrameLayout layoutsend;
    User user;
    EditText inputMess;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    ApiService apiBanGiay;
    AppCompatImageView imgBack;
    List<Message> list;
    RecyclerView chatRCV;
    ChatAdapter chatAdapter;
    int sender;
    int receiver;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        apiBanGiay = RetrofitClient.getInstance(Utils.BASE_URL).create(ApiService.class);
        user = Utils.user_current;
        message = (Message)  getIntent().getSerializableExtra("user");
        accid = getIntent().getIntExtra("accountid",-1);
        name = getIntent().getStringExtra("name");
        Init();
        EventClick();
        getChatMess();

    }
    private void Init(){
        textName = findViewById(R.id.textName);
        sender = user.getAccountid();
        if(message !=null){
            textName.setText(message.getName());
            receiver = message.getReceiver();
            if(sender==receiver){
                receiver = message.getSender();
            }
        }else{
            receiver = accid;
            textName.setText(name);
        }
        layoutsend = findViewById(R.id.layoutsend);
        inputMess = findViewById(R.id.inputMess);
        imgBack = findViewById(R.id.imgBack);
        list = new ArrayList<>();
        chatRCV = findViewById(R.id.chatRCV);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        chatRCV.setLayoutManager(linearLayoutManager);
        chatAdapter =new ChatAdapter(getApplicationContext(),list);
        chatRCV.setAdapter(chatAdapter);
    }

    private void EventClick(){
        layoutsend.setOnClickListener(v -> insertChatMess());
        imgBack.setOnClickListener(v -> {
            Intent intent= new Intent(getApplicationContext(), MessageActivity.class);
            startActivity(intent);
            finish();
        });
    }

    private void getChatMess(){
        compositeDisposable.add(apiBanGiay.getChatMess(sender,receiver)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        addModel -> {
                            list = addModel.getResult();
                            Collections.sort(list, (o1, o2) -> o1.getMesid()-o2.getMesid());
                            chatAdapter =new ChatAdapter(getApplicationContext(),list);
                            chatAdapter.notifyDataSetChanged();
                            chatRCV.setAdapter(chatAdapter);
                            chatRCV.scrollToPosition(list.size()-1);
                        },
                        throwable -> {
                        }
                ));
    }

    private void insertChatMess(){
        String content = inputMess.getText().toString().trim();
        if(TextUtils.isEmpty(content) || sender==0 || receiver==0){
        }else {
            compositeDisposable.add(apiBanGiay.insertChatMess(sender,receiver,content)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            addModel -> {
                                chatAdapter.notifyDataSetChanged();
                                chatRCV.scrollToPosition(list.size()-1);
                                startActivity(getIntent());
                            },
                            throwable -> {
                            }
                    ));
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        getChatMess();
    }

    @Override
    protected void onResume() {
        super.onResume();
        getChatMess();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        getChatMess();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }
}
