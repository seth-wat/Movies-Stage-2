package com.example.android.popmovies;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.example.android.popmovies.data.Review;

import java.util.ArrayList;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ViewHolder> {
    private LayoutInflater mInflater;
    private ArrayList<Review> reviews;
    private Context context;

    public ReviewAdapter(Context context, ArrayList<Review> reviews) {
        mInflater = LayoutInflater.from(context);
        this.context = context;
        this.reviews = reviews;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder() {

        }

    }
}