package com.example.shopsneaker.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.shopsneaker.R;
import com.example.shopsneaker.activity.FullImageView;
import com.example.shopsneaker.model.SaleDetails;
import com.example.shopsneaker.utils.Utils;

import java.util.List;

public class AddSaleDetailsAdapter extends RecyclerView.Adapter<AddSaleDetailsAdapter.MyViewHolder>{
    Context context;
    List<SaleDetails> array;
    Boolean isSelectedAll = true;
    public AddSaleDetailsAdapter(Context context, List<SaleDetails> array) {
        this.context = context;
        this.array = array;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_add_saledetails, parent, false);
        return new MyViewHolder(item);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        SaleDetails shoes = array.get(position);
        holder.textviewName.setText(shoes.getName());
        holder.textviewPrice.setText("Giá: " + shoes.getPrice() + "USD");
        if (shoes.getImages().contains("http")){
            Glide.with(context).load(shoes.getImages()).into(holder.imageShoe);
        }else {
            String hinh = Utils.BASE_URL+"images/" + shoes.getImages();
            Glide.with(context).load(hinh).into(holder.imageShoe);
        }
        holder.imageShoe.setOnClickListener(v -> {
            Intent intent = new Intent(context, FullImageView.class);
            intent.putExtra("image",shoes.getImages());
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        });
        holder.box.setText(shoes.getId()+"");
        holder.box.setOnClickListener(v -> {
            if(holder.box.isChecked()){
                Utils.ListSaleDetails.add(array.get(position));
            }
            else {
                Utils.ListSaleDetails.remove(array.get(position));
            }
        });
    }

//    public void selectAll(){
//        isSelectedAll=true;
//        notifyDataSetChanged();
//    }
//    public void unselectall(){
//        isSelectedAll=false;
//        notifyDataSetChanged();
//    }

    @Override
    public int getItemCount() {
        return array.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView textviewName, textviewPrice;
        ImageView imageShoe;
        CheckBox box;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            box = itemView.findViewById(R.id.int_id);
            textviewPrice = itemView.findViewById(R.id.textviewPrice);
            textviewName = itemView.findViewById(R.id.textviewName);
            imageShoe = itemView.findViewById(R.id.imageShoe);
        }
    }
}
