package com.wangzhen.admin.customvideoview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.media.MediaPlayer;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.VideoView;

/**
 * Created by admin on 2019/1/4.
 */

public class CustomFrameLayout extends FrameLayout {

    private VideoView videoView;
    private CustomImageView customImageView;
    private Context context;
    private float video_width;//视频宽度
    private float video_height;//视频高度

    public CustomFrameLayout(@NonNull Context context) {
        super(context);
        setWillNotDraw(false);
        this.context = context;
        initData();
    }

    public CustomFrameLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setWillNotDraw(false);
        this.context = context;
        initData();
    }

    public CustomFrameLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setWillNotDraw(false);
        this.context = context;
        initData();
    }

    private void initData() {
        videoView = new VideoView(context);
        addView(videoView);
    }

    /**
     * @param path   视频路径
     * @param width  视频宽度
     * @param height 视频高度
     */
    public void setData(String path, float width, float height) {
        videoView.setVideoPath(path);
        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.setLooping(true);
                mp.start();
            }
        });

        //添加view
        video_width = width ;
        video_height = height;
        customImageView = new CustomImageView(context);
        customImageView.setScreentSize(video_width, video_height);
        addView(customImageView);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }
}
