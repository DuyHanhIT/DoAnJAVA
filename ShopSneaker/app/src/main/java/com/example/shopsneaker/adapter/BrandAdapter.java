package com.example.shopsneaker.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.shopsneaker.R;
import com.example.shopsneaker.model.Brand;

import java.util.List;

public class BrandAdapter extends BaseAdapter {
    List<Brand> array;
    Context context;

    public BrandAdapter(Context context, List<Brand> array) {
        this.array = array;
        this.context = context;
    }

    @Override
    public int getCount() {
        return array.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }
    public class ViewHolder{
        TextView textTenLoaiSP;
        ImageView imgHinhAnhLoaiSP;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null){
            viewHolder = new ViewHolder();
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.item_brand, null);
            viewHolder.textTenLoaiSP = convertView.findViewById(R.id.item_BrandName);
            viewHolder.imgHinhAnhLoaiSP=convertView.findViewById(R.id.item_BrandLogo);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();

        }
        viewHolder.textTenLoaiSP.setText(array.get(position).getBrandname());
        Glide.with(context).load(array.get(position).getLogo()).into(viewHolder.imgHinhAnhLoaiSP);

        return convertView;
    }
}
