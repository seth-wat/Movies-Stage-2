package com.example.android.popmovies.data;

import org.parceler.Parcel;
import org.parceler.ParcelConstructor;

/**
 * Hold video details for use in the Movie class
 */
@Parcel
public class Video {

    private String key;
    private String name;
    private String site;

    @ParcelConstructor
    public Video(String key, String name) {
        this.key = key;
        this.name = name;
    }

    public String getKey() {
        return key;
    }

    public String getName() {
        return name;
    }

}
