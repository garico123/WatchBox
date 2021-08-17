package com.example.watchbox.framework.data;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

public class MovieDetails implements Parcelable {
    private String mId;
    private String mTitle;
    private String mPlot;
    private String mReleased;
    private String mCast;
    private String mDirector;
    private String mWriters;
    private String mType;
    private String mRating;
    private boolean mFavourite, mWatched, mRecommended;
    private String mPosterURL;
    private Bitmap mPoster;

    public MovieDetails(){
        this.mFavourite = false;
        this.mWatched = false;
        this.mRecommended = false;
    }

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        this.mId = id;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        this.mTitle = title;
    }

    public String getPlot() {
        return mPlot;
    }

    public void setPlot(String plot) {
        this.mPlot = plot;
    }

    public String getReleased() {
        return mReleased;
    }

    public void setReleased(String released) {
        this.mReleased = released;
    }

    public String getCast() {
        return mCast;
    }

    public void setCast(String cast) {
        this.mCast = cast;
    }

    public String getDirector() {
        return mDirector;
    }

    public void setDirector(String director) {
        this.mDirector = director;
    }

    public String getWriters() {
        return mWriters;
    }

    public void setWriters(String writers) {
        this.mWriters = writers;
    }

    public String getType() {
        return mType;
    }

    public void setType(String type) {
        this.mType = type;
    }

    public String getRating() {
        return mRating;
    }

    public void setRating(String rating) {
        this.mRating = rating;
    }

    public boolean isFavourite() {
        return mFavourite;
    }

    public void setFavourite(boolean favourite) {
        this.mFavourite = favourite;
    }

    public String getPosterUrl() {
        return mPosterURL;
    }

    public void setPosterURL(String url) {
        this.mPosterURL = url;
    }

    public boolean isWatched() {
        return mWatched;
    }

    public void setWatched(boolean watched) {
        this.mWatched = watched;
    }

    public boolean isRecommended() {
        return mRecommended;
    }

    public void setRecommended(boolean recommended) {
        this.mRecommended = recommended;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    protected MovieDetails(Parcel in) {
        mId = in.readString();
        mTitle = in.readString();
        mPlot = in.readString();
        mReleased = in.readString();
        mCast = in.readString();
        mDirector = in.readString();
        mWriters = in.readString();
        mType = in.readString();
        mRating = in.readString();
        mFavourite = in.readInt() == 1;
        mWatched = in.readInt() == 1;
        mRecommended = in.readInt() == 1;
        mPosterURL = in.readString();
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeString(mId);
        out.writeString(mTitle);
        out.writeString(mPlot);
        out.writeString(mReleased);
        out.writeString(mCast);
        out.writeString(mDirector);
        out.writeString(mWriters);
        out.writeString(mType);
        out.writeString(mRating);
        out.writeInt(mFavourite ? 1 : 0);
        out.writeInt(mWatched ? 1 : 0);
        out.writeInt(mRecommended ? 1 : 0);
        out.writeString(mPosterURL);
    }

    public static final Creator<MovieDetails> CREATOR = new Creator<MovieDetails>() {
        @Override
        public MovieDetails createFromParcel(Parcel in) {
            return new MovieDetails(in);
        }

        @Override
        public MovieDetails[] newArray(int size) {
            return new MovieDetails[size];
        }
    };

    public Bitmap getPoster() {
        return mPoster;
    }

    public void setPoster(Bitmap poster) {
        this.mPoster = poster;
    }
}