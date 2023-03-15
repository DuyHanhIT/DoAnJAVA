package com.example.shopsneaker.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.shopsneaker.R;
import com.example.shopsneaker.activity.FullImageView;
import com.example.shopsneaker.activity.UpdatePostsActivity;
import com.example.shopsneaker.model.Images;
import com.example.shopsneaker.model.Market;
import com.example.shopsneaker.utils.Utils;
import com.example.shopsneaker.utils.checkconnect;

import java.util.List;

public class ImagesAdapter extends RecyclerView.Adapter<ImagesAdapter.MyViewHolder>{
    Context context;
    List<Images> array;

    public ImagesAdapter(Context context, List<Images> array) {
        this.context = context;
        this.array = array;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_iamges,parent, false);
        return new MyViewHolder(item);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Images images = array.get(position);
        if (images.getImage().contains("http")){
            Glide.with(context).load(images.getImage()).into(holder.anhSanPham);
        }else {
            String hinh = Utils.BASE_URL+"images/" + images.getImage();
            Glide.with(context).load(hinh).into(holder.anhSanPham);
        }
        holder.anhSanPham.setOnClickListener(v -> {
            Intent intent = new Intent(context, FullImageView.class);
            intent.putExtra("image",images.getImage());
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        });

    }

    @Override
    public int getItemCount() {
        return array.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView anhSanPham;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            anhSanPham = itemView.findViewById(R.id.anhSanPham);
        }
    }
}
