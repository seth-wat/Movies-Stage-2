package com.example.android.popmovies;

import android.app.Activity;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.popmovies.data.Review;
import com.example.android.popmovies.databinding.ActivityDetailBinding;
import com.example.android.popmovies.databinding.LayoutReviewBinding;

import java.util.ArrayList;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ViewHolder> {
    private LayoutInflater mInflater;
    private ArrayList<Review> reviews;
    private Context context;
    private Activity activity;

    public ReviewAdapter(Context context, Activity activity, ArrayList<Review> reviews) {
        mInflater = LayoutInflater.from(context);
        this.context = context;
        this.activity = activity;
        this.reviews = reviews;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.layout_review, parent, false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Review review = reviews.get(position);
        holder.author.setText(review.getAuthor());
        holder.content.setText(review.getContent());
    }

    @Override
    public int getItemCount() {
        return reviews.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView author;
        TextView content;
        public ViewHolder(View view) {
            super(view);
            author = (TextView) view.findViewById(R.id.user_name_text_view);
            content = (TextView) view.findViewById(R.id.synopsis_frame);

        }

    }
}