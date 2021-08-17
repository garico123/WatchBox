package com.example.watchbox.framework.recyclerviews.movieItem;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.watchbox.R;
import com.example.watchbox.framework.data.MovieDetails;

import java.util.List;

public class MovieItemRecyclerAdapter extends RecyclerView.Adapter<MovieItemViewHolder> {
    private MovieItemClickListener mListener;
    private Context mContext;
    private List<MovieDetails> mMoviesList;

    /**
     * interface to return viewHolder clicks to the presenter
     */
    public interface MovieItemClickListener {
        void onItemClick(int position);
    }

    public MovieItemRecyclerAdapter(Context context,
                                    List<MovieDetails> moviesList,
                                    MovieItemClickListener listener) {
        this.mContext = context;
        this.mMoviesList = moviesList;
        this.mListener = listener;
    }

    @NonNull
    @Override
    public MovieItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MovieItemViewHolder(LayoutInflater.from(mContext)
                .inflate(R.layout.movie_item, parent, false),
                mListener
        );
    }

    @Override
    public void onBindViewHolder(@NonNull MovieItemViewHolder holder, int position) {
        MovieDetails details = mMoviesList.get(position);
        holder.setTitle(details.getTitle());
        holder.setYear(details.getReleased());
    }

    @Override
    public int getItemCount() {
        return mMoviesList.size();
    }

    public void setList(List<MovieDetails> moviesList) {
        this.mMoviesList = moviesList;
    }
}
