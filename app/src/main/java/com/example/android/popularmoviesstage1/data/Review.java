package com.example.android.popularmoviesstage1.data;

import org.parceler.Parcel;

/**
 * Holds review details for use in the Movie class.
 */
@Parcel
public class Review {
    private String author;
    private String content;

    public Review(String author, String content) {
        this.author = author;
        this.content = content;
    }

    public String getAuthor() {
        return author;
    }

    public String getContent() {
        return content;
    }


}
