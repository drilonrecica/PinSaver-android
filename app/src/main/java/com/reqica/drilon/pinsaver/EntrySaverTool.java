package com.reqica.drilon.pinsaver;

import android.content.Context;
import android.util.Log;

import de.adorsys.android.securestoragelibrary.SecurePreferences;

public class EntrySaverTool {
    private static final String KEY_ENTRIES = "entries";
    private static final String KEY_DIVIDER = "~";
    private static final String KEY_EMPTY = "";

    public static boolean checkEntry(String entry, Context context) {
        String savedArticles = SecurePreferences.getStringValue(KEY_ENTRIES, context, null);
        String[] articles = savedArticles.split(KEY_DIVIDER);

        int result = 0;
        for (String article : articles) {
            if (article.equals(entry)) {
                result = 1;
            }
        }
        return result != 0;
    }

    public static String[] getEntries(Context context) {
        String savedArticles = SecurePreferences.getStringValue(KEY_ENTRIES, context, KEY_EMPTY);
        return savedArticles.split(KEY_DIVIDER);
    }

    public static void saveEntry(String entry, Context context) {
        String savedArticles = SecurePreferences.getStringValue(KEY_ENTRIES, context, null);
        savedArticles = savedArticles + entry + KEY_DIVIDER;
        try {
            SecurePreferences.setValue(KEY_ENTRIES, savedArticles, context);
        } catch (Exception e) {
            Log.e("SaveCryptoException", e.getMessage());
        }
    }

    public static void removeEntry(String entry, Context context) {
        String savedArticles = SecurePreferences.getStringValue(KEY_ENTRIES, context, null);
        String[] articles = savedArticles.split(KEY_DIVIDER);

        for (String article : articles) {
            if (entry.equals(article)) {
                String remainingArticles = savedArticles.replace(article + KEY_DIVIDER, KEY_EMPTY);
                try {
                    SecurePreferences.setValue(KEY_ENTRIES, remainingArticles, context);
                } catch (Exception e) {
                    Log.e("RemoveCryptoException", e.getMessage());
                }
            }
        }
    }
}