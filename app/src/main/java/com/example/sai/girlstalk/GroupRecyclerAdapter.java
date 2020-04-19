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

public class GroupRecyclerAdapter extends RecyclerView.Adapter<GroupRecyclerAdapter.ViewHolder> {

    ArrayList<GroupModel> list;
    Context mContext;

    public GroupRecyclerAdapter(ArrayList<GroupModel> list, Context context) {
        this.list = list;
        mContext = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        View view = layoutInflater.inflate(R.layout.group_rows,viewGroup,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
            GroupModel current = list.get(i);
        Glide.with(mContext).load(current.getGroupIcon()).into(viewHolder.gropThumbnail);
        viewHolder.groupTitle.setText(current.getGroupTitle());
        viewHolder.groupMembers.setText("Members: "+current.getGroupMembers());
        viewHolder.groupLocation.setText("Location: "+current.groupLocation);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView gropThumbnail;
        TextView groupTitle,groupMembers, groupLocation;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            gropThumbnail = itemView.findViewById(R.id.groupIcon);
            groupMembers  = itemView.findViewById(R.id.groupMembers);
            groupLocation = itemView.findViewById(R.id.groupLocation);
            groupTitle = itemView.findViewById(R.id.groupTitle);

        }
    }
}
