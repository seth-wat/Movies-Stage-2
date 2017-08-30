package com.example.android.popmovies.events;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.view.View;

import com.example.android.popmovies.data.Video;
import com.example.android.popmovies.databinding.ActivityDetailBinding;

import java.net.URL;

/**
 *
 */

public class TrailerClickHandler implements View.OnClickListener {
    private String stringUrl;
    private Activity launchingActivity;
    public TrailerClickHandler(String stringUrl, Activity launchingActivity) {
        super();
        this.stringUrl = stringUrl;
        this.launchingActivity = launchingActivity;

    }
    @Override
    public void onClick(View v) {
        Intent launchVideoIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(stringUrl));
        launchingActivity.startActivity(launchVideoIntent);
    }
}
