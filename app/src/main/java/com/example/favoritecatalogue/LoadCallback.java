package com.example.favoritecatalogue;

import android.database.Cursor;

public interface LoadCallback {
    void postExecute(Cursor cursor);
}
