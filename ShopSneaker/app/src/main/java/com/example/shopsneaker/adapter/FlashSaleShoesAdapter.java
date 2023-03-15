package com.example.shopsneaker.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.shopsneaker.R;
import com.example.shopsneaker.activity.FullImageView;
import com.example.shopsneaker.activity.ProductDetailActivity;
import com.example.shopsneaker.model.Shoes;
import com.example.shopsneaker.utils.Utils;
import com.example.shopsneaker.utils.checkconnect;

import java.util.List;

public class FlashSaleShoesAdapter extends RecyclerView.Adapter<FlashSaleShoesAdapter.MyViewHolder> {
    android.content.Context context;
    List<Shoes> array;

    public FlashSaleShoesAdapter(Context context, List<Shoes> array) {
        this.context = context;
        this.array = array;
    }

    @androidx.annotation.NonNull
    @Override
    public FlashSaleShoesAdapter.MyViewHolder onCreateViewHolder(@androidx.annotation.NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_flashsale,parent,false);
        return new FlashSaleShoesAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@androidx.annotation.NonNull FlashSaleShoesAdapter.MyViewHolder holder, int position) {
        FlashSaleShoesAdapter.MyViewHolder myViewHolder = holder;
        Shoes shoes = array.get(position);
        holder.saleprice.setText(shoes.getSaleprice()+" USD");
        String htmlcontent = "<strike>"+shoes.getPrice()+" USD</strike>";
        holder.price.setTextColor(android.graphics.Color.rgb(119,116,116));
        holder.price.setText(android.text.Html.fromHtml(htmlcontent));
        if (shoes.getImages().contains("http")){
            Glide.with(context).load(shoes.getImages()).into(holder.images);
        }else {
            String hinh = Utils.BASE_URL+"images/" + shoes.getImages();
            Glide.with(context).load(hinh).into(holder.images);
        }
        holder.images.setOnClickListener(v -> {
            Intent intent = new Intent(context, FullImageView.class);
            intent.putExtra("image",shoes.getImages());
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return array.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder  {
        TextView  saleprice,price;
        ImageView images;
        public MyViewHolder(@androidx.annotation.NonNull View itemView) {
            super(itemView);
            images = itemView.findViewById(R.id.anhSanPham);
            saleprice = itemView.findViewById(R.id.giaSanPhamKM);
            price=itemView.findViewById(R.id.giaSanPham);
            itemView.setOnClickListener(v -> {
                Intent intent=new Intent(context, ProductDetailActivity.class);
                intent.putExtra("chitiet", array.get(getAdapterPosition()));
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                checkconnect.ShowToast_Short(context,array.get(getAdapterPosition()).getShoeName());
                context.startActivity(intent);
            });
        }


    }
}
