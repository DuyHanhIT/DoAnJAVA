package com.example.shopsneaker.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.shopsneaker.R;
import com.example.shopsneaker.activity.ChatActivity;
import com.example.shopsneaker.activity.FullImageView;
import com.example.shopsneaker.model.Message;
import com.example.shopsneaker.utils.Utils;

import java.text.SimpleDateFormat;
import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.MyViewHolder> {
    Context context;
    List<Message> array;
    public static final int left = 0;
    public static final int right = 1;

    public ChatAdapter(Context context, List<Message> array) {
        this.context = context;
        this.array = array;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View item;
        if(i==right){
            item = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_container_sent_mess, parent, false);
        }else {
            item = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_container_receiver, parent, false);
        }
        return new MyViewHolder(item);

    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int i) {
        Message message = array.get(i);
        holder.txtMess.setText(message.getContent());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");
        holder.txtDateTime.setText(simpleDateFormat.format(message.getTimestamp()));

    }

    @Override
    public int getItemCount() {
        return array.size();
    }

    @Override
    public int getItemViewType(int position) {
        if(array.get(position).getSender()==Utils.user_current.getAccountid()){
            return right;
        }else{
            return left;
        }
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView txtMess,txtDateTime;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            txtMess = itemView.findViewById(R.id.txtMess);
            txtDateTime = itemView.findViewById(R.id.txtDateTime);
        }
    }
}
