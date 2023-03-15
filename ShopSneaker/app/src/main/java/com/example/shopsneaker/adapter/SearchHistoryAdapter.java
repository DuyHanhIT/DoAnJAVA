package com.example.shopsneaker.adapter;

public class SearchHistoryAdapter extends androidx.recyclerview.widget.RecyclerView.Adapter<com.example.shopsneaker.adapter.SearchHistoryAdapter.MyViewHolder> {
    android.content.Context context;
    java.util.List<com.example.shopsneaker.model.Shoes> array;

    public SearchHistoryAdapter(android.content.Context context, java.util.List<com.example.shopsneaker.model.Shoes> array) {
        this.context = context;
        this.array = array;
    }

    @androidx.annotation.NonNull
    @Override
    public com.example.shopsneaker.adapter.SearchHistoryAdapter.MyViewHolder onCreateViewHolder(@androidx.annotation.NonNull android.view.ViewGroup parent, int viewType) {
        android.view.View view = android.view.LayoutInflater.from(parent.getContext()).inflate(com.example.shopsneaker.R.layout.item_flashsale,parent,false);
        return  new com.example.shopsneaker.adapter.SearchHistoryAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@androidx.annotation.NonNull com.example.shopsneaker.adapter.SearchHistoryAdapter.MyViewHolder holder, int position) {
        com.example.shopsneaker.adapter.SearchHistoryAdapter.MyViewHolder myViewHolder = holder;
        com.example.shopsneaker.model.Shoes shoes = array.get(position);
        java.util.Date date = new java.util.Date();
        if (shoes.getSaleprice()==0|| shoes.getStartday().after(date) || shoes.getEndday().before(date)){
            holder.price.setText(shoes.getPrice()+" USD");
            holder.saleprice.setVisibility(android.view.View.INVISIBLE);
            holder.iconFlashSale.setVisibility(android.view.View.INVISIBLE);


        }else {
            holder.price.setText(shoes.getSaleprice()+" USD");
            holder.saleprice.setVisibility(android.view.View.INVISIBLE);
        }
        if (shoes.getImages().contains("http")){
            com.bumptech.glide.Glide.with(context).load(shoes.getImages()).into(holder.images);
        }else {
            String hinh = com.example.shopsneaker.utils.Utils.BASE_URL+"images/" + shoes.getImages();
            com.bumptech.glide.Glide.with(context).load(hinh).into(holder.images);
        }


    }

    @Override
    public int getItemCount() {
        return array.size();
    }

    public class MyViewHolder extends androidx.recyclerview.widget.RecyclerView.ViewHolder {
        android.widget.TextView saleprice,price;
        android.widget.ImageView images,iconFlashSale;
        public MyViewHolder(@androidx.annotation.NonNull android.view.View itemView) {
            super(itemView);
            images = itemView.findViewById(com.example.shopsneaker.R.id.anhSanPham);
            saleprice = itemView.findViewById(com.example.shopsneaker.R.id.giaSanPhamKM);
            price=itemView.findViewById(com.example.shopsneaker.R.id.giaSanPham);
            iconFlashSale=itemView.findViewById(com.example.shopsneaker.R.id.iconFlashsale);
            itemView.setOnClickListener(v -> {
                android.content.Intent intent=new android.content.Intent(context, com.example.shopsneaker.activity.ProductDetailActivity.class);
                intent.putExtra("chitiet", array.get(getAdapterPosition()));
                intent.setFlags(android.content.Intent.FLAG_ACTIVITY_NEW_TASK);
                com.example.shopsneaker.utils.checkconnect.ShowToast_Short(context,array.get(getAdapterPosition()).getShoeName());
                context.startActivity(intent);
            });
        }
    }
}
