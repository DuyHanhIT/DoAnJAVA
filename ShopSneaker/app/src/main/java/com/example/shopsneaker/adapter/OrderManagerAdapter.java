package com.example.shopsneaker.adapter;


import android.content.Context;
import android.content.Intent;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

import com.example.shopsneaker.R;
import com.example.shopsneaker.activity.OrderDetailsActivity;
import com.example.shopsneaker.activity.OrderManagerActivity;
import com.example.shopsneaker.activity.UpdateOrder;
import com.example.shopsneaker.interFace.ItemClickListener;
import com.example.shopsneaker.interFace.ItemLongClickListener;
import com.example.shopsneaker.model.EventBus.SuaOrder;
import com.example.shopsneaker.model.Order;
import com.example.shopsneaker.utils.checkconnect;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

public class OrderManagerAdapter extends RecyclerView.Adapter<OrderManagerAdapter.MyViewHolder> implements android.widget.Filterable {
    Context context;
    List<Order> array;
    List<Order> arrayOld;
    public OrderManagerAdapter(Context context, List<Order> array) {
        this.context = context;
        this.array = array;
        arrayOld=array;
    }

    @androidx.annotation.NonNull
    @Override
    public OrderManagerAdapter.MyViewHolder onCreateViewHolder(@androidx.annotation.NonNull android.view.ViewGroup parent, int viewType) {
        android.view.View item = android.view.LayoutInflater.from(parent.getContext()).inflate(R.layout.item_order_manager, parent, false);
        return new MyViewHolder(item);
    }

    @Override
    public void onBindViewHolder(@androidx.annotation.NonNull OrderManagerAdapter.MyViewHolder holder, int position) {
        com.example.shopsneaker.model.Order order = array.get(position);
        holder.txtorderID.setText("Mã đơn hàng: "+order.getOrderid());
        holder.txtbookingdate.setText("Ngày đặt: " + order.getBookingdate());
        holder.name.setText("Tên khách hàng: " + order.getName());
        holder.address.setText("Địa chỉ: " + order.getAddress());
        holder.phone.setText("Số điện thoại: " + order.getPhone());
        holder.email.setText("Email: " + order.getEmail());
        holder.status.setText("Tình trạng: " + order.getStatusname());
        holder.total.setText("Tổng tiền: " + order.getTotal()+"USD");
    }

    @Override
    public int getItemCount() {
        return array.size();
    }



    public class MyViewHolder extends RecyclerView.ViewHolder {
        android.widget.TextView txtorderID,txtbookingdate,name,address,phone,email,total,status;

        public MyViewHolder(@androidx.annotation.NonNull android.view.View itemView) {
            super(itemView);
            txtorderID = itemView.findViewById(com.example.shopsneaker.R.id.textviewOrderId);
            txtbookingdate = itemView.findViewById(com.example.shopsneaker.R.id.txtbookingdate);
            name = itemView.findViewById(com.example.shopsneaker.R.id.txtName);
            address = itemView.findViewById(com.example.shopsneaker.R.id.txtaddress);
            phone = itemView.findViewById(com.example.shopsneaker.R.id.txtphonenumber);
            email = itemView.findViewById(com.example.shopsneaker.R.id.txtemail);
            total = itemView.findViewById(com.example.shopsneaker.R.id.txtTotal);
            status = itemView.findViewById(com.example.shopsneaker.R.id.textviewStatusId);
            itemView.setOnClickListener(v -> {
                Intent intent=new Intent(context, UpdateOrder.class);
                intent.putExtra("update", array.get(getAdapterPosition()));
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                checkconnect.ShowToast_Short(context,array.get(getAdapterPosition()).getOrderid().toString());
                context.startActivity(intent);

            });
        }


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
                    List<Order> list = new java.util.ArrayList<>();
                    for (Order order: arrayOld){
                        if (order.getOrderid().toString().contains(strSearch.toLowerCase())){
                            list.add(order);
                        }
                    }
                    array=list;
                }
                android.widget.Filter.FilterResults filterResults = new android.widget.Filter.FilterResults();
                filterResults.values= array;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, android.widget.Filter.FilterResults filterResults) {
                array =(List<com.example.shopsneaker.model.Order>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }
}
