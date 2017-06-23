package com.example.android.booklisting;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Book>> {

    public static final String LOG_TAG = MainActivity.class.getName();

    private static final int BOOK_LOADER_ID = 1;

    private String booksRequestUrl;

    private BookAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView bookListView = (ListView) findViewById(R.id.list);

        bookListView.setEmptyView(findViewById(R.id.empty_view));
        ((TextView)findViewById(R.id.empty_view)).setText(R.string.no_books);

        adapter = new BookAdapter(this, new ArrayList<Book>());

        bookListView.setAdapter(adapter);

        bookListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Book curentBook = adapter.getItem(position);

                Uri bookUri = Uri.parse(curentBook.getUrl());

                Intent websiteIntent = new Intent(Intent.ACTION_VIEW, bookUri);

                startActivity(websiteIntent);
            }
        });

        LoaderManager loaderManager = getLoaderManager();
        if(loaderManager.getLoader(BOOK_LOADER_ID) != null) {

            ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
            if (networkInfo != null && networkInfo.isConnected()) {
                loaderManager = getLoaderManager();
                loaderManager.initLoader(BOOK_LOADER_ID, null, MainActivity.this);

                Log.i(LOG_TAG, "Loader Initialized");
            } else {
                ((TextView)findViewById(R.id.empty_view)).setText(R.string.no_internet_connection);
            }
        }

        ImageButton searchButton = (ImageButton) findViewById(R.id.search_button);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StringBuilder builder = new StringBuilder("https://www.googleapis.com/books/v1/volumes?q=");
                String searchQuery = ((EditText) findViewById(R.id.search_field)).getText().toString();
                if (!searchQuery.isEmpty()) {
                    String[] searchTerms = searchQuery.split(" ");
                    builder.append(searchTerms[0]);
                    for (int i = 1; i < searchTerms.length; i++) {
                        builder.append("+" + searchTerms[i]);
                    }
                    builder.append("&maxResults=10");
                    booksRequestUrl = builder.toString();

                    Log.i(LOG_TAG, booksRequestUrl);

                    ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                    NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

                    if (networkInfo != null && networkInfo.isConnected()){
                        LoaderManager loaderManager = getLoaderManager();
                        loaderManager.restartLoader(BOOK_LOADER_ID, null, MainActivity.this);

                        Log.i(LOG_TAG, "Loader Initialized");
                    } else {
                        ((TextView)findViewById(R.id.empty_view)).setText(R.string.no_internet_connection);
                    }
                } else {
                    ((TextView)findViewById(R.id.empty_view)).setText(R.string.no_books);
                    Log.i(LOG_TAG, "srting= null");
                }
            }
        });
    }

    @Override
    public Loader<List<Book>> onCreateLoader(int id, Bundle args) {
        Log.i(LOG_TAG, "Loader Created");
        return new BookLoader(this, booksRequestUrl);
    }

    @Override
    public void onLoadFinished(Loader<List<Book>> loader, List<Book> data) {
        Log.i(LOG_TAG, "Loader Finished");

        adapter.clear();

        ((TextView)findViewById(R.id.empty_view)).setText(R.string.no_books);

        if(data != null && !data.isEmpty()) {
            adapter.addAll(data);
        }
    }

    @Override
    public void onLoaderReset(Loader<List<Book>> loader) {
        adapter.clear();
    }
}