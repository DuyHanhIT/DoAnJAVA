package com.example.shopsneaker.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shopsneaker.R;
import com.example.shopsneaker.activity.ChatActivity;
import com.example.shopsneaker.model.Market;
import com.example.shopsneaker.retrofit.ApiService;
import com.example.shopsneaker.retrofit.RetrofitClient;
import com.example.shopsneaker.utils.Utils;

import java.util.List;
import io.reactivex.rxjava3.disposables.CompositeDisposable;

public class MarketAdapter extends RecyclerView.Adapter<MarketAdapter.MyViewHolder>{
    RecyclerView.RecycledViewPool recycledViewPool =  new RecyclerView.RecycledViewPool();
    Context context;
    List<Market> array;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private ApiService apiBanGiay = RetrofitClient.getInstance(Utils.BASE_URL).create(ApiService.class);;


    public MarketAdapter(Context context, List<Market> array) {
        this.context = context;
        this.array = array;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_post_text,parent, false);
        return new MyViewHolder(item);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Market market = array.get(position);
        holder.txtCustomerName.setText(market.getName());
        holder.txtShoesName.setText(market.getShoesname());
        holder.txtPrice.setText("Giá: "+market.getPrice()+" USD");
        holder.txtSize.setText("Size: "+market.getSize());
        holder.txtDescription.setText(market.getDescription());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(
                holder.rcv_img.getContext(), LinearLayoutManager.VERTICAL, false
        );
        linearLayoutManager.setInitialPrefetchItemCount(market.getImages().size());
        ImagesAdapter imagesAdapter = new ImagesAdapter(context,market.getImages());
        holder.rcv_img.setLayoutManager(linearLayoutManager);
        holder.rcv_img.setAdapter(imagesAdapter);
        holder.rcv_img.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL,false));
        holder.rcv_img.setHasFixedSize(true);
        holder.rcv_img.setRecycledViewPool(recycledViewPool);
        holder.imgAdd.setOnClickListener(v -> {
            Intent intent=new Intent(context, ChatActivity.class);
            intent.putExtra("accountid",array.get(position).getAccountid());
            intent.putExtra("name",array.get(position).getName());
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        });

    }

    @Override
    public int getItemCount() {
        return array.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView txtCustomerName,txtShoesName,txtPrice,txtSize,txtDescription;
        RecyclerView rcv_img;
        ImageView imgAdd;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            txtCustomerName = itemView.findViewById(R.id.txtCustomerName);
            txtShoesName = itemView.findViewById(R.id.txtShoesName);
            txtPrice = itemView.findViewById(R.id.txtPrice);
            txtSize = itemView.findViewById(R.id.txtSize);
            txtDescription = itemView.findViewById(R.id.txtDescription);
            imgAdd = itemView.findViewById(R.id.imgAdd);
            rcv_img = itemView.findViewById(R.id.recyclerViewImages);
        }
    }
}
