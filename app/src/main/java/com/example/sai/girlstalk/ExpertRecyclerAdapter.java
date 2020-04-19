package com.example.sai.girlstalk;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class ExpertRecyclerAdapter extends RecyclerView.Adapter<ExpertRecyclerAdapter.ViewHolder> {

    ArrayList<StoryModel> list;
    Context mContext;

    public ExpertRecyclerAdapter(ArrayList<StoryModel> list, Context context) {
        this.list = list;
        mContext = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        View view = layoutInflater.inflate(R.layout.experts_row,viewGroup,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

        StoryModel current = list.get(i);
        Glide.with(mContext).load(current.getThumbnailUrl()).into(viewHolder.expertImage);
        viewHolder.expertName.setText(current.getTitle());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView expertImage;
        TextView expertName;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            expertImage= itemView.findViewById(R.id.expertImage);
            expertName = itemView.findViewById(R.id.expertName);
        }
    }
}
