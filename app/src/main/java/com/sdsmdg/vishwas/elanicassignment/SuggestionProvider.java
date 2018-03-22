package com.sdsmdg.vishwas.elanicassignment;

import android.content.SearchRecentSuggestionsProvider;

/**
 * Created by vishwas on 22/3/18.
 */

public class SuggestionProvider extends SearchRecentSuggestionsProvider {
    public final static String AUTHORITY = "com.sdsmdg.vishwas.elanicassignment.SuggestionProvider";
    public final static int MODE = DATABASE_MODE_QUERIES | DATABASE_MODE_2LINES;

    public SuggestionProvider() {
        setupSuggestions(AUTHORITY, MODE);
    }
}
