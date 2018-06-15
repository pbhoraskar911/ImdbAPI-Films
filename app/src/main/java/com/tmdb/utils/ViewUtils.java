package com.tmdb.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;

import com.tmdb.R;
import com.tmdb.ui.mvp.login.LoginActivity;
import com.tmdb.ui.mvp.splashscreen.LauncherActivity;

/**
 * Created by Pranav Bhoraskar
 */

public abstract class ViewUtils {

    public static final String IMDB_URL = "http://www.imdb.com/title/";
    public static final String YOUTUBE_URL = "https://www.youtube.com/watch?v=";

    /**
     * Check for the availability of Internet
     *
     * @return true if network status is active
     */
    public static boolean isInternetAvailable(Context context) {
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();

        return (activeNetwork != null && activeNetwork.isConnectedOrConnecting());
    }

    public static void createNoInternetDialog(@NonNull final Context context, int title) {

        AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.AlertDialogCustom);
        builder.setTitle(title);
        builder.setMessage(context.getString(R.string.no_internet));
        builder.setCancelable(false);
        builder.setPositiveButton(android.R.string.ok,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                        if (context instanceof LauncherActivity) {
                            ((LauncherActivity) context).finish();
                        }
                        if (context instanceof LoginActivity) {
                            ((LoginActivity) context).finish();
                        }
                    }
                });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}