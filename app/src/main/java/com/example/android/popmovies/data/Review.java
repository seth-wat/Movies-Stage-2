package com.example.android.popmovies.data;

import org.parceler.Parcel;
import org.parceler.ParcelConstructor;

/**
 * Holds review details for use in the Movie class.
 */
@Parcel
public class Review {
    private String author;
    private String content;

    @ParcelConstructor
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
