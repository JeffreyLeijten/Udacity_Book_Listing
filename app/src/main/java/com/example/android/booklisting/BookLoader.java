package com.example.android.booklisting;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.util.Log;

import java.util.List;

import static com.example.android.booklisting.MainActivity.LOG_TAG;

/**
 * Created by Jeffrey on 21-6-2017.
 */

public class BookLoader extends AsyncTaskLoader<List<Book>> {

    private String url;

    public BookLoader(Context context, String pUrl) {
        super(context);
        url = pUrl;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public List<Book> loadInBackground() {
        Log.i(LOG_TAG, "Load in Background");
        if (url == null) {
            Log.i(LOG_TAG, "URL is null");
            return null;
        }

        List<Book> books = QueryUtils.fetchBookData(url);
        return books;
    }
}
