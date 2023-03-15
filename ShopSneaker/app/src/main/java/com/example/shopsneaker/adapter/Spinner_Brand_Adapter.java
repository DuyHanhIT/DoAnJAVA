package com.example.shopsneaker.adapter;

public class Spinner_Brand_Adapter extends android.widget.ArrayAdapter<com.example.shopsneaker.model.Brand>{


    public Spinner_Brand_Adapter(@androidx.annotation.NonNull android.content.Context context, int resource, @androidx.annotation.NonNull java.util.List<com.example.shopsneaker.model.Brand> objects) {
        super(context, resource, objects);
    }

    @androidx.annotation.NonNull
    @Override
    public android.view.View getView(int position, @androidx.annotation.Nullable android.view.View convertView, @androidx.annotation.NonNull android.view.ViewGroup parent) {
        convertView= android.view.LayoutInflater.from(parent.getContext()).inflate(com.example.shopsneaker.R.layout.item_spinner_brand_selected,parent,false);
        android.widget.TextView txtSelected= convertView.findViewById(com.example.shopsneaker.R.id.tv_faculty_Spinner);
        com.example.shopsneaker.model.Brand b = this.getItem(position);
        if (b!=null){
            txtSelected.setText(String.valueOf(b.getBrandid())+" - "+ b.getBrandname());
        }

        return convertView;
    }

    @Override
    public android.view.View getDropDownView(int position, @androidx.annotation.Nullable android.view.View convertView, @androidx.annotation.NonNull android.view.ViewGroup parent) {
        convertView= android.view.LayoutInflater.from(parent.getContext()).inflate(com.example.shopsneaker.R.layout.item_spinner_brand,parent,false);
        android.widget.TextView txtTenBrand= convertView.findViewById(com.example.shopsneaker.R.id.tv_faculty_Sp);
        com.example.shopsneaker.model.Brand b = this.getItem(position);
        if (b!=null){
            txtTenBrand.setText(String.valueOf(b.getBrandid())+" - "+ b.getBrandname());
        }

        return convertView;
    }
}
