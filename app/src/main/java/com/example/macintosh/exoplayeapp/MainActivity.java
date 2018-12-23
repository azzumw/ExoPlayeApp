package com.example.macintosh.exoplayeapp;

import android.app.Dialog;

import android.content.res.Configuration;
import android.net.Uri;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

public class MainActivity extends AppCompatActivity {

    private static final String STATE_RESUME_WINDOW = "resumeWindow" ;
    private static final String STATE_RESUME_POSITION = "resumePosition";
    private final String STATE_PLAYER_FULLSCREEN = "playerFullscreen";

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

    MyFragment myFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myFragment = new MyFragment();

        FragmentManager fragmentManager = getSupportFragmentManager();

        fragmentManager.beginTransaction().add(R.id.main_media_frame,myFragment).commit();
//        bundle = null;
//        mExoPlayerView = findViewById(R.id.playerview);

//        if (savedInstanceState != null) {
//            mResumeWindow = savedInstanceState.getInt(STATE_RESUME_WINDOW);
//            mResumePosition = savedInstanceState.getLong(STATE_RESUME_POSITION);
//            mExoPlayerFullscreen = savedInstanceState.getBoolean(STATE_PLAYER_FULLSCREEN);
//        }
    }


    //    @Override
//    public void onResume() {
//        super.onResume();
//        if (mExoPlayerView == null) {
//
//            mExoPlayerView =  findViewById(R.id.playerview);
//            initFullscreenDialog();
//            initFullscreenButton();
//
//            if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
//                requestWindowFeature(Window.FEATURE_NO_TITLE);
//                getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//                        WindowManager.LayoutParams.FLAG_FULLSCREEN);
//                openFullscreenDialog();
//            }
//
//            Uri uri = Uri.parse(PATH);
//            String userAgent = Util.getUserAgent(this, getApplicationContext().getApplicationInfo().packageName);
//
////            DefaultHttpDataSourceFactory httpDataSourceFactory = new DefaultHttpDataSourceFactory(userAgent, null, DefaultHttpDataSource.DEFAULT_CONNECT_TIMEOUT_MILLIS, DefaultHttpDataSource.DEFAULT_READ_TIMEOUT_MILLIS, true);
////            DefaultDataSourceFactory dataSourceFactory = new DefaultDataSourceFactory(getContext(), null, httpDataSourceFactory);
//            DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(this, userAgent);
//
//            mVideoSource = new ExtractorMediaSource(uri, dataSourceFactory, new DefaultExtractorsFactory(), null, null);
//        }
//
//        initExoPlayer();
//
//        if (mExoPlayerFullscreen) {
//            ((ViewGroup) mExoPlayerView.getParent()).removeView(mExoPlayerView);
//            mFullScreenDialog.addContentView(mExoPlayerView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
//            mFullScreenIcon.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_fullscreen_skrink));
//            mFullScreenDialog.show();
//        }
//    }


//    private void initExoPlayer() {
//
//
//        BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
//
//        TrackSelection.Factory videoTrackSelectionFactory = new AdaptiveTrackSelection.Factory(bandwidthMeter);
//        TrackSelector trackSelector = new DefaultTrackSelector(videoTrackSelectionFactory);
//        player = ExoPlayerFactory.newSimpleInstance(this, trackSelector);
//        mExoPlayerView.setPlayer(player);
//        player.prepare(mVideoSource);
//
//
//
//
//        if(bundle!=null){
//            int resumeWindow = bundle.getInt(STATE_RESUME_WINDOW);
//            long resumePostion = bundle.getLong(STATE_RESUME_POSITION);
//
//            player.seekTo(resumeWindow,resumePostion);
//
//            player.setPlayWhenReady(false);
//        }else {
//            player.setPlayWhenReady(true);
//        }
//
//    }


//    private void initFullscreenDialog(){
//        mFullScreenDialog = new Dialog(this, android.R.style.Theme_Black_NoTitleBar_Fullscreen) {
//            public void onBackPressed() {
//                if (mExoPlayerFullscreen)
//                    closeFullscreenDialog();
//                super.onBackPressed();
//            }
//        };
//    }


//    private void closeFullscreenDialog() {
//        ((ViewGroup) mExoPlayerView.getParent()).removeView(mExoPlayerView);
//        ((FrameLayout) findViewById(R.id.main_media_frame)).addView(mExoPlayerView);
//        mExoPlayerFullscreen = false;
//        mFullScreenDialog.dismiss();
//        mFullScreenIcon.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_fullscreen_expand));
//    }

//    private void openFullscreenDialog() {
//
//        ((ViewGroup) mExoPlayerView.getParent()).removeView(mExoPlayerView);
//        mFullScreenDialog.addContentView(mExoPlayerView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
//        mFullScreenIcon.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_fullscreen_skrink));
//        mExoPlayerFullscreen = true;
//        mFullScreenDialog.show();
//    }

//    @Override
//    protected void onStop() {
//        super.onStop();
//        mExoPlayerView.setPlayer(null);
//        player.release();
//        player = null;
//    }

//    private void initFullscreenButton() {
//
//        PlaybackControlView controlView = mExoPlayerView.findViewById(R.id.exo_controller);
//        mFullScreenIcon = controlView.findViewById(R.id.exo_fullscreen_icon);
//        mFullScreenButton = controlView.findViewById(R.id.exo_fullscreen_button);
//        mFullScreenButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (!mExoPlayerFullscreen)
//                    openFullscreenDialog();
//                else
//                    closeFullscreenDialog();
//            }
//        });
//    }




//    @Override
//    public void onPause() {
//        super.onPause();
//
//        super.onPause();
//
//        if (mExoPlayerView != null && mExoPlayerView.getPlayer() != null) {
//            mResumeWindow = mExoPlayerView.getPlayer().getCurrentWindowIndex();
//            mResumePosition = Math.max(0, mExoPlayerView.getPlayer().getContentPosition());
//            bundle.putLong(STATE_RESUME_POSITION,mResumePosition);
//            bundle.putInt(STATE_RESUME_WINDOW,mResumeWindow);
//
//            mExoPlayerView.getPlayer().release();
//        }
//
//        if (mFullScreenDialog != null)
//            mFullScreenDialog.dismiss();
//    }



//    @Override
//    protected void onSaveInstanceState(Bundle outState) {
//        super.onSaveInstanceState(outState);
//        long ResumePosition = player.getCurrentPosition();
//        int ResumeWindow = player.getCurrentWindowIndex();
//        bundle = outState;
//        bundle.putBoolean(STATE_PLAYER_FULLSCREEN,mExoPlayerFullscreen);
//        bundle.putInt(STATE_RESUME_WINDOW, ResumeWindow);
//        bundle.putLong(STATE_RESUME_POSITION, ResumePosition);
//
//    }



//    @Override
//    public void onConfigurationChanged(Configuration newConfig) {
//        super.onConfigurationChanged(newConfig);
//
//        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE){
//            openFullscreenDialog();
//        }
//        else {
//            closeFullscreenDialog();
//        }
//
//    }


//    @Override
//    protected void onStart() {
//        super.onStart();
//        initExoPlayer();
//    }




//    @Override
//    protected void onPause() {
//
//        super.onPause();
//
//        if (mExoPlayerView != null && mExoPlayerView.getPlayer() != null) {
//            mResumeWindow = mExoPlayerView.getPlayer().getCurrentWindowIndex();
//            mResumePosition = Math.max(0, mExoPlayerView.getPlayer().getContentPosition());
//
//            mExoPlayerView.getPlayer().release();
//        }
//
//        if (mFullScreenDialog != null)
//            mFullScreenDialog.dismiss();
//    }
}
