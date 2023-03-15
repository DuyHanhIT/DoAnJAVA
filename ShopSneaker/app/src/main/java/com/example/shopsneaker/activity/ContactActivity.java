
package com.example.shopsneaker.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.shopsneaker.R;
import com.example.shopsneaker.adapter.ContactAdapter;
import com.example.shopsneaker.model.Contact;

import java.util.ArrayList;

public class ContactActivity extends AppCompatActivity {
    androidx.appcompat.widget.Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);



        ListView listView;
        ArrayList<Contact> arrayList;
        ContactAdapter adapterLienhe;

        listView = findViewById(R.id.lvLienHe);
        toolbar = findViewById(com.example.shopsneaker.R.id.toolbarContact);
        actionToolbar();
        arrayList = new ArrayList<>();
        arrayList.add(new Contact("Email","Gửi Email cho chúng tôi", R.drawable.ic_baseline_email_24));
        arrayList.add(new Contact("Điện thoại","Gọi diện thoại cho chúng tôi", R.drawable.ic_baseline_phone_24));
        arrayList.add(new Contact("Địa chỉ cửa hàng","Xem địa chỉ cửa hàng", com.example.shopsneaker.R.drawable.ic_ggmap_on_foreground));

        adapterLienhe = new ContactAdapter(ContactActivity.this, R.layout.layoutdathang,arrayList);
        listView.setAdapter(adapterLienhe);

        listView.setOnItemClickListener((parent, view, position, id) -> {
            if(position == 0){
                Intent intent = new Intent();
                intent.setClass(ContactActivity.this, ContactActivity2.class);
                startActivity(intent);
            }
            if(position == 1){
                Intent intent = new Intent();
                intent.setClass(ContactActivity.this, ContactActivity3.class);
                startActivity(intent);
            }
            if(position == 2){
                Intent intent = new Intent();
                intent.setClass(ContactActivity.this, MapsActivity.class);
                startActivity(intent);
            }
        });



    }
    private void actionToolbar() {
        setSupportActionBar(toolbar);
        androidx.appcompat.app.ActionBar actionBar=getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(v -> finish());
    }
}
