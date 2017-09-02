package com.example.android.popmovies;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.popmovies.data.Video;
import com.example.android.popmovies.events.TrailerClickHandler;
import com.example.android.popmovies.utilities.NetworkUtils;


import java.util.ArrayList;

/**
 *
 */

public class TrailerAdapter extends RecyclerView.Adapter<TrailerAdapter.ViewHolder> {
    private ArrayList<Video> videos;
    private LayoutInflater layoutInflater;
    private Activity activity;

    public TrailerAdapter(Context context, ArrayList<Video> videos, Activity activity) {
        this.videos = videos;
        this.layoutInflater = LayoutInflater.from(context);
        this.activity = activity;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.layout_trailer, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.name.setText(videos.get(position).getName());
        holder.itemView.setOnClickListener(new TrailerClickHandler(NetworkUtils.YOUTUBE_BASE_URL + videos.get(position).getKey(), activity));
    }

    @Override
    public int getItemCount() {
        return videos.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView name;

        public ViewHolder(View v) {
            super(v);
            name = (TextView) v.findViewById(R.id.trailer_name_text_view);
        }

    }
}
