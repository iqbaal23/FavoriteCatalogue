package com.example.favoritecatalogue.fragment;


import android.content.Context;
import android.database.ContentObserver;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.favoritecatalogue.LoadCallback;
import com.example.favoritecatalogue.MainActivity;
import com.example.favoritecatalogue.R;
import com.example.favoritecatalogue.adapter.MovieAdapter;
import com.example.favoritecatalogue.parcelable.MovieItems;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

import static com.example.favoritecatalogue.db.DatabaseContract.FavoriteMovieColumns.MOVIE_CONTENT_URI;
import static com.example.favoritecatalogue.helper.MappingHelper.mapMovieCursorToArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class MovieFragment extends Fragment implements LoadCallback {
    private MovieAdapter movieAdapter;
    RecyclerView rvMovie;

    public MovieFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_movie, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rvMovie = view.findViewById(R.id.rv_list);
        rvMovie.setLayoutManager(new LinearLayoutManager(getContext()));
        rvMovie.setHasFixedSize(true);
        movieAdapter = new MovieAdapter(getActivity());
        rvMovie.setAdapter(movieAdapter);

        HandlerThread handlerThread = new HandlerThread("DataObserver");
        handlerThread.start();
        Handler handler = new Handler(handlerThread.getLooper());

        DataObserver myObserver = new DataObserver(handler, getContext());
        if (getContext() != null){
            getContext().getContentResolver().registerContentObserver(MOVIE_CONTENT_URI, true, myObserver);
        }

        new getData(getContext(), this).execute();
    }

    @Override
    public void onResume() {
        super.onResume();
        new getData(getContext(), this).execute();
        Log.d("movie", "movie : onResume");
    }

    @Override
    public void postExecute(Cursor cursor) {
        ArrayList<MovieItems> listMovie = mapMovieCursorToArrayList(cursor);
        if (listMovie.size() > 0){
            movieAdapter.setListMovie(listMovie);
        } else {
            movieAdapter.setListMovie(new ArrayList<MovieItems>());
        }
    }

    static class DataObserver extends ContentObserver {
        final Context context;

        DataObserver(Handler handler, Context context) {
            super(handler);
            this.context = context;
        }

        @Override
        public void onChange(boolean selfChange) {
            super.onChange(selfChange);
            new getData(context, (MainActivity) context).execute();
        }
    }

    private static class getData extends AsyncTask<Void, Void, Cursor>{
        private final WeakReference<Context> weakContext;
        private final WeakReference<LoadCallback> weakCallback;

        private getData(Context context, LoadCallback callback) {
            weakContext = new WeakReference<>(context);
            weakCallback = new WeakReference<>(callback);
        }

        @Override
        protected Cursor doInBackground(Void... voids) {
            return weakContext.get().getContentResolver().query(MOVIE_CONTENT_URI, null, null, null, null);
        }

        @Override
        protected void onPostExecute(Cursor cursor) {
            super.onPostExecute(cursor);
            weakCallback.get().postExecute(cursor);
        }
    }
}
