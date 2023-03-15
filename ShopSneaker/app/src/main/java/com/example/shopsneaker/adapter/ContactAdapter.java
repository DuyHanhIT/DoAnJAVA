package com.example.shopsneaker.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.shopsneaker.R;
import com.example.shopsneaker.model.Contact;

import java.util.List;

public class ContactAdapter extends BaseAdapter {
    private Context context;
    private int layout;
    private List<Contact> arraylist;

    public ContactAdapter(Context context, int layout, List<Contact> arraylist) {
        this.context = context;
        this.layout = layout;
        this.arraylist = arraylist;
    }

    @Override
    public int getCount() {
        return arraylist.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(layout, null);
        Contact contact = arraylist.get(position);

        TextView textView = convertView.findViewById(R.id.name);
        TextView textView1 = convertView.findViewById(R.id.Mota);
        ImageView imageView = convertView.findViewById(R.id.imgAnh);
        textView.setText(contact.getNamecontact());
        textView1.setText(contact.getDesCrip());
        imageView.setImageResource(contact.getImages());

        return convertView;
    }
}
