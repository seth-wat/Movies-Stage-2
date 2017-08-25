package com.example.android.popmovies.loaders;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

/**
 * Created by God on 8/24/2017.
 */

public class DeleteLoader extends AsyncTaskLoader<Integer> {
    private int key;

    public DeleteLoader(Context context, int key) {
        super(context);
        this.key =key;
    }
    @Override
    public Integer loadInBackground() {
        return null;
    }
}
