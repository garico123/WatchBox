package com.example.watchbox.screens.moviedetails;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.watchbox.R;
import com.example.watchbox.framework.utils.AppConstants;
import com.example.watchbox.mvp.BaseActivity;
import com.example.watchbox.framework.data.MovieDetails;
import com.example.watchbox.screens.moviedetails.repository.MovieDetailsRepository;

public class MovieDetailsActivity extends BaseActivity implements MovieDetailsContract.View {
    private MovieDetailsContract.Presenter mPresenter;
    private TextView tvTitle, tvPlot, tvYear, tvCast, tvDirector, tvWriters, tvType, tvRatings;
    private CheckBox cbFavourite, cbWatched, cbRecommended;
    private ImageView ivPoster;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wb_activity_movie_details);

        MovieDetails movieDetails = getIntent().getParcelableExtra(AppConstants.MOVIE_DETAILS);

        new MovieDetailsPresenter(this,
                MovieDetailsRepository.getInstance(),
                movieDetails
        );
    }

    @Override
    public void setPresenter(MovieDetailsContract.Presenter presenter) {
        mPresenter = presenter;
        mPresenter.start();
    }

    @Override
    public void initViews() {
        ivPoster = findViewById(R.id.iv_poster_img);
        tvTitle = findViewById(R.id.tv_title);
        tvPlot = findViewById(R.id.tv_plot);
        tvYear = findViewById(R.id.tv_released_content);
        tvCast = findViewById(R.id.tv_cast_content);
        tvDirector = findViewById(R.id.tv_director_content);
        tvWriters = findViewById(R.id.tv_writers_content);
        tvType = findViewById(R.id.tv_type_content);
        tvRatings = findViewById(R.id.tv_ratings_content);
        cbFavourite = findViewById(R.id.cb_favourite);
        cbWatched = findViewById(R.id.cb_watched);
        cbRecommended = findViewById(R.id.cb_recommended);
    }

    @Override
    public void setTitle(String title) {
        tvTitle.setText(title);
    }

    @Override
    public void setPlot(String plot) {
        tvPlot.setText(plot);
    }

    @Override
    public void setDirector(String director) {
        tvDirector.setText(director);
    }

    @Override
    public void setYear(String year) {
        tvYear.setText(year);
    }

    @Override
    public void setCast(String cast) {
        tvCast.setText(cast);
    }

    @Override
    public void setType(String type) {
        tvType.setText(type);
    }

    @Override
    public void setRatings(String rating) {
        tvRatings.setText(rating);
    }

    @Override
    public void setWriters(String writers) {
        tvWriters.setText(writers);
    }

    @Override
    public void setFavourite(boolean isFavourite) {
        cbFavourite.setChecked(isFavourite);
    }

    @Override
    public void initListeners() {
        cbFavourite.setOnCheckedChangeListener(
                (buttonView, isChecked) -> mPresenter.onFavouriteChanged(isChecked)
        );

        cbWatched.setOnCheckedChangeListener(
                (buttonView, isChecked) -> mPresenter.onWatchedChanged(isChecked)
        );

        cbRecommended.setOnCheckedChangeListener(
                (buttonView, isChecked) -> mPresenter.onRecommendedChanged(isChecked)
        );
    }

    @Override
    public void setPoster(Bitmap poster) {
        ivPoster.setImageBitmap(poster);
    }

    @Override
    public void setWatched(boolean watched) {
        cbWatched.setChecked(watched);
    }

    @Override
    public void setRecommended(boolean recommended) {
        cbRecommended.setChecked(recommended);
    }
}