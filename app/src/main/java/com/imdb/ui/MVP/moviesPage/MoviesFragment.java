package com.imdb.ui.mvp.moviespage;


import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.imdb.R;
import com.imdb.di.ImdbApplication;
import com.imdb.di.component.NetworkComponent;
import com.imdb.model.Movie;
import com.imdb.session.SessionManager;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import retrofit2.Retrofit;

/**
 * Created by prana on 6/30/2016.
 */
public class MoviesFragment extends Fragment implements MoviesView {

    private static final String TAG = MoviesFragment.class.getSimpleName();
    private static final String SAVE_MOVIES_STATE = "movies_state";

    @BindView(R.id.movie_recycler_view)
    RecyclerView movieRecyclerView;
    @BindView(R.id.progress_bar)
    ProgressBar progressBar;

    @Inject
    Retrofit retrofit;

    private RecyclerView.Adapter recyclerAdapter;
    private String access_token = "159ab6ca-37ea-4351-ba42-9ef903beacc3";

    ArrayList<Movie> listOfMovies = new ArrayList<>();
    boolean flag = false;

    SessionManager sessionManager;

    MoviesPresenter moviesPresenter;

    public MoviesFragment() {
        setArguments(new Bundle());
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getNetComponent().inject(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movies, container, false);
        ButterKnife.bind(this, view);
        setHasOptionsMenu(true);

        Bundle bundle = getArguments();
        if (bundle != null && bundle.containsKey(SAVE_MOVIES_STATE)) {
            listOfMovies = bundle.getParcelableArrayList(SAVE_MOVIES_STATE);
            return view;
        }
        return view;
    }

    @SuppressFBWarnings("ST_WRITE_TO_STATIC_FROM_INSTANCE_METHOD")
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (!listOfMovies.isEmpty()) {
            setUpRecyclerView(listOfMovies);
            flag = true;
        }
        else {
            listOfMovies = new ArrayList<>();
        }

        if (!flag) {
            moviesPresenter = new MoviesPresenter(this, retrofit);
            if (moviesPresenter.isNetworkAvailable()) {
                moviesPresenter.makeRetrofitCall();
            }
            else {
                showErrorDialog();
            }
        }
        else {
            setUpRecyclerView(listOfMovies);
            progressBar.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void setUpRecyclerView(ArrayList<Movie> listOfMovies) {
        this.listOfMovies = listOfMovies;
        recyclerAdapter = new RecyclerAdapter(getActivity(), this.listOfMovies);
        movieRecyclerView.setAdapter(recyclerAdapter);
        movieRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        movieRecyclerView.setHasFixedSize(true);
    }

    /**
     * show error dialog if network is not available
     */
    @Override
    public void showErrorDialog() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
        dialog.setTitle(getResources().getString(R.string.network_error));
        dialog.setMessage(getResources().getString(R.string.network_error_message));
        dialog.setCancelable(false);
        dialog.setPositiveButton(getResources().getString(android.R.string.ok),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        dialog.dismiss();
                        getActivity().finish();
                    }
                }).show();
    }

    private NetworkComponent getNetComponent() {
        return ((ImdbApplication) getActivity().getApplication()).getNetworkComponent();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_movie, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    /**
     * Setting the event on the Menu item
     *
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if ((item.getItemId()) == R.id.action_refresh) {
            Log.i(TAG, "Refresh Clicked..");
            moviesPresenter = new MoviesPresenter(this, retrofit);
            moviesPresenter.makeRetrofitCall();
            return true;
        }
        if ((item.getItemId()) == R.id.logout) {
            Log.i(TAG, "Logging out...");
            sessionManager = new SessionManager(getActivity());
            sessionManager.logoutUser();
            getActivity().finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onPause() {
        super.onPause();
        if (listOfMovies != null) {
            getArguments().putParcelableArrayList(SAVE_MOVIES_STATE, listOfMovies);
            flag = true;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        flag = false;
    }

    @Override
    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(View.INVISIBLE);
    }
}