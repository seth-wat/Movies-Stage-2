package com.example.android.popmovies.utilities;

import android.view.View;

import com.example.android.popmovies.databinding.ActivityDetailBinding;

/**
 * For use in DetailActivity
 */

public class ErrorDisplayUtils {

    private ErrorDisplayUtils() {

    }

    public static void showReviewError(ActivityDetailBinding mBinder) {
        mBinder.reviewInclude.reviewErrorTextView.setVisibility(View.VISIBLE);
        mBinder.reviewInclude.userNameTextView.setVisibility(View.INVISIBLE);
        mBinder.reviewInclude.buttonNext.setVisibility(View.INVISIBLE);
        mBinder.reviewInclude.buttonPrevious.setVisibility(View.INVISIBLE);
        mBinder.reviewInclude.synopsisFrame.setVisibility(View.INVISIBLE);

    }

    public static void hideReviewError(ActivityDetailBinding mBinder) {
        mBinder.reviewInclude.reviewErrorTextView.setVisibility(View.INVISIBLE);
        mBinder.reviewInclude.userNameTextView.setVisibility(View.VISIBLE);
        mBinder.reviewInclude.buttonNext.setVisibility(View.VISIBLE);
        mBinder.reviewInclude.buttonPrevious.setVisibility(View.VISIBLE);
        mBinder.reviewInclude.synopsisFrame.setVisibility(View.VISIBLE);

    }

    public static void showTrailerError(ActivityDetailBinding mBinder) {
        mBinder.trailerRecyclerView.setVisibility(View.INVISIBLE);
        mBinder.trailerErrorTextView.setVisibility(View.VISIBLE);
        mBinder.reviewTitleTextView.setPadding(0, 40, 0, 0);

    }

    public static void hideTrailerError(ActivityDetailBinding mBinder) {
        mBinder.trailerRecyclerView.setVisibility(View.VISIBLE);
        mBinder.reviewTitleTextView.setPadding(0, 0, 0, 0);
        mBinder.trailerErrorTextView.setVisibility(View.INVISIBLE);

    }
}
