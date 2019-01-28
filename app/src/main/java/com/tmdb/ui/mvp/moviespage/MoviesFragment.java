package com.tmdb.ui.mvp.moviespage;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.tmdb.R;
import com.tmdb.di.TmdbApplication;
import com.tmdb.di.component.NetworkComponent;
import com.tmdb.model.Result;
import com.tmdb.session.SessionManager;
import com.tmdb.utils.ViewUtils;

import java.util.ArrayList;

import javax.inject.Inject;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import butterknife.BindView;
import butterknife.ButterKnife;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import retrofit2.Retrofit;
import timber.log.Timber;

/**
 * Created by Pranav Bhoraskar
 */

public class MoviesFragment extends Fragment implements MoviesView,
        SwipeRefreshLayout.OnRefreshListener {

    private static final String TAG = MoviesFragment.class.getSimpleName();
    private static final String SAVE_MOVIES_STATE = "movies_state";

    @BindView(R.id.recycler_view)
    RecyclerView movieRecyclerView;
    @BindView(R.id.progress_bar)
    ProgressBar progressBar;
    @BindView(R.id.swiperefresh)
    SwipeRefreshLayout swipeRefresh;

    @Inject
    Retrofit retrofit;

    private String tag;
    boolean flag = false;

    private SessionManager sessionManager;
    private MoviesPresenter moviesPresenter;
    private GridLayoutManager gridLayoutManager;
    private RecyclerView.Adapter recyclerAdapter;

    private ArrayList<Result> listOfMovies = new ArrayList<>();

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
        tag = bundle.getString("tag");

        if (ViewUtils.isInternetAvailable(getActivity())) {
            if (savedInstanceState != null) {
                listOfMovies = savedInstanceState.getParcelableArrayList(SAVE_MOVIES_STATE);
            }
            if (bundle != null && bundle.containsKey(SAVE_MOVIES_STATE)) {
                listOfMovies = bundle.getParcelableArrayList(SAVE_MOVIES_STATE);
                return view;
            }
            else {
                listOfMovies = new ArrayList<>();
            }
            setUpSwipeRefresh();
        }
        else {
            swipeRefresh.setEnabled(false);
        }
        return view;
    }

    @SuppressFBWarnings("ST_WRITE_TO_STATIC_FROM_INSTANCE_METHOD")
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        fetchMovieDetails(tag);
    }

    private void fetchMovieDetails(String tag) {
        if (ViewUtils.isInternetAvailable(getActivity())) {
            if (!listOfMovies.isEmpty()) {
                setUpRecyclerView(listOfMovies, tag);
                flag = true;
            }
            else {
                moviesPresenter = new MoviesPresenter(this, retrofit, tag);
                moviesPresenter.makeRetrofitCall();
            }
        }
        else {
            swipeRefresh.setEnabled(false);
            hideProgress();
            displaySnackBar();
        }
    }

    private void setUpSwipeRefresh() {
        swipeRefresh.setOnRefreshListener(this);
        swipeRefresh.setNestedScrollingEnabled(true);
        swipeRefresh.setColorSchemeResources(R.color.colorAccent, R.color.colorPrimaryDark);
    }

    @Override
    public void setUpRecyclerView(ArrayList<Result> listOfMovies, String tag) {
        this.listOfMovies = listOfMovies;
        recyclerAdapter = new RecyclerAdapter(getActivity(), this.listOfMovies, tag);

        gridLayoutManager = new GridLayoutManager(getActivity(), 2);

        if (isLandscapeMode()) {
            gridLayoutManager.setSpanCount(3);
            movieRecyclerView.setLayoutManager(gridLayoutManager);
        }
        else {
            movieRecyclerView.setLayoutManager(gridLayoutManager);
        }
        movieRecyclerView.setAdapter(recyclerAdapter);
        movieRecyclerView.setHasFixedSize(true);
    }

    private boolean isLandscapeMode() {
        return getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;
    }

    /**
     * show error dialog if network is not available
     */
    @Override
    public void showErrorDialog() {
        ViewUtils.createNoInternetDialog(getActivity(), R.string.network_error);
    }

    private NetworkComponent getNetComponent() {
        return ((TmdbApplication) getActivity().getApplication()).getNetworkComponent();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        if (menu.size() == 0) {
            inflater.inflate(R.menu.menu_movie, menu);
        }
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
            Timber.tag(TAG).i("Refresh Clicked..");
            if (ViewUtils.isInternetAvailable(getActivity())) {
                fetchMovieDetails(tag);
                Toast.makeText(getActivity(), "Refreshing Movies..", Toast.LENGTH_SHORT).show();
            }
            else {
                swipeRefresh.setEnabled(false);
                hideProgress();
                displaySnackBar();
            }
            return true;
        }
        if ((item.getItemId()) == R.id.logout) {
            Timber.tag(TAG).i("Logging out...");
            sessionManager = new SessionManager(getActivity());
            sessionManager.logoutUser();
            getActivity().finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        flag = false;
    }

    @Override
    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        if (swipeRefresh.isRefreshing()) {
            swipeRefresh.setRefreshing(false);
        }
        progressBar.setVisibility(View.INVISIBLE);
    }

    private void displaySnackBar() {
        Snackbar.make(getView(), R.string.network_error, Snackbar.LENGTH_LONG)
                .setAction(R.string.retry, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        fetchMovieDetails(tag);
                    }
                })
                .setActionTextColor(getResources().getColor(R.color.colorPrimaryLight))
                .show();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(SAVE_MOVIES_STATE, listOfMovies);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (ViewUtils.isInternetAvailable(getActivity())) {
            if (isLandscapeMode()) {
                gridLayoutManager.setSpanCount(3);
            }
            else {
                gridLayoutManager.setSpanCount(2);
            }
        }
        else {
            swipeRefresh.setEnabled(false);
            displaySnackBar();
        }
    }

    @Override
    public void onRefresh() {
        if (swipeRefresh.isRefreshing()) {
            swipeRefresh.setRefreshing(false);
        }
        fetchMovieDetails(tag);
    }

    public static Fragment newInstance() {
        return new MoviesFragment();
    }
}