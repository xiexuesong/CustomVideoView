package com.wangzhen.admin.customvideoview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceView;
import android.widget.VideoView;

/**
 * Created by admin on 2019/1/4.
 */

public class CustomVideoView extends VideoView {

    private Paint paint_shade;//透明遮罩层画笔
    private Paint paint_border;//边框画笔
    private Context context;

    public CustomVideoView(Context context) {
        super(context);
        this.context = context;
        initData();
    }

    public CustomVideoView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        initData();
    }

    public CustomVideoView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        initData();
    }

    //初始化数据
    private void initData() {
        //绘制中间透明区域矩形边界的Paint
        paint_border = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint_border.setColor(Color.TRANSPARENT);//设置中间区域颜色为透明
        paint_border.setStyle(Paint.Style.STROKE);
        paint_border.setStrokeWidth(3f);
        paint_border.setAlpha(0);//取值范围为0~255，数值越小越透明

        //绘制四周矩形阴影区域
        paint_shade = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint_shade.setColor(Color.RED);
        paint_shade.setStyle(Paint.Style.FILL);
        paint_shade.setAlpha(170);//取值范围为0~255，数值越小越透明
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Log.i("MDL","width:" + getWidth() + " height:" + getWidth());
        canvas.drawRect(0,0,getWidth(),getHeight(),paint_shade);
        canvas.drawText("text:",100,100,paint_shade);
    }

}
