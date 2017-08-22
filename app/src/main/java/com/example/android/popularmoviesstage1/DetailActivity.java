package com.example.android.popularmoviesstage1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

public class DetailActivity extends AppCompatActivity {

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
        mTitleTextView.setText(mIntent.getStringExtra("title"));
        Picasso.with(this).load(mIntent.getStringExtra("detailImage")).into(mImageView);
        mSynopsisTextView.setText(mIntent.getStringExtra("plotSynopsis"));
        String releaseDate = "Release Date: " + mIntent.getStringExtra("releaseDate");
        mReleaseDateTextView.setText(releaseDate);
        String userRating = "Average Rating: " + mIntent.getStringExtra("userRating");
        mUserRatingTextView.setText(userRating);


    }


}
