package com.example.android.popularmoviesstage1.data;

import org.parceler.Parcel;

/**
 * Hold video details for use in the Movie class
 */
@Parcel
public class Video {

    private String key;
    private String name;
    private String site;

    public Video(String key, String name, String site) {
        this.key = key;
        this.name = name;
        this.site = site;
    }

    public String getKey() {
        return key;
    }

    public String getName() {
        return name;
    }

    public String getSite() {
        return site;
    }
}
