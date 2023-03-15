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

public class statisticsIdAdapter extends RecyclerView.Adapter<statisticsIdAdapter.MyViewHolder> implements android.widget.Filterable{
    Context context;
    List<statistics> array;
    List<statistics> arrayOld;

    public statisticsIdAdapter(Context context, List<statistics> array) {
        this.context = context;
        this.array = array;
        arrayOld=array;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View item = android.view.LayoutInflater.from(parent.getContext()).inflate(R.layout.item_statistics_shoes,parent,false);
        return new MyViewHolder(item);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        statistics statistics = array.get(position);
        holder.txtID.setText(statistics.getName()+"");
        holder.txtMonth.setText(statistics.getMonth()+"");
        holder.txtYear.setText(statistics.getYear()+"");
        holder.txtSellNubmber.setText(statistics.getSellnumber()+"");
        holder.txtrevenue.setText(statistics.getTurnover()+"USD");
    }

    @Override
    public int getItemCount() {
        return array.size();
    }

    @Override
    public android.widget.Filter getFilter() {
        return new android.widget.Filter() {
            @Override
            protected android.widget.Filter.FilterResults performFiltering(CharSequence charSequence) {
                String strSearch = charSequence.toString();
                if (strSearch.isEmpty()){
                    array=arrayOld;
                }
                else {
                    List<statistics> statisticsList = new java.util.ArrayList<>();
                    for (statistics statisticss: arrayOld){
                        if (statisticss.getName().toLowerCase().contains(strSearch.toLowerCase())){
                            statisticsList.add(statisticss);
                        }
                    }
                    array=statisticsList;
                }
                android.widget.Filter.FilterResults filterResults = new android.widget.Filter.FilterResults();
                filterResults.values =array;
                return  filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, android.widget.Filter.FilterResults filterResults) {
                array= (java.util.List<com.example.shopsneaker.model.statistics>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView txtID, txtMonth,txtYear,txtSellNubmber,txtrevenue;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            txtID = itemView.findViewById(R.id.txtID);
            txtMonth = itemView.findViewById(R.id.txtMonth);
            txtYear = itemView.findViewById(R.id.txtYear);
            txtSellNubmber = itemView.findViewById(R.id.txtSellNubmber);
            txtrevenue = itemView.findViewById(R.id.txtrevenue);
        }
    }
}
