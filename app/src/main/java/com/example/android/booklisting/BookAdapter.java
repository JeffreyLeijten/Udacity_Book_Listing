package com.example.android.booklisting;

import android.app.Activity;
import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;


/**
 * Created by Jeffrey on 22-6-2017.
 */

public class BookAdapter extends ArrayAdapter<Book>{
    public BookAdapter(Activity context, ArrayList<Book> books) {
        super(context, 0, books);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
        }

        Book currentBook = getItem(position);

        TextView titleTextView = (TextView) convertView.findViewById(R.id.title);
        titleTextView.setText(currentBook.getTitle());

        TextView authorsTextView = (TextView) convertView.findViewById(R.id.authors);
        if (currentBook.getAuthors() != null){
            authorsTextView.setVisibility(View.VISIBLE);
            String authorString = authorStringBuilder(currentBook.getAuthors());
            authorsTextView.setText(authorString);
        } else {
            authorsTextView.setVisibility(View.GONE);
        }

        TextView publishedYearTextView = (TextView) convertView.findViewById(R.id.published_year);
        if (currentBook.getPublishedYear() != -1){
            publishedYearTextView.setVisibility(View.VISIBLE);
            publishedYearTextView.setText(Integer.toString(currentBook.getPublishedYear()));
        } else {
            publishedYearTextView.setVisibility(View.GONE);
        }

        TextView descriptionTextView = (TextView) convertView.findViewById(R.id.description);
        if (currentBook.getDescription() != null){
            descriptionTextView.setVisibility(View.VISIBLE);
            String descriptionText = decreaseStringLength(currentBook.getDescription(), 5);
            descriptionTextView.setText(descriptionText);
        } else {
            descriptionTextView.setVisibility(View.GONE);
        }

        return convertView;
    }

    private String authorStringBuilder(String[] authors){
        StringBuilder builder = new StringBuilder("");
        builder.append(authors[0]);
        for (int i = 1; i<authors.length; i++){
            builder.append("; " + authors[i]);
        }
        return builder.toString();
    }

    private String decreaseStringLength(String originalString, int numberOfSentences){
        String[] substrings = originalString.split("\\. ");
        StringBuilder builder = new StringBuilder("");
        for (int i = 0; i < numberOfSentences && i < substrings.length; i++){
            builder.append(substrings[i] + ". ");
        }
        return builder.toString();
    }

}
