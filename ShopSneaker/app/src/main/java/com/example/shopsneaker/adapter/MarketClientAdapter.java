package com.example.shopsneaker.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shopsneaker.R;
import com.example.shopsneaker.model.Market;
import com.example.shopsneaker.retrofit.ApiService;
import com.example.shopsneaker.retrofit.RetrofitClient;
import com.example.shopsneaker.utils.Utils;

import java.util.List;
import io.reactivex.rxjava3.disposables.CompositeDisposable;

public class MarketClientAdapter extends RecyclerView.Adapter<MarketClientAdapter.MyViewHolder>{
    RecyclerView.RecycledViewPool recycledViewPool =  new RecyclerView.RecycledViewPool();
    Context context;
    List<Market> array;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private ApiService apiBanGiay = RetrofitClient.getInstance(Utils.BASE_URL).create(ApiService.class);;


    public MarketClientAdapter(Context context, List<Market> array) {
        this.context = context;
        this.array = array;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_post_client,parent, false);
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
        holder.txtStatus.setText(market.getStatusname());
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

    }

    @Override
    public int getItemCount() {
        return array.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView txtCustomerName,txtShoesName,txtPrice,txtSize,txtDescription,txtStatus;
        RecyclerView rcv_img;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            txtStatus = itemView.findViewById(R.id.txtStatus);
            txtCustomerName = itemView.findViewById(R.id.txtCustomerName);
            txtShoesName = itemView.findViewById(R.id.txtShoesName);
            txtPrice = itemView.findViewById(R.id.txtPrice);
            txtSize = itemView.findViewById(R.id.txtSize);
            txtDescription = itemView.findViewById(R.id.txtDescription);
            rcv_img = itemView.findViewById(R.id.recyclerViewImages);
        }
    }
}
