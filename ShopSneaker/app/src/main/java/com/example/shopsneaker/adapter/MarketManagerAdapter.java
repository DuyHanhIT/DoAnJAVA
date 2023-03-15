package com.example.shopsneaker.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shopsneaker.R;
import com.example.shopsneaker.activity.MarketManagerActivity;
import com.example.shopsneaker.interFace.ItemClickListener;
import com.example.shopsneaker.model.Market;
import com.example.shopsneaker.retrofit.ApiService;
import com.example.shopsneaker.retrofit.RetrofitClient;
import com.example.shopsneaker.utils.Utils;

import java.util.List;

import io.reactivex.rxjava3.disposables.CompositeDisposable;

public class MarketManagerAdapter extends RecyclerView.Adapter<MarketManagerAdapter.MyViewHolder>{
    RecyclerView.RecycledViewPool recycledViewPool =  new RecyclerView.RecycledViewPool();
    Context context;
    List<Market> array;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private ApiService apiBanGiay = RetrofitClient.getInstance(Utils.BASE_URL).create(ApiService.class);;

    public MarketManagerAdapter(Context context, List<Market> array) {
        this.context = context;
        this.array = array;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_posts_managment,parent, false);
        return new MyViewHolder(item);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Market market = array.get(position);
        holder.txtname.setText(market.getName());
        holder.sttname.setText(market.getStatusname());
        holder.txtshoesname.setText(market.getShoesname());
        holder.txtprice.setText(market.getPrice()+"USD");
        holder.txtsize.setText("Size: "+market.getSize());
        holder.txtdescrip.setText(market.getDescription());
        int x = market.getId();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(
          holder.rcvImages.getContext(), LinearLayoutManager.VERTICAL, false
        );
        linearLayoutManager.setInitialPrefetchItemCount(market.getImages().size());
        ImagesAdapter imagesAdapter = new ImagesAdapter(context,market.getImages());
        holder.rcvImages.setLayoutManager(linearLayoutManager);
        holder.rcvImages.setAdapter(imagesAdapter);
        holder.rcvImages.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL,false));
        holder.rcvImages.setHasFixedSize(true);
        holder.rcvImages.setRecycledViewPool(recycledViewPool);
        if(market.getStatusid()==2){
            holder.btn.setVisibility(View.INVISIBLE);
        }
        holder.setItemClick((view, position1, value) -> {
            if(value==1){
                compositeDisposable.add(apiBanGiay.updateMarket(x)
                        .subscribeOn(io.reactivex.rxjava3.schedulers.Schedulers.io())
                        .observeOn(io.reactivex.rxjava3.android.schedulers.AndroidSchedulers.mainThread())
                        .subscribe(
                                marketModel -> {
                                    if (marketModel.isSuccess()){
                                        Intent intent=new Intent(context, MarketManagerActivity.class);
                                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                        context.startActivity(intent);
                                    }
                                },
                                throwable -> {
                                    Toast.makeText(context, "Khong ket noi duoc voi server", Toast.LENGTH_LONG).show();
                                }
                        ));
            }
        });
    }



    @Override
    public int getItemCount() {
        return array.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView txtname,txtshoesname,txtprice,txtsize,txtdescrip, sttname;
        RecyclerView rcvImages;
        Button btn;
        ItemClickListener listener;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            btn = itemView.findViewById(R.id.buttonAcp);
            sttname = itemView.findViewById(R.id.txtStatus);
            txtname = itemView.findViewById(R.id.txtCustomerName);
            txtshoesname = itemView.findViewById(R.id.txtShoesName);
            txtprice = itemView.findViewById(R.id.txtPrice);
            txtsize = itemView.findViewById(R.id.txtSize);
            txtdescrip = itemView.findViewById(R.id.txtDescription);
            rcvImages = itemView.findViewById(R.id.recyclerViewImages);
            btn.setOnClickListener(this);
        }

        public void setItemClick(ItemClickListener listener){
            this.listener = listener;
        }

        @Override
        public void onClick(View v) {
            if(v==btn) {listener.onClick(v, getAdapterPosition(),1);}
        }
    }
}
