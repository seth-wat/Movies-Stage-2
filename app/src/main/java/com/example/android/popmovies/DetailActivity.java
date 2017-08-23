package com.example.android.popmovies;

import android.content.Intent;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.popmovies.data.Movie;
import com.example.android.popmovies.loaders.DetailLoader;

import org.parceler.Parcels;

public class DetailActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Movie> {

    Movie myMovie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        TextView mTitleTextView = (TextView) findViewById(R.id.title_text_view);
        ImageView mImageView = (ImageView) findViewById(R.id.detail_image_view);
        TextView mReleaseDateTextView = (TextView) findViewById(R.id.release_date_text_view);
        TextView mUserRatingTextView = (TextView) findViewById(R.id.user_rating_text_view);
        TextView mSynopsisTextView = (TextView) findViewById(R.id.synopsis_text_view);

        Intent mIntent = getIntent();
        myMovie = Parcels.unwrap(mIntent.getParcelableExtra("movieParcel"));
        if (myMovie != null) {
            Toast.makeText(this, "The movie was passed and unwrapped successfully!", Toast.LENGTH_LONG).show();
        }

        getSupportLoaderManager().initLoader(123, null, this);



    }


    @Override
    public Loader<Movie> onCreateLoader(int id, Bundle args) {
        return new DetailLoader(this, myMovie);
    }

    @Override
    public void onLoadFinished(Loader<Movie> loader, Movie data) {
        if (data != null) {
            if (!data.getReviews().isEmpty()) {
                String author = data.getReviews().get(0).getAuthor();
                Toast.makeText(this, author, Toast.LENGTH_LONG).show();
            }
            if (!data.getVideos().isEmpty()) {

            }
        }
    }

    @Override
    public void onLoaderReset(Loader<Movie> loader) {

    }
}
