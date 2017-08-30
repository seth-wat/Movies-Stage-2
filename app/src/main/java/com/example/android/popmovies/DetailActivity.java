package com.example.android.popmovies;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.popmovies.data.Movie;
import com.example.android.popmovies.data.Review;
import com.example.android.popmovies.data.Video;
import com.example.android.popmovies.databinding.ActivityDetailBinding;
import com.example.android.popmovies.events.FavoriteClickHandler;
import com.example.android.popmovies.events.ReviewClickHandler;
import com.example.android.popmovies.loaders.DetailLoader;
import com.squareup.picasso.Picasso;

import org.parceler.Parcels;

import java.util.ArrayList;

import static android.view.MotionEvent.ACTION_SCROLL;

public class DetailActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Movie> {

    Movie myMovie;
    ActivityDetailBinding mBinder;
    LinearLayoutManager reviewLayoutManager;
    ReviewClickHandler mReviewClickHandler;
    int reviewIndex;
//    ReviewAdapter reviewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        mBinder = DataBindingUtil.setContentView(this, R.layout.activity_detail);
        reviewLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

        if (savedInstanceState != null) {
             reviewIndex = savedInstanceState.getInt("index");
        }

        Intent mIntent = getIntent();
        myMovie = Parcels.unwrap(mIntent.getParcelableExtra("movieParcel"));
        if (myMovie != null) {
            Toast.makeText(this, "The movie was passed and unwrapped successfully!", Toast.LENGTH_LONG).show();
        }
        mBinder.titleTextView.setText(myMovie.getTitle());
        mBinder.releaseDateTextView.setText(myMovie.getReleaseDate());
        mBinder.ratingTextView.setText(myMovie.getUserRating() + " / 10");
        mBinder.durationTextView.setText("120 min");

        mBinder.synopsisTextView.setText(myMovie.getPlotSynopsis());
        Picasso.with(this).load(myMovie.getDetailImagePath()).into(mBinder.dropImageView);
        getSupportLoaderManager().initLoader(123, null, this);


    }




    @Override
    public Loader<Movie> onCreateLoader(int id, Bundle args) {
        return new DetailLoader(this, myMovie);
    }

    @Override
    public void onLoadFinished(Loader<Movie> loader, Movie data) {
        if (data != null) {
            final ArrayList<Review> reviews = !myMovie.getReviews().isEmpty() ? myMovie.getReviews() : null;
            final ArrayList<Video> videos = !myMovie.getVideos().isEmpty() ? myMovie.getVideos() : null;
            if (! (videos == null)) {
                mBinder.trailerRecyclerView.setLayoutManager(reviewLayoutManager);
                mBinder.trailerRecyclerView.setAdapter(new TrailerAdapter(this, videos, this));

            }

            if (! (reviews == null)) {
                mBinder.reviewInclude.synopsisFrame.setText(reviews.get(reviewIndex).getContent());
                mBinder.reviewInclude.userNameTextView.setText(reviews.get(reviewIndex).getAuthor());
                mReviewClickHandler = new ReviewClickHandler(reviews, reviewIndex, mBinder.reviewInclude.userNameTextView, mBinder.reviewInclude.synopsisFrame,
                        mBinder.reviewInclude.buttonNext, mBinder.reviewInclude.buttonPrevious);

                mBinder.reviewInclude.buttonNext.setOnClickListener(mReviewClickHandler);
                mBinder.reviewInclude.buttonPrevious.setOnClickListener(mReviewClickHandler);
            }
            mBinder.fab.setOnClickListener(new FavoriteClickHandler(data, mBinder.dropImageView, data.getFavorite()));
        }
    }

    @Override
    public void onLoaderReset(Loader<Movie> loader) {

    }

    @Override
    protected void onPause() {
        super.onPause();

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt("index", mReviewClickHandler.getIndex());
        super.onSaveInstanceState(outState);
    }
}
