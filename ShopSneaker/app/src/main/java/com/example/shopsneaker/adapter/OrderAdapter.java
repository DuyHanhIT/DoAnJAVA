package com.example.shopsneaker.adapter;

import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog.Builder;
import androidx.recyclerview.widget.RecyclerView;
import com.example.shopsneaker.R;
import com.example.shopsneaker.activity.OrderDetailsActivity;
import com.example.shopsneaker.activity.UpdateOrder;
import com.example.shopsneaker.model.Order;
import com.example.shopsneaker.retrofit.ApiService;
import com.example.shopsneaker.utils.Utils;
import com.example.shopsneaker.utils.checkconnect;
import java.util.List;

import io.reactivex.rxjava3.disposables.CompositeDisposable;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.MyViewHolder> {
    Context context;
    List<Order> array;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    ApiService apiBanGiay= com.example.shopsneaker.retrofit.RetrofitClient.getInstance(Utils.BASE_URL).create(ApiService .class);


    public OrderAdapter(Context context, List<Order> array) {
        this.context = context;
        this.array = array;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View item = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_order, parent, false);
        return new OrderAdapter.MyViewHolder(item);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int i) {
        Order shoes = array.get(i);
        holder.textviewDate.setText("Ngày đặt: "+ shoes.getBookingdate());
        holder.textviewTotal.setText("Tổng tiền: " + shoes.getTotal() + "USD");
        holder.textviewOrderId.setText("Đơn đặt hàng: "+shoes.getOrderid());
        holder.textviewStatusId.setText("Tình trạng: "+shoes.getStatusname());
        if(shoes.getStatusid()==4 || shoes.getStatusid()==5){
            holder.btncancel.setVisibility(INVISIBLE);
            holder.btncancel.setEnabled(false);
        }else {
            holder.btncancel.setVisibility(VISIBLE);
            holder.btncancel.setOnClickListener(view -> {
                Builder b = new Builder(view.getContext());
                //Thiết lập tiêu đề
                b.setTitle("Thông báo");
                b.setMessage("Bạn chắc chắn muốn hủy đơn hàng này ?");
                b.setPositiveButton("Đồng ý", new android.content.DialogInterface.OnClickListener() {
                    public void onClick(android.content.DialogInterface dialog, int id) {
                        compositeDisposable.add(apiBanGiay.updateOrder(shoes.getOrderid(), "Đã hủy")
                                .subscribeOn(io.reactivex.rxjava3.schedulers.Schedulers.io())
                                .observeOn(io.reactivex.rxjava3.android.schedulers.AndroidSchedulers.mainThread())
                                .subscribe(
                                        updateOrderModel -> {
                                            if(updateOrderModel.isSuccess()){
                                                holder.textviewStatusId.setText("Tình trạng: Đã hủy");
                                                android.widget.Toast.makeText(context,"Hủy thành công", android.widget.Toast.LENGTH_LONG).show();
                                                holder.btncancel.setEnabled(false);
                                                holder.btncancel.setVisibility(INVISIBLE);
                                            }else {
                                                android.widget.Toast.makeText(context,"Cập nhật thất bại", android.widget.Toast.LENGTH_LONG).show();
                                            }
                                        },
                                        throwable -> {
                                            android.widget.Toast.makeText(context,"Không kết nối được server", android.widget.Toast.LENGTH_LONG).show();
                                        }
                                ));
                    }
                });
                b.setNegativeButton("Cancel", new android.content.DialogInterface.OnClickListener() {
                    public void onClick(android.content.DialogInterface dialog, int id) {

                        return;
                    }
                });//Tạo dialog
                androidx.appcompat.app.AlertDialog al = b.create();//Hiển thị
                al.show();

            });
        }

    }

    @Override
    public int getItemCount() {
        return array.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView textviewOrderId, textviewDate, textviewTotal, textviewStatusId;
        Button btncancel;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            textviewOrderId = itemView.findViewById(R.id.textviewOrderId);
            textviewDate = itemView.findViewById(R.id.textviewDate);
            textviewTotal = itemView.findViewById(R.id.textviewTotal);
            textviewStatusId = itemView.findViewById(R.id.textviewStatusId);
            btncancel= itemView.findViewById(com.example.shopsneaker.R.id.cancelorder);
            itemView.setOnClickListener(v -> {
                Intent intent=new Intent(context,OrderDetailsActivity.class);
                intent.putExtra("iddh", array.get(getAdapterPosition()).getOrderid());
                intent.putExtra("statusid", array.get(getAdapterPosition()).getStatusid());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                checkconnect.ShowToast_Short(context,array.get(getAdapterPosition()).getOrderid().toString());
                context.startActivity(intent);
            });
        }
    }
}
