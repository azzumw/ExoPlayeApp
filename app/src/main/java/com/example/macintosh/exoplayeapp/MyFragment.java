package com.example.macintosh.exoplayeapp;

import android.app.Dialog;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.PlaybackControlView;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSource;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

public class MyFragment extends Fragment {

    private static final String STATE_RESUME_WINDOW = "resumeWindow" ;
    private static final String STATE_RESUME_POSITION = "resumePosition";
    private final String STATE_PLAYER_FULLSCREEN = "playerFullscreen";
    private final String AUTOPLAY = "autoplay";

    final String PATH = "file:///android_asset/videoplayback.mp4";

    PlayerView mExoPlayerView;
    SimpleExoPlayer player;
    Dialog mFullScreenDialog;
    boolean mExoPlayerFullscreen = false;
    ImageView mFullScreenIcon;
    private FrameLayout mFullScreenButton;
    Bundle bundle;
    private MediaSource mVideoSource;

    private int mResumeWindow;
    private long mResumePosition;
    private boolean autoPlay = false;



    public MyFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment,container,false);
        setRetainInstance(true);
        mExoPlayerView = rootView.findViewById(R.id.playerViewId);

        if(savedInstanceState!=null){
            mResumeWindow = savedInstanceState.getInt(STATE_RESUME_WINDOW);
            mResumePosition = savedInstanceState.getLong(STATE_RESUME_POSITION);
            autoPlay = savedInstanceState.getBoolean(AUTOPLAY);
        }

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        initFullscreenDialog();
        initFullscreenButton();
        initExoPlayer();
    }



    private void initExoPlayer() {
        Uri uri = Uri.parse(PATH);

        //create exoplayer object
        BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();

        TrackSelection.Factory videoTrackSelectionFactory = new AdaptiveTrackSelection.Factory(bandwidthMeter);
        TrackSelector trackSelector = new DefaultTrackSelector(videoTrackSelectionFactory);
        player = ExoPlayerFactory.newSimpleInstance(getContext(),trackSelector);

        mVideoSource = buildMediaSource(uri);
        player.prepare(mVideoSource);
        //attach the player to the view
        mExoPlayerView.setPlayer(player);
        player.seekTo(mResumeWindow,mResumePosition);
        player.setPlayWhenReady(autoPlay);

    }

    private MediaSource buildMediaSource(Uri uri) {
        DefaultExtractorsFactory extractorSourceFactory = new DefaultExtractorsFactory();
        String userAgent = Util.getUserAgent(getContext(), getActivity().getApplicationContext().getApplicationInfo().packageName);
        DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(getContext(), userAgent);

        // this return a single mediaSource object. i.e. no next, previous buttons to play next/prev media file
        return new ExtractorMediaSource(uri, dataSourceFactory, extractorSourceFactory, null, null);
    }

    private void releasePlayer(){
        mResumeWindow = player.getCurrentWindowIndex();
        mResumePosition = player.getCurrentPosition();
        autoPlay = false;
        player.release();
        player = null;
    }

    @Override
    public void onPause() {
        super.onPause();
        releasePlayer();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        autoPlay = true;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.e("ON_SAVE","before");
        if(player==null){
            Log.e("ON_SAVE","ONSAVE");
            outState.putInt(STATE_RESUME_WINDOW,mResumeWindow);
            outState.putLong(STATE_RESUME_POSITION,mResumePosition);
            outState.putBoolean(AUTOPLAY,autoPlay);
        }
    }

    private void initFullscreenDialog(){
        mFullScreenDialog = new Dialog(getContext(), android.R.style.Theme_Black_NoTitleBar_Fullscreen) {
            public void onBackPressed() {
                if (mExoPlayerFullscreen)
                    closeFullscreenDialog();
                super.onBackPressed();
            }
        };
    }

    private void closeFullscreenDialog() {
        ((ViewGroup) mExoPlayerView.getParent()).removeView(mExoPlayerView);
        ((FrameLayout) getActivity().findViewById(R.id.main_media_frame)).addView(mExoPlayerView);
        mExoPlayerFullscreen = false;
        mFullScreenDialog.dismiss();
        mFullScreenIcon.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.ic_fullscreen_expand));
    }

    private void openFullscreenDialog() {

        ((ViewGroup) mExoPlayerView.getParent()).removeView(mExoPlayerView);
        mFullScreenDialog.addContentView(mExoPlayerView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        mFullScreenIcon.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.ic_fullscreen_skrink));
        mExoPlayerFullscreen = true;
        mFullScreenDialog.show();
    }

    private void initFullscreenButton() {

        PlaybackControlView controlView = mExoPlayerView.findViewById(R.id.exo_controller);
        mFullScreenIcon = controlView.findViewById(R.id.exo_fullscreen_icon);
        mFullScreenButton = controlView.findViewById(R.id.exo_fullscreen_button);
        mFullScreenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mExoPlayerFullscreen)
                    openFullscreenDialog();
                else
                    closeFullscreenDialog();
            }
        });
    }

}
