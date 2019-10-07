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
import android.widget.Toast;

import com.example.favoritecatalogue.LoadCallback;
import com.example.favoritecatalogue.MainActivity;
import com.example.favoritecatalogue.R;
import com.example.favoritecatalogue.adapter.TvAdapter;
import com.example.favoritecatalogue.parcelable.TvItems;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

import static com.example.favoritecatalogue.db.DatabaseContract.FavoriteTvColumns.TV_CONTENT_URI;
import static com.example.favoritecatalogue.helper.MappingHelper.mapTvCursorToArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class TvShowsFragment extends Fragment implements LoadCallback {
    private TvAdapter tvAdapter;
    RecyclerView rvTv;

    public TvShowsFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_tv_shows, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rvTv = view.findViewById(R.id.rv_list);
        rvTv.setLayoutManager(new LinearLayoutManager(getContext()));
        rvTv.setHasFixedSize(true);
        tvAdapter = new TvAdapter(getActivity());
        rvTv.setAdapter(tvAdapter);

        HandlerThread handlerThread = new HandlerThread("DataObserver");
        handlerThread.start();
        Handler handler = new Handler(handlerThread.getLooper());

        DataObserver myObserver = new DataObserver(handler, getContext());
        if (getContext() != null){
            getContext().getContentResolver().registerContentObserver(TV_CONTENT_URI, true, myObserver);
        }

        new getData(getContext(), this).execute();
    }

    @Override
    public void onResume() {
        super.onResume();
        new getData(getContext(), this).execute();
        Log.d("tv", "tv : onResume");
    }

    @Override
    public void postExecute(Cursor cursor) {
        ArrayList<TvItems> listTv = mapTvCursorToArrayList(cursor);
        if (listTv.size() > 0){
            tvAdapter.setListTv(listTv);
        } else {
            tvAdapter.setListTv(new ArrayList<TvItems>());
        }
    }

    static class DataObserver extends ContentObserver{
        final Context context;

        public DataObserver(Handler handler, Context context) {
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
            return weakContext.get().getContentResolver().query(TV_CONTENT_URI, null, null, null, null);
        }

        @Override
        protected void onPostExecute(Cursor cursor) {
            super.onPostExecute(cursor);
            weakCallback.get().postExecute(cursor);
        }
    }
}
