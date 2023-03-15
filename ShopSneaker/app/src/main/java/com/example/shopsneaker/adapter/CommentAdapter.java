package com.example.shopsneaker.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shopsneaker.R;
import com.example.shopsneaker.model.Comment;

import java.util.List;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.MyViewHolder> {
    Context context;
    List<Comment> array;
    int x;

    public CommentAdapter(Context context, List<Comment> array) {
        this.context = context;
        this.array = array;
    }




    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.itemcomment,parent,false);
        return new MyViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Comment cmt = array.get(position);
        holder.txtcomment.setText(cmt.getContent());
        holder.txtuser.setText(cmt.getName());

    }
    @Override
    public int getItemCount() {
        return array.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView txtuser, txtcomment;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            txtuser = itemView.findViewById(R.id.textusercmt);
            txtcomment = itemView.findViewById(R.id.textcomment);
        }
    }
}
