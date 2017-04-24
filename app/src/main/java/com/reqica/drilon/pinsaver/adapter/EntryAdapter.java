package com.reqica.drilon.pinsaver.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.reqica.drilon.pinsaver.model.EntryModel;
import com.reqica.drilon.pinsaver.R;

import java.util.ArrayList;

public class EntryAdapter extends ArrayAdapter<EntryModel> {
    private static final String KEY_NULL = "null";
    private static final String KEY_EMPTY = "";

    public EntryAdapter(@NonNull Context context, @NonNull ArrayList<EntryModel> favorites) {
        super(context, 0, favorites);
    }

    @NonNull
    @Override
    public View getView(final int position, View convertView, @NonNull ViewGroup parent) {
        // Get the data item for this position
        final EntryModel entryModel = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_entry, parent, false);
        }

        // Lookup view for data population
        TextView favoriteIdTV = (TextView) convertView.findViewById(R.id.card_item_text_view);

        String cardName = entryModel != null ? entryModel.getCard() : KEY_EMPTY;

        if (cardName.startsWith(KEY_NULL)) {
            cardName = cardName.replace(KEY_NULL, KEY_EMPTY);
        }

        favoriteIdTV.setText(cardName);

        // Return the completed view to render on screen
        return convertView;
    }
}

