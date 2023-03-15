package com.example.shopsneaker.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.shopsneaker.model.SizeTable;

import java.util.List;

public class SizeTableAdapter extends BaseAdapter {
    Context context;
    List<SizeTable> arraySize;

    public SizeTableAdapter(Context context, List<SizeTable> arraySize) {
        this.context = context;
        this.arraySize = arraySize;
    }

    @Override
    public int getCount() {
        return arraySize.size();
    }

    @Override
    public Object getItem(int position) {
        return arraySize.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    public class ViewHolder{

    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        return convertView;
    }
}
