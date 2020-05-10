package com.example.instagram.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.instagram.R;

import java.util.List;

public class TagAdapter extends RecyclerView.Adapter<TagAdapter.ViewHolder>{

    private Context myContext;
    private List<String> myTags;
    private List<String> myTagsCount;

    public TagAdapter(Context myContext, List<String> myTags, List<String> myTagsCount) {
        this.myContext = myContext;
        this.myTags = myTags;
        this.myTagsCount = myTagsCount;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(myContext).inflate(R.layout.tag_item,parent,false);
        return new TagAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tag.setText("#"+myTags.get(position));
        holder.noOfPosts.setText(myTagsCount.get(position)+" posts");
    }

    @Override
    public int getItemCount() {
        return myTags.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView tag;
        public TextView noOfPosts;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tag=itemView.findViewById(R.id.hash_tag);
            noOfPosts=itemView.findViewById(R.id.no_of_posts);
        }
    }
    public void filter(List<String> filterTags,List<String> filterTagsCount){
        this.myTags = filterTags;
        this.myTagsCount = filterTagsCount;
        notifyDataSetChanged();
    }
}
