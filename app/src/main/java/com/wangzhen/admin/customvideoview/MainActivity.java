package com.wangzhen.admin.customvideoview;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Environment;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private CustomVideoView customVideoView ;
    private LinearLayout constraintLayout;
    private CustomFrameLayout customFrameLayout;
    private String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/out.mp4";
    private MediaPlayer mediaPlayer ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       /* customVideoView = findViewById(R.id.customVideoView);
        constraintLayout = findViewById(R.id.constrainLayout);
        customVideoView.setVideoPath(path);
        customVideoView.start();*/


        customFrameLayout = findViewById(R.id.customFrameLayout);
        customFrameLayout.setData(path,600,600);

       /* mediaPlayer = new MediaPlayer();
        try {
            mediaPlayer.setDataSource(path);
            mediaPlayer.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
        customVideoView.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                mediaPlayer.setDisplay(holder);
                mediaPlayer.start();
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {

            }
        });*/


    }
}
