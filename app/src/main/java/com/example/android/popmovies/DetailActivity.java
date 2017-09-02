package com.example.android.popmovies;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.popmovies.data.Movie;
import com.example.android.popmovies.data.Review;
import com.example.android.popmovies.data.Video;
import com.example.android.popmovies.databinding.ActivityDetailBinding;
import com.example.android.popmovies.events.FavoriteClickHandler;
import com.example.android.popmovies.events.ReviewClickHandler;
import com.example.android.popmovies.loaders.DetailLoader;
import com.example.android.popmovies.loaders.MovieLoader;
import com.example.android.popmovies.utilities.ErrorDisplayUtils;
import com.squareup.picasso.Picasso;

import org.parceler.Parcels;

import java.util.ArrayList;

public class DetailActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Movie> {

    Movie myMovie;
    ActivityDetailBinding mBinder;
    LinearLayoutManager reviewLayoutManager;
    ReviewClickHandler mReviewClickHandler;
    int reviewIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        mBinder = DataBindingUtil.setContentView(this, R.layout.activity_detail);
        reviewLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);


        Intent mIntent = getIntent();
        myMovie = Parcels.unwrap(mIntent.getParcelableExtra("movieParcel"));
        if (myMovie != null) {
        }
        if (savedInstanceState != null) {
            reviewIndex = savedInstanceState.getInt("index");
        }

        mBinder.titleTextView.setText(myMovie.getTitle());
        mBinder.releaseDateTextView.setText(myMovie.getReleaseDate());
        mBinder.ratingTextView.setText(myMovie.getUserRating() + " / 10");

        if (myMovie.getDetailByteImage() != null) {
            Bitmap image = BitmapFactory.decodeByteArray(myMovie.getDetailByteImage(), 0, myMovie.getDetailByteImage().length);
            mBinder.dropImageView.setImageBitmap(image);
        } else {
            Picasso.with(this).load(myMovie.getBackDropPath()).into(mBinder.dropImageView);
        }
        mBinder.durationTextView.setText("120 min");

        mBinder.synopsisTextView.setText(myMovie.getPlotSynopsis());
        getSupportLoaderManager().initLoader(123, null, this);


    }




    @Override
    public Loader<Movie> onCreateLoader(int id, Bundle args) {
        return new DetailLoader(this, myMovie);
    }

    @Override
    public void onLoadFinished(Loader<Movie> loader, Movie data) {
        if (data != null) {
            final ArrayList<Review> reviews = !data.getReviews().isEmpty() ? data.getReviews() : null;
            final ArrayList<Video> videos = !data.getVideos().isEmpty() ? data.getVideos() : null;

            if (! (videos == null)) {
                ErrorDisplayUtils.hideReviewError(mBinder);
                mBinder.trailerRecyclerView.setLayoutManager(reviewLayoutManager);
                mBinder.trailerRecyclerView.setAdapter(new TrailerAdapter(this, videos, this));
            } else {
                ErrorDisplayUtils.showTrailerError(mBinder);
            }

            if (! (reviews == null)) {
                ErrorDisplayUtils.hideReviewError(mBinder);

                mBinder.reviewInclude.synopsisFrame.setText(reviews.get(reviewIndex).getContent());
                mBinder.reviewInclude.userNameTextView.setText(reviews.get(reviewIndex).getAuthor());
                mReviewClickHandler = new ReviewClickHandler(reviews, reviewIndex, mBinder.reviewInclude.userNameTextView, mBinder.reviewInclude.synopsisFrame,
                        mBinder.reviewInclude.buttonNext, mBinder.reviewInclude.buttonPrevious);
                mBinder.reviewInclude.buttonNext.setOnClickListener(mReviewClickHandler);
                mBinder.reviewInclude.buttonPrevious.setOnClickListener(mReviewClickHandler);
            } else {
                ErrorDisplayUtils.showReviewError(mBinder);
            }

            if (data.getFavorite()) {
                mBinder.fab.setImageResource(R.drawable.ic_favorited_black_24dp);
            } else {
                mBinder.fab.setImageResource(R.drawable.ic_favorite_border_black_24dp);
            }

            mBinder.fab.setOnClickListener(new FavoriteClickHandler(data));
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
        if (mReviewClickHandler != null) {
            outState.putInt("index", mReviewClickHandler.getIndex());
        }

        super.onSaveInstanceState(outState);
    }
}
