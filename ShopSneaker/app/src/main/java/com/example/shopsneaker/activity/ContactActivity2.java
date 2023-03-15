package com.example.shopsneaker.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.shopsneaker.R;

public class ContactActivity2 extends AppCompatActivity {
    TextView textView3;
    TextView textView4;
    TextView send;
    TextView send1;
    androidx.appcompat.widget.Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_mail);
        toolbar= findViewById(com.example.shopsneaker.R.id.toolbarContactEmail);
//        actionToolbar();

        textView3 =  findViewById(R.id.textemail1);
        textView4 =  findViewById(R.id.textemail2);


        send = (TextView) findViewById(R.id.lienhedathang);
        send1 = (TextView) findViewById(R.id.lienheEmail);



        send.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                String to = textView3.getText().toString();


                Intent email = new Intent(Intent.ACTION_SEND);
                email.putExtra(Intent.EXTRA_EMAIL, new String[]{to});

                email.setType("message/rfc822");

                startActivity(Intent.createChooser(email, "Choose an Email Client"));
            }

        });

        send1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String to = textView4.getText().toString();

                Intent email = new Intent(Intent.ACTION_SEND);
                email.putExtra(Intent.EXTRA_EMAIL, new String[]{to});

                email.setType("message/rfc822");

                startActivity(Intent.createChooser(email, "Choose an Email Client"));
            }
        });

    }
    /*private void actionToolbar() {
        setSupportActionBar(toolbar);
        androidx.appcompat.app.ActionBar actionBar=getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        //toolbar.setNavigationOnClickListener(v -> finish());
        toolbar.setNavigationOnClickListener(view -> finish());
    }*/



    @SuppressLint("ResourceType")
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.layout.activity_contact_mail, menu);
        return true;
    }
}
