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

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MyViewHolder> {
    Context context;
    List<Message> array;

    public MessageAdapter(Context context, List<Message> array) {
        this.context = context;
        this.array = array;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View item = LayoutInflater.from(parent.getContext()).inflate(R.layout.contact_placeholder, parent, false);
        return new MessageAdapter.MyViewHolder(item);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int i) {
        Message message = array.get(i);
        if(message.getSender()== Utils.user_current.getAccountid()) {
            if(message.getContent().length()>20){
                holder.textviewMess.setText("Bạn: " + message.getContent().substring(0,20)+"...");
            }else {
                holder.textviewMess.setText("Bạn: " + message.getContent());
            }

        }
        else{
            if(message.getContent().length()>20){
                holder.textviewMess.setText(message.getName()+": " + message.getContent().substring(0,20)+"...");
            }else {
                holder.textviewMess.setText(message.getName()+": " + message.getContent());
            }
        }

        holder.textviewUserName.setText(message.getName());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");
        holder.textviewDate.setText(simpleDateFormat.format(message.getTimestamp()));

    }

    @Override
    public int getItemCount() {
        return array.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView textviewUserName, textviewMess, textviewDate;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            textviewUserName = itemView.findViewById(R.id.textviewUserName);
            textviewMess = itemView.findViewById(R.id.textviewMess);
            textviewDate = itemView.findViewById(R.id.textviewDate);
            itemView.setOnClickListener(v -> {
                Intent intent = new Intent(context, ChatActivity.class);
                intent.putExtra("user",array.get(getAdapterPosition()));
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            });
        }
    }
}
