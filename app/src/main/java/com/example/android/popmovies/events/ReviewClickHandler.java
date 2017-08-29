package com.example.android.popmovies.events;

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


    public ReviewClickHandler(ArrayList<Review> reviews, TextView authorTextView, TextView contentTextView, Button nextButton, Button prevButton) {
        this.reviews = reviews;
        this.authorTextView = authorTextView;
        this.contentTextView = contentTextView;
        this.nextButton = nextButton;
        this.prevButton = prevButton;

    }



    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.button_next) {
            if (index + 1 >= reviews.size()) {
                nextButton.setText("");
                return;
            }
            index++;
            nextButton.setText("Next Review");
            Review review = reviews.get(index);
            authorTextView.setText(review.getAuthor());
            contentTextView.setText(review.getContent());

        } else if (v.getId() == R.id.button_previous) {
            if (index - 1 < 0) {
                prevButton.setText("");
                return;
            }
            index--;
            prevButton.setText("Previous Review");
            Review review = reviews.get(index);
            authorTextView.setText(review.getAuthor());
            contentTextView.setText(review.getContent());
        }
    }
}
