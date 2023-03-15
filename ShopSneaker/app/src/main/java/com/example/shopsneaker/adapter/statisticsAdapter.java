package com.example.shopsneaker.adapter;

public class statisticsAdapter extends androidx.recyclerview.widget.RecyclerView.Adapter<statisticsAdapter.MyViewHolder> {
    android.content.Context context;
    java.util.List<com.example.shopsneaker.model.statistics> array;

    public statisticsAdapter(android.content.Context context, java.util.List<com.example.shopsneaker.model.statistics> array) {
        this.context = context;
        this.array = array;
    }

    @androidx.annotation.NonNull
    @Override
    public com.example.shopsneaker.adapter.statisticsAdapter.MyViewHolder onCreateViewHolder(@androidx.annotation.NonNull android.view.ViewGroup parent, int viewType) {
        android.view.View item = android.view.LayoutInflater.from(parent.getContext()).inflate(com.example.shopsneaker.R.layout.item_revenue,parent,false);
        return new com.example.shopsneaker.adapter.statisticsAdapter.MyViewHolder(item);
    }

    @Override
    public void onBindViewHolder(@androidx.annotation.NonNull com.example.shopsneaker.adapter.statisticsAdapter.MyViewHolder holder, int position) {
        com.example.shopsneaker.model.statistics statistics = array.get(position);
        holder.month.setText(statistics.getMonth()+"");
        holder.year.setText(statistics.getYear()+"");
        holder.sellnumber.setText(statistics.getSellnumber()+"");
        holder.turnover.setText(statistics.getTurnover()+"USD");
        holder.txtOrder.setText(statistics.getCount()+"");
    }

    @Override
    public int getItemCount() {
        return array.size();
    }

    public class MyViewHolder extends androidx.recyclerview.widget.RecyclerView.ViewHolder {
        android.widget.TextView month,year,sellnumber,turnover,txtOrder;
        public MyViewHolder(@androidx.annotation.NonNull android.view.View itemView) {
            super(itemView);
            txtOrder = itemView.findViewById(com.example.shopsneaker.R.id.txtOrder);
            month = itemView.findViewById(com.example.shopsneaker.R.id.txtMonth);
            year = itemView.findViewById(com.example.shopsneaker.R.id.txtYear);
            sellnumber = itemView.findViewById(com.example.shopsneaker.R.id.txtSellNubmber);
            turnover = itemView.findViewById(com.example.shopsneaker.R.id.txtrevenue);
        }
    }
}