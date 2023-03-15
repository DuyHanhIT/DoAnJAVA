package com.example.shopsneaker.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shopsneaker.R;
import com.example.shopsneaker.model.statistics;

import java.util.List;

public class statisticsDateAdapter extends RecyclerView.Adapter<statisticsDateAdapter.MyViewHolder>{
    Context context;
    List<statistics> array;

    public statisticsDateAdapter(Context context, List<statistics> array) {
        this.context = context;
        this.array = array;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View item = android.view.LayoutInflater.from(parent.getContext()).inflate(R.layout.item_statistics_date,parent,false);
        return new MyViewHolder(item);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        statistics statistics = array.get(position);
        holder.txtDay.setText(statistics.getDay()+"");
        holder.txtSellNum.setText(statistics.getSellnumber()+"");
        holder.txttotal.setText(statistics.getTurnover()+"USD");
        holder.txtOrder.setText(statistics.getCount()+"");
    }

    @Override
    public int getItemCount() {
        return array.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView txtDay, txtSellNum,txttotal,txtOrder;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            txtOrder = itemView.findViewById(R.id.txtOrder);
            txtDay = itemView.findViewById(R.id.txtDay);
            txtSellNum = itemView.findViewById(R.id.txtSellNumber);
            txttotal = itemView.findViewById(R.id.txtTurnover);
        }
    }
}
