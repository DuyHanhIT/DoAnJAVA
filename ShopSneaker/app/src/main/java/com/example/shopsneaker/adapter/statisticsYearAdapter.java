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

public class statisticsYearAdapter extends RecyclerView.Adapter<statisticsYearAdapter.MyViewHolder>{
    Context context;
    List<statistics> array;

    public statisticsYearAdapter(Context context, List<statistics> array) {
        this.context = context;
        this.array = array;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View item = android.view.LayoutInflater.from(parent.getContext()).inflate(R.layout.item_statistics_year,parent,false);
        return new MyViewHolder(item);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        statistics statistics = array.get(position);
        holder.year.setText(statistics.getYear()+"");
        holder.turnover.setText(statistics.getTurnover()+"USD");
        holder.sellnumber.setText(statistics.getSellnumber()+"");
        holder.txtOrder.setText(statistics.getCount()+"");
    }

    @Override
    public int getItemCount() {
        return array.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView year, turnover,sellnumber,txtOrder;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            txtOrder = itemView.findViewById(R.id.txtOrder);
            year = itemView.findViewById(R.id.txtYear);
            turnover = itemView.findViewById(R.id.txtturnover);
            sellnumber = itemView.findViewById(R.id.txtSellNum);
        }
    }
}
