package com.example.watchbox.framework.recyclerviews.movieItem;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.watchbox.R;

class MovieItemViewHolder extends RecyclerView.ViewHolder {
    private TextView tvTitle, tvYear;

    MovieItemViewHolder(@NonNull View itemView,
                        MovieItemRecyclerAdapter.MovieItemClickListener listener) {
        super(itemView);

        tvTitle = itemView.findViewById(R.id.tv_title_content);
        tvYear = itemView.findViewById(R.id.tv_year_content);

        itemView.setOnClickListener(v -> listener.onItemClick(getBindingAdapterPosition()));
    }

    void setTitle(String title) {
        tvTitle.setText(title);
    }

    void setYear(String year) {
        tvYear.setText(year);
    }
}