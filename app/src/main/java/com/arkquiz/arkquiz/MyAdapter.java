package com.arkquiz.arkquiz;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    private ArrayList<RVItem> rvList;
    private Context context;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView TextView_nickname, TextView_ranking, TextView_score;
        public ImageView ImageView_flag;
        public MyViewHolder(View itemView) {
            super(itemView);
            TextView_nickname=itemView.findViewById(R.id.TextView_rankingItem_username);
            TextView_ranking=itemView.findViewById(R.id.TextView_rankingItem_ranking);
            ImageView_flag=itemView.findViewById(R.id.ImageView_rankingItem_country);
            TextView_score=itemView.findViewById(R.id.TextView_rankingItem_score);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public MyAdapter(ArrayList<RVItem> rvList, Context context) {
        this.rvList=rvList;
        this.context=context;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public MyAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                     int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.ranking_item, parent, false);
        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    //view에 아이템 값들을 할당
    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.TextView_ranking.setText(String.valueOf(rvList.get(position).getRanking()));
        holder.TextView_nickname.setText(rvList.get(position).getNickname());
        holder.TextView_score.setText(String.valueOf(rvList.get(position).getScore()));
        TypedArray typedArray=context.getResources().obtainTypedArray(R.array.flag);
        Drawable drawable=typedArray.getDrawable(rvList.get(position).getCountryCode());
        holder.ImageView_flag.setImageDrawable(drawable);
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return rvList.size();
    }
}
