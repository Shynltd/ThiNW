package com.example.thinw;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.QuickContactBadge;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyHolder> {
    private Context context;
    private List<Example> mList;

    public MyAdapter(Context context, List<Example> mList) {
        this.context = context;
        this.mList = mList;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.rv_list, parent, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        holder.tvComment.setText(mList.get(position).getEmail());
        for (int i=0;i<mList.get(position).getEmail().length();i++){
            String n = String.valueOf(mList.get(position).getEmail().charAt(i));
            if (n.equalsIgnoreCase("n")) {
                holder.tvComment.setTextColor(Color.parseColor("#2C367A"));
            };
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                View alert = LayoutInflater.from(context).inflate(R.layout.dialog,null);
                builder.setView(alert);
                TextView tvPostId = alert.findViewById(R.id.tvPostId);
                TextView tvName= alert.findViewById(R.id.tvName);
                TextView tvId= alert.findViewById(R.id.tvId);
                TextView tvbody= alert.findViewById(R.id.tvBody);
                TextView tvEmail= alert.findViewById(R.id.tvEmail);
                tvPostId.setText(mList.get(position).getPostId()+"");
                tvName.setText(mList.get(position).getName()+"");
                tvId.setText(mList.get(position).getId()+"");
                tvbody.setText(mList.get(position).getBody()+"");
                tvEmail.setText(mList.get(position).getEmail()+"");
                builder.create().show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public static class MyHolder extends RecyclerView.ViewHolder {
        TextView tvComment;
        public MyHolder(@NonNull View itemView) {
            super(itemView);
            tvComment = itemView.findViewById(R.id.tvComment);
        }
    }
}
