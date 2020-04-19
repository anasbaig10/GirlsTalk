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

public class StoryRecyclerAdapter extends RecyclerView.Adapter<StoryRecyclerAdapter.ViewHolder> {

    ArrayList<StoryModel> list;
    Context mContext;

    public StoryRecyclerAdapter(ArrayList<StoryModel> list, Context context) {
        this.list = list;
        mContext = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View view = layoutInflater.inflate(R.layout.stories_row,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, int i) {
        StoryModel current = list.get(i);
        Glide
                .with(mContext)
                .load(current.getThumbnailUrl())
                .centerCrop()
                .into(viewHolder.thumbnail);

        viewHolder.title.setText(current.getTitle());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView thumbnail;
        TextView title;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            thumbnail = itemView.findViewById(R.id.storyThumbnail);
            title = itemView.findViewById(R.id.storyTitle);
        }

    }
}
