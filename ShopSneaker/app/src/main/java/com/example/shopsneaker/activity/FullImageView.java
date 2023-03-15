package com.example.shopsneaker.activity;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.shopsneaker.R;
import com.example.shopsneaker.utils.Utils;
import com.github.chrisbanes.photoview.PhotoView;

public class FullImageView extends AppCompatActivity {
    private PhotoView photoView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_image_view);

        photoView = findViewById(R.id.photo_view);
        String image = getIntent().getStringExtra("image");
        if (image.contains("http")){
            Glide.with(getApplicationContext()).load(image).into(photoView);
        }else {
            String hinh = Utils.BASE_URL+"images/" + image;
            Glide.with(getApplicationContext()).load(hinh).into(photoView);
        }

    }
}
