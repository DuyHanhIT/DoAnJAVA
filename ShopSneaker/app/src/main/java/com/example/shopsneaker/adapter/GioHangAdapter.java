package com.example.shopsneaker.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.shopsneaker.R;
import com.example.shopsneaker.activity.FullImageView;
import com.example.shopsneaker.interFace.ItemClickListener;
import com.example.shopsneaker.model.EventBus.tinhtongtien;
import com.example.shopsneaker.model.GioHang;
import com.example.shopsneaker.utils.Utils;

import org.greenrobot.eventbus.EventBus;

import java.text.DecimalFormat;
import java.util.List;

public class GioHangAdapter extends RecyclerView.Adapter<GioHangAdapter.MyViewHolder> {
    Context context;
    List<GioHang> arrayListgiohang;

    public GioHangAdapter(Context context, List<GioHang> arrayListgiohang) {
        this.context = context;
        this.arrayListgiohang = arrayListgiohang;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(parent.getContext()).inflate(R.layout.dong_giohang, parent, false);
        return new MyViewHolder(item);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        GioHang giohang = arrayListgiohang.get(position);
        holder.textviewtenmonhang.setText(giohang.getShoeName());
        DecimalFormat decimalFormat =new DecimalFormat("###,###,###");
        holder.textviewgiamonhang.setText("Giá: "+ decimalFormat.format(giohang.getPrice())+"USĐ");
        holder.textviewsize.setText("Size: "+ giohang.getSize());
        holder.buttonvalue.setText(arrayListgiohang.get(position).getPurchased()+"");
        if (giohang.getImages().contains("http")){
            Glide.with(context).load(giohang.getImages()).into(holder.imagegiohang);
        }else {
            String hinh = Utils.BASE_URL+"images/" + giohang.getImages();
            Glide.with(context).load(hinh).into(holder.imagegiohang);
        }
        holder.imagegiohang.setOnClickListener(v -> {
            Intent intent = new Intent(context, FullImageView.class);
            intent.putExtra("image",giohang.getImages());
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        });
        holder.setItemClick((view, position1, value) -> {
            if(value == 1){
                if(arrayListgiohang.get(position1).getPurchased()>1){
                    int soluongmoi=arrayListgiohang.get(position1).getPurchased()-1;
                    arrayListgiohang.get(position1).setPurchased(soluongmoi);
                }
                else if(arrayListgiohang.get(position1).getPurchased()==1){
                    AlertDialog.Builder builder = new AlertDialog.Builder(view.getRootView().getContext());
                    builder.setTitle("Thông báo");
                    builder.setMessage("Bạn có muốn xóa sản phẩm này không");
                    builder.setPositiveButton("Đồng ý", (dialog, which) -> {
                        Utils.manggiohang.remove(position1);
                        notifyDataSetChanged();
                        EventBus.getDefault().postSticky(new tinhtongtien());
                    });
                    builder.setNegativeButton("Hủy", (dialog, which) -> {
                        dialog.dismiss();
                    });
                    builder.show();
                }
            }
            else if(value == 2){
                    int soluongmoi=arrayListgiohang.get(position1).getPurchased()+1;
                    arrayListgiohang.get(position1).setPurchased(soluongmoi);
            }
            holder.buttonvalue.setText(arrayListgiohang.get(position1).getPurchased()+"");
            EventBus.getDefault().postSticky(new tinhtongtien());
        });
    }

    @Override
    public int getItemCount() {
        return arrayListgiohang.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView imagegiohang;
        TextView textviewtenmonhang, textviewgiamonhang, textviewsize;
        Button buttonminusleft, buttonvalue, buttonminusright;
        ItemClickListener listener;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            textviewtenmonhang = itemView.findViewById(R.id.textviewtenmonhang);
            textviewsize = itemView.findViewById(R.id.textviewsize);
            textviewgiamonhang = itemView.findViewById(R.id.textviewgiamonhang);
            buttonminusleft = itemView.findViewById(R.id.buttonminusleft);
            buttonminusright = itemView.findViewById(R.id.buttonminusright);
            buttonvalue = itemView.findViewById(R.id.buttonvalue);
            imagegiohang = itemView.findViewById(R.id.imagegiohang);

            //event click
            buttonminusleft.setOnClickListener(this);
            buttonminusright.setOnClickListener(this);
        }
        public void setItemClick(ItemClickListener listener){
            this.listener = listener;
        }

        @Override
        public void onClick(View v) {
            if(v == buttonminusleft){
                listener.onClick(v, getAdapterPosition(),1);
            }
            else if(v == buttonminusright){
                listener.onClick(v, getAdapterPosition(),2);
            }
        }
    }
}