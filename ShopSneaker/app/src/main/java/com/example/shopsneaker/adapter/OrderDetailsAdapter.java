package com.example.shopsneaker.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.shopsneaker.R;
import com.example.shopsneaker.activity.FullImageView;
import com.example.shopsneaker.model.OrderDetails;
import com.example.shopsneaker.retrofit.ApiService;
import com.example.shopsneaker.retrofit.RetrofitClient;
import com.example.shopsneaker.utils.Utils;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class OrderDetailsAdapter extends RecyclerView.Adapter<OrderDetailsAdapter.MyViewHolder> {
    Context context;
    List<OrderDetails> array;
    float x;


    public OrderDetailsAdapter(Context context, List<OrderDetails> array) {
        this.context = context;
        this.array = array;
    }

    private void animateImag( ImageView ratingImage){
        ScaleAnimation scaleAnimation = new ScaleAnimation(0 , 1f, 0 ,1f,Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
        scaleAnimation.setFillAfter(true);
        scaleAnimation.setDuration(200);
        ratingImage.startAnimation(scaleAnimation);
    }



    @NonNull
    @Override
    public OrderDetailsAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(parent.getContext()).inflate(R.layout.dong_sanpham, parent, false);
        return new OrderDetailsAdapter.MyViewHolder(item);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderDetailsAdapter.MyViewHolder holder, int position) {

        OrderDetails shoes = array.get(position);
        holder.textviewName.setText(shoes.getName());
        holder.textviewPrice.setText("Giá: " + shoes.getPrice() + "USD");
        holder.textviewPurchased.setText("Số lượng: "+shoes.getQuantity());
        holder.textviewSize.setText("Size: "+shoes.getSize());
        if (shoes.getImages().contains("http")){
            Glide.with(context).load(shoes.getImages()).into(holder.imageShoe);
        }else {
            String hinh = Utils.BASE_URL+"images/" + shoes.getImages();
            Glide.with(context).load(hinh).into(holder.imageShoe);
        }
        holder.imageShoe.setOnClickListener(v -> {
            Intent intent = new Intent(context, FullImageView.class);
            intent.putExtra("image",shoes.getImages());
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        });
        int a = shoes.getStatusid();
        int shoesid;
        shoesid= shoes.getId();
        holder.itemView.setOnClickListener(v -> {
            ImageView ratingImage;
            RatingBar ratingBar;
            CompositeDisposable compositeDisposable = new CompositeDisposable();
            ApiService apiBanGiay;
            apiBanGiay = RetrofitClient.getInstance(Utils.BASE_URL).create(ApiService.class);
            AlertDialog.Builder builder= new AlertDialog.Builder(v.getRootView().getContext());
            View view = LayoutInflater.from(v.getRootView().getContext()).inflate(R.layout.rate_us_dialog_layout,null);
            ratingBar = view.findViewById(R.id.ratingBar);
            ratingImage = view.findViewById(R.id.ratingImage);

            ratingBar.setOnRatingBarChangeListener((ratingBar1, rating, fromUser) -> {
                if(rating <= 1){
                    ratingImage.setImageResource(R.drawable.angryicon);
                } else if(rating <= 2){
                    ratingImage.setImageResource(R.drawable.sadicon);
                } else if(rating <= 3){
                    ratingImage.setImageResource(R.drawable.normalicon);
                } else if(rating <= 4){
                    ratingImage.setImageResource(R.drawable.smileicon);
                } else if(rating <= 5){
                    ratingImage.setImageResource(R.drawable.happyicon);
                }
                x = rating;
                //animate emoji image
                animateImag(ratingImage);

            });

            builder.setPositiveButton("Đánh giá ngay", (dialog, which) -> {
                if(a==4){
                    int AccountId = Utils.user_current.getAccountid();
                    compositeDisposable.add(apiBanGiay.RatingStar(shoesid, AccountId, x).subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(
                                    RatesModel -> {
                                        Toast.makeText(context.getApplicationContext(), "Bạn đã đánh giá thành công!", Toast.LENGTH_LONG).show();
                                        dialog.dismiss();
                                    },
                                    throwable -> {
                                        Toast.makeText(context.getApplicationContext(), "Bạn đã đánh giá thành công!", Toast.LENGTH_LONG).show();
                                    }
                            ));
                }
                else {
                    Toast.makeText(context.getApplicationContext(), "Bạn chưa thể đánh giá sản phẩm này", Toast.LENGTH_LONG).show();
                }
            }).setNegativeButton("Để sau", (dialog, id) -> dialog.dismiss()).setView(view).setCancelable(true);
            builder.show();


        });


    }

    @Override
    public int getItemCount() {
        return array.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView textviewPrice, textviewSize, textviewPurchased, textviewName;
        ImageView imageShoe;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            textviewPrice = itemView.findViewById(R.id.textviewPrice);
            textviewName = itemView.findViewById(R.id.textviewName);
            textviewSize = itemView.findViewById(R.id.textviewSize);
            textviewPurchased = itemView.findViewById(R.id.textviewPurchased);
            imageShoe = itemView.findViewById(R.id.imageShoe);


        }
    }
}
