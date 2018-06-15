package com.tmdb.ui.mvp.moviedetail;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tmdb.R;
import com.tmdb.model.videos.VideoResult;
import com.tmdb.utils.ViewUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Pranav Bhoraskar
 */

public class VideoRecyclerAdapter extends RecyclerView.Adapter<VideoRecyclerAdapter.ViewHolder> {

    private final Context context;
    private final List<VideoResult> listOfVideos;
    private String videoKey;

    public VideoRecyclerAdapter(Context context, List<VideoResult> listOfVideos) {
        this.context = context;
        this.listOfVideos = listOfVideos;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View holder = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_video_recycler_view, parent, false);
        return new ViewHolder(holder);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.bindVideoData(listOfVideos.get(holder.getAdapterPosition()));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                videoKey = listOfVideos.get(holder.getAdapterPosition()).getKey();
                context.startActivity(new Intent(Intent.ACTION_VIEW,
                        Uri.parse(ViewUtils.YOUTUBE_URL + videoKey)));
            }
        });
    }

    @Override
    public int getItemCount() {
        return listOfVideos != null ? listOfVideos.size() : 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.video_name)
        TextView videoName;
        @BindView(R.id.video_type)
        TextView videoType;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindVideoData(VideoResult videoResult) {
            videoName.setText(videoResult.getName());
            videoType.setText(videoResult.getType());
        }
    }
}
