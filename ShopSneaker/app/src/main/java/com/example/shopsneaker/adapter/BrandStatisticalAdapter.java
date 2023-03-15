package com.example.shopsneaker.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shopsneaker.R;
import com.example.shopsneaker.activity.ProductManagement;
import com.example.shopsneaker.model.Brand;

import java.util.List;

public class BrandStatisticalAdapter extends RecyclerView.Adapter<BrandStatisticalAdapter.MyViewHolder>{
    Context context;
    List<Brand> array;
    Intent intent;

    public BrandStatisticalAdapter(Context context, List<Brand> array) {
        this.context = context;
        this.array = array;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_tksp,parent, false);
        return new MyViewHolder(item);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Brand brand = array.get(position);
        holder.brandName.setText(brand.getBrandname());
        if (brand.getCount()!=0){
            holder.countBrand.setText("Số lượng: "+brand.getCount());
            holder.itemView.setOnClickListener(view ->{
                intent =new Intent(context, ProductManagement.class);
                intent.putExtra("brandId",array.get(position).getBrandid());
                intent.putExtra("Title",array.get(position).getBrandname());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            });
        }else {
            holder.countBrand.setText("Sản phẩm đã bán: "+brand.getCountspdaban());
            holder.sellnumber.setText("Doanh thu: "+brand.getTurnover()+" USD");
            holder.itemView.setOnClickListener(view ->{
                intent =new Intent(context, com.example.shopsneaker.activity.StatisticsById.class);
                intent.putExtra("brandid",array.get(position).getBrandid());
                intent.putExtra("Title",array.get(position).getBrandname());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            });


        }

    }

    @Override
    public int getItemCount() {
        return array.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView brandName, countBrand, sellnumber,Turnover;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            brandName = itemView.findViewById(R.id.brandName);
            countBrand = itemView.findViewById(R.id.countBrand);
            sellnumber = itemView.findViewById(R.id.txtSellNumberr);
            Turnover=itemView.findViewById(R.id.txtTurnoverr);

        }
    }
}
