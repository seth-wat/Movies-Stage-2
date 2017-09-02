package com.example.android.popmovies.events;

import android.graphics.Color;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.android.popmovies.R;
import com.example.android.popmovies.data.Review;

import java.util.ArrayList;

/**
 *
 */

public class ReviewClickHandler implements View.OnClickListener {

    private int index;
    private ArrayList<Review> reviews;
    private TextView authorTextView;
    private TextView contentTextView;
    private Button nextButton;
    private Button prevButton;


    public ReviewClickHandler(ArrayList<Review> reviews, int index, TextView authorTextView, TextView contentTextView, Button nextButton, Button prevButton) {
        this.reviews = reviews;
        this.index = index;
        this.authorTextView = authorTextView;
        this.contentTextView = contentTextView;
        this.nextButton = nextButton;
        this.prevButton = prevButton;

        if (reviews.size() < 2) {
            nextButton.setEnabled(false);
            prevButton.setEnabled(false);
        }

    }



    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.button_next) {
            if (index + 1 >= reviews.size()) {
                nextButton.setEnabled(false);
                return;
            }
            index++;
            if (!(index - 1 < 0)) {
                prevButton.setEnabled(true);
            }
            Review review = reviews.get(index);
            authorTextView.setText(review.getAuthor());
            contentTextView.setText(review.getContent());

        } else if (v.getId() == R.id.button_previous) {
            if (index - 1 < 0) {
                prevButton.setEnabled(false);
                return;
            }
            index--;
            if (!(index + 1 >= reviews.size())) {
                nextButton.setEnabled(true);
            }
            Review review = reviews.get(index);
            authorTextView.setText(review.getAuthor());
            contentTextView.setText(review.getContent());
        }
    }

    public int getIndex() {
        return index;
    }
}
