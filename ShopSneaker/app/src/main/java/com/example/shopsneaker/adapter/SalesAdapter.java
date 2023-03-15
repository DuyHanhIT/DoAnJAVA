package com.example.shopsneaker.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.shopsneaker.R;
import com.example.shopsneaker.activity.OrderDetailsActivity;
import com.example.shopsneaker.activity.ProductDetailActivity;
import com.example.shopsneaker.activity.SaleDetailsActivity;
import com.example.shopsneaker.activity.SalesActivity;
import com.example.shopsneaker.interFace.ItemLongClickListener;
import com.example.shopsneaker.model.EventBus.SuaXoaEvent;
import com.example.shopsneaker.model.EventBus.UDSales;
import com.example.shopsneaker.model.Sales;
import com.example.shopsneaker.utils.checkconnect;

import org.greenrobot.eventbus.EventBus;

import java.text.SimpleDateFormat;
import java.util.List;

public class SalesAdapter extends RecyclerView.Adapter<SalesAdapter.MyViewHolder> {
    Context context;
    List<Sales> array;

    public SalesAdapter(Context context, List<Sales> array) {
        this.context = context;
        this.array = array;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View item = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_sales, parent, false);
        return new SalesAdapter.MyViewHolder(item);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int i) {
        Sales sales = array.get(i);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        holder.txtSalesName.setText(sales.getSalesname());
        holder.txtStartDay.setText(simpleDateFormat.format(sales.getStartday()));
        holder.txtEndDay.setText(simpleDateFormat.format(sales.getEndday()));
        holder.txtContent.setText(sales.getContent());
        holder.txtPercent.setText(sales.getPercent()+"%");
        holder.setItemLongClickListener((view, position1, isLongClick) -> {
            EventBus.getDefault().postSticky(new UDSales(sales));
        });
    }

    @Override
    public int getItemCount() {
        return array.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener, View.OnLongClickListener {
        private com.example.shopsneaker.interFace.ItemLongClickListener itemLongClickListener;
        public TextView txtSalesName, txtStartDay, txtEndDay, txtContent,txtPercent;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            txtSalesName = itemView.findViewById(R.id.txtSalesName);
            txtStartDay = itemView.findViewById(R.id.txtStartDay);
            txtEndDay = itemView.findViewById(R.id.txtEndDay);
            txtContent = itemView.findViewById(R.id.txtContent);
            txtPercent = itemView.findViewById(R.id.txtPercent);
            itemView.setOnClickListener(v -> {
                Intent intent=new Intent(context, SaleDetailsActivity.class);
                intent.putExtra("idsales", array.get(getAdapterPosition()).getSalesid());
                intent.putExtra("salesName", array.get(getAdapterPosition()).getSalesname());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                checkconnect.ShowToast_Short(context,array.get(getAdapterPosition()).getSalesname());
                context.startActivity(intent);
            });
            itemView.setOnCreateContextMenuListener(this);
            itemView.setOnLongClickListener(this);
        }
        public void setItemLongClickListener(ItemLongClickListener itemLongClickListener) {
            this.itemLongClickListener = itemLongClickListener;
        }
        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            menu.add(0,0,getAdapterPosition(),"sửa");
            menu.add(0,1,getAdapterPosition(),"xóa");
        }

        @Override
        public boolean onLongClick(View v) {
            itemLongClickListener.onClick(v,getAdapterPosition(),true);
            return false;
        }
    }

}
