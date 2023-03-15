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
import com.example.shopsneaker.activity.updateSizeActivity;
import com.example.shopsneaker.model.SizeManagment;
import com.example.shopsneaker.utils.Utils;
import com.example.shopsneaker.utils.checkconnect;

import java.util.List;

public class SizeManagmentAdapter extends RecyclerView.Adapter<SizeManagmentAdapter.MyViewHolder> {
    Context context;
    List<SizeManagment> array;

    public SizeManagmentAdapter(Context context, List<SizeManagment> array) {
        this.context = context;
        this.array = array;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product_size, parent, false);
        return new MyViewHolder(item);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        SizeManagment sizeManagment = array.get(position);
        holder.txtId.setText(sizeManagment.getSizeid()+"");
        holder.txtname.setText(sizeManagment.getName());
        holder.s38.setText(sizeManagment.getS38()+"");
        holder.s39.setText(sizeManagment.getS39()+"");
        holder.s40.setText(sizeManagment.getS40()+"");
        holder.s41.setText(sizeManagment.getS41()+"");
        holder.s42.setText(sizeManagment.getS42()+"");
        holder.s43.setText(sizeManagment.getS43()+"");
        holder.s44.setText(sizeManagment.getS44()+"");
        holder.s45.setText(sizeManagment.getS45()+"");
        holder.s46.setText(sizeManagment.getS46()+"");
        holder.s47.setText(sizeManagment.getS47()+"");
        holder.s48.setText(sizeManagment.getS48()+"");
        if (sizeManagment.getImages().contains("http")){
            Glide.with(context).load(sizeManagment.getImages()).into(holder.images);
        }else {
            String hinh = Utils.BASE_URL+"images/" + sizeManagment.getImages();
            Glide.with(context).load(hinh).into(holder.images);
        }
        holder.images.setOnClickListener(v -> {
            Intent intent = new Intent(context, FullImageView.class);
            intent.putExtra("image",sizeManagment.getImages());
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return array.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView txtId,txtname,s38,s39,s40,s41,s42,s43,s44,s45,s46,s47,s48;
        ImageView images;
        private com.example.shopsneaker.interFace.ItemLongClickListener itemLongClickListener;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            txtId = itemView.findViewById(R.id.txtIdSP);
            txtname = itemView.findViewById(R.id.txtName);
            images = itemView.findViewById(R.id.imageShoes1);
            s38 = itemView.findViewById(R.id.s38);
            s39 = itemView.findViewById(R.id.s39);
            s40 = itemView.findViewById(R.id.s40);
            s41 = itemView.findViewById(R.id.s41);
            s42 = itemView.findViewById(R.id.s42);
            s43 = itemView.findViewById(R.id.s43);
            s44 = itemView.findViewById(R.id.s44);
            s45 = itemView.findViewById(R.id.s45);
            s46 = itemView.findViewById(R.id.s46);
            s47 = itemView.findViewById(R.id.s47);
            s48 = itemView.findViewById(R.id.s48);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, updateSizeActivity.class);
                    intent.putExtra("themsize", array.get(getPosition()));
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    checkconnect.ShowToast_Short(context,array.get(getPosition()).getName());
                    context.startActivity(intent);
                }
            });
        }
    }
}
/*public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener, View.OnLongClickListener {


    public MyViewHolder(@NonNull View itemView) {
        super(itemView);
        txtname = itemView.findViewById(R.id.)

        itemView.setOnCreateContextMenuListener(this);
        itemView.setOnLongClickListener(this);
    }

    public void setItemLongClickListener(ItemLongClickListener itemLongClickListener) {
        this.itemLongClickListener = itemLongClickListener;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        menu.add(0,0,getAdapterPosition(),"sửa");
    }

    @Override
    public boolean onLongClick(View v) {
        itemLongClickListener.onClick(v,getAdapterPosition(),true);
        return false;
    }
}*/