package com.example.favoritecatalogue.parcelable;

import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

import com.example.favoritecatalogue.db.DatabaseContract;

import static android.provider.BaseColumns._ID;
import static com.example.favoritecatalogue.db.DatabaseContract.getColumnInt;
import static com.example.favoritecatalogue.db.DatabaseContract.getColumnString;

public class TvItems implements Parcelable {
    private int id;
    private String name, date, score, overview, photo;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public TvItems(){

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeString(date);
        dest.writeString(score);
        dest.writeString(overview);
        dest.writeString(photo);
    }

    protected TvItems(Parcel in) {
        this.id = in.readInt();
        this.name = in.readString();
        this.date = in.readString();
        this.score = in.readString();
        this.overview = in.readString();
        this.photo = in.readString();
    }

    public TvItems(int id, String name, String date, String score, String overview, String photo) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.score = score;
        this.overview = overview;
        this.photo = photo;
    }

    public TvItems(Cursor cursor){
        this.id = getColumnInt(cursor, _ID);
        this.name = getColumnString(cursor, DatabaseContract.FavoriteTvColumns.TITLE);
        this.date = getColumnString(cursor, DatabaseContract.FavoriteTvColumns.DATE);
        this.score = getColumnString(cursor, DatabaseContract.FavoriteTvColumns.SCORE);
        this.overview = getColumnString(cursor, DatabaseContract.FavoriteTvColumns.OVERVIEW);
        this.photo = getColumnString(cursor, DatabaseContract.FavoriteTvColumns.PHOTO);
    }

    public static final Creator<TvItems> CREATOR = new Creator<TvItems>() {
        @Override
        public TvItems createFromParcel(Parcel in) {
            return new TvItems(in);
        }

        @Override
        public TvItems[] newArray(int size) {
            return new TvItems[size];
        }
    };
}
