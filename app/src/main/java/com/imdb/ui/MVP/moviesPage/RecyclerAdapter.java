package com.imdb.ui.mvp.moviespage;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.imdb.R;
import com.imdb.model.Movie;
import com.imdb.ui.mvp.moviedetail.MovieDetailFragment;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by prana on 6/30/2016.
 */
public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

    public static final String MOVIE_TITLE = "movie_title";
    public static final String MOVIE_RATED = "movie_rated";
    public static final String MOVIE_DURATION = "movie_duration";
    public static final String MOVIE_GENRE = "movie_genre";
    public static final String MOVIE_POSTER_URL = "movie_poster";
    public static final String MOVIE_PLOT = "movie_plot";
    public static final String MOVIE_DIRECTOR = "movie_director";
    public static final String MOVIE_LANGUAGE = "movie_languages";

    private Context mContext;
    private List<Movie> mMovieList;

    public RecyclerAdapter(Context context, List<Movie> movieList) {
        mContext = context;
        mMovieList = movieList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View holder = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.movie_list_row, parent, false);
        return new ViewHolder(holder);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.bindData(mMovieList.get(position));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MovieDetailFragment movieDetailFragment = new MovieDetailFragment();
                FragmentManager fragmentManager = ((MoviesListActivity) mContext).
                        getSupportFragmentManager();
                Bundle bundle = new Bundle();
                bundle.putString(MOVIE_TITLE, holder.movieName.getText().toString());
                bundle.putString(MOVIE_RATED, holder.movieRating.getText().toString());
                bundle.putString(MOVIE_DURATION, holder.movieRuntime.getText().toString());
                bundle.putString(MOVIE_GENRE, holder.movieGenres.getText().toString());
                bundle.putString(MOVIE_POSTER_URL, holder.urlPoster);
                bundle.putString(MOVIE_PLOT, holder.plotOfMovie);
                bundle.putString(MOVIE_DIRECTOR, holder.director);
                bundle.putString(MOVIE_LANGUAGE, holder.languages);
                movieDetailFragment.setArguments(bundle);

                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.moviesFrameLayout, movieDetailFragment,
                        MoviesListActivity.MOVIE_DETAIL_FRAGMENT);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
    }

    @Override
    public int getItemCount() {
        if (mMovieList != null) {
            return mMovieList.size();
        }
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.movie_title_poster)
        ImageView moviePoster;
        @BindView(R.id.movie_name)
        TextView movieName;
        @BindView(R.id.movie_rating)
        TextView movieRating;
        @BindView(R.id.movie_runtime)
        TextView movieRuntime;
        @BindView(R.id.movie_genres)
        TextView movieGenres;
        String urlPoster, plotOfMovie, director = "", languages;


        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        /**
         * Function to bind data
         *
         * @param movies
         */
        public void bindData(Movie movies) {

            urlPoster = movies.getUrlPoster();
            plotOfMovie = movies.getPlot();
            languages = movies.getLanguages().toString();

            for (int i = 0; i < movies.getDirectors().size(); i++) {
                director += movies.getDirectors().get(i).getName() + ", ";
            }
            director = director.substring(0, director.length() - 2);

            Picasso.with(mContext)
                    .load(movies.getUrlPoster())
                    .placeholder(R.mipmap.no_image)
                    .into(moviePoster);

            movieName.setText(movies.getTitle());
            movieRating.setText(movies.getRated());
            movieRuntime.setText(movies.getRuntime());
            movieGenres.setText(movies.getGenres().toString());
        }
    }
}