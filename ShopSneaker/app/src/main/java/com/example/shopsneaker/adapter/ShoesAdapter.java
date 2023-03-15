package com.example.shopsneaker.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.shopsneaker.R;
import com.example.shopsneaker.activity.FullImageView;
import com.example.shopsneaker.activity.ProductDetailActivity;
import com.example.shopsneaker.interFace.ItemClickListener;
import com.example.shopsneaker.interFace.ItemLongClickListener;
import com.example.shopsneaker.model.EventBus.SuaXoaEvent;
import com.example.shopsneaker.model.Shoes;
import com.example.shopsneaker.model.User;
import com.example.shopsneaker.utils.Utils;
import com.example.shopsneaker.utils.checkconnect;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import io.paperdb.Paper;

public class ShoesAdapter extends RecyclerView.Adapter<ShoesAdapter.MyViewHolder> implements android.widget.Filterable {

    Context context;
    List<Shoes> array;
    List<Shoes> arrayOld;

    public ShoesAdapter(Context context, List<Shoes> array) {
        this.context = context;
        this.array = array;
        this.arrayOld=array;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_shoes, parent, false);
        return new MyViewHolder(item);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Shoes shoes=array.get(position);
        holder.name.setText(shoes.getShoeName());
        java.util.Date date = new java.util.Date();
        if (shoes.getSaleprice()==0|| shoes.getStartday().after(date) || shoes.getEndday().before(date)){
            holder.price.setText(shoes.getPrice()+" USD");
            holder.price.setTextColor(android.graphics.Color.rgb(255,68,68));
            holder.saleprice.setVisibility(View.INVISIBLE);
            holder.linearLayoutIconSale.setVisibility(android.view.View.INVISIBLE);
        }else {
            String htmlcontent = "<strike>"+shoes.getPrice()+" USD</strike>";
            holder.price.setText(android.text.Html.fromHtml(htmlcontent));
            holder.price.setTextColor(android.graphics.Color.rgb(119,116,116));
            holder.saleprice.setText(shoes.getSaleprice()+" USD");
            holder.percent.setText(shoes.getPercent()+" %");
            holder.saleprice.setVisibility(android.view.View.VISIBLE);//holder.icon_SaleShoes.setVisibility(android.view.View.VISIBLE);
            holder.linearLayoutIconSale.setVisibility(android.view.View.VISIBLE);

        }
        if (shoes.getImages().contains("http")){
            Glide.with(context).load(shoes.getImages()).into(holder.images);
        }else {
            String hinh = Utils.BASE_URL+"images/" + shoes.getImages();
            Glide.with(context).load(hinh).into(holder.images);
        }
        holder.images.setOnClickListener(v -> {
            Intent intent = new Intent(context, FullImageView.class);
            intent.putExtra("image",shoes.getImages());
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        });
        holder.purchased.setText("Đã bán "+shoes.getPurchased());
        holder.description.setText(shoes.getDescription());
        if (shoes.getShoesNew() ==0){
            holder.icon_newShoes.setVisibility(View.INVISIBLE);
        }
        holder.setItemLongClickListener((view, position1, isLongClick) -> {
            if(!isLongClick){
                //click
                Intent intent=new Intent(context, ProductDetailActivity.class);
                intent.putExtra("chitiet", shoes);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                //checkconnect.ShowToast_Short(context,array.get(getAdapterPosition()).getShoeName());
                context.startActivity(intent);
            }else {
                EventBus.getDefault().postSticky(new SuaXoaEvent(shoes));
            }
        });
    }
    public class LoadingViewHolder extends RecyclerView.ViewHolder{
        ProgressBar progressBar;
        public LoadingViewHolder(@NonNull View itemView){
            super(itemView);
            progressBar = itemView.findViewById(R.id.progresbar);
        }
    }
    @Override
    public int getItemCount() {
        return array.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener, View.OnLongClickListener {
        TextView name, price,saleprice,purchased,description,percent;
        ImageView images,icon_newShoes;
        android.widget.LinearLayout linearLayoutIconSale;
        private com.example.shopsneaker.interFace.ItemLongClickListener itemLongClickListener;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.textviewName);
            price = itemView.findViewById(R.id.textviewPrice);
            saleprice=itemView.findViewById(R.id.textviewSalePrice);
            images = itemView.findViewById(R.id.imageShoes);
            purchased=itemView.findViewById(R.id.textviewPurchased);
            description= itemView.findViewById(R.id.textviewDescription);
            icon_newShoes = itemView.findViewById(R.id.img_iconNewShoes);
            linearLayoutIconSale= itemView.findViewById(com.example.shopsneaker.R.id.linearLayoutIconSale);
            //icon_SaleShoes=itemView.findViewById(R.id.img_iconSaleShoes);
            percent = itemView.findViewById(com.example.shopsneaker.R.id.textviewpercent);
            String s = "admin";
            User user = Paper.book().read("user");
            itemView.setOnClickListener(v -> {
                if(s.equals(user.getUsername())||Utils.user_current.getRolesid()==2){

                }
                else{
                    Intent intent=new Intent(context, ProductDetailActivity.class);
                    intent.putExtra("chitiet", array.get(getAdapterPosition()));
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    checkconnect.ShowToast_Short(context,array.get(getAdapterPosition()).getShoeName());
                    context.startActivity(intent);
                }
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
        }

        @Override
        public boolean onLongClick(View v) {
            itemLongClickListener.onClick(v,getAdapterPosition(),true);
            return false;
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
                    List<Shoes> list = new java.util.ArrayList<>();
                    for (Shoes shoes: arrayOld){
                        if (shoes.getShoeName().toLowerCase().contains(strSearch.toLowerCase())){
                            list.add(shoes);
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
                array =(List<Shoes>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }
}
