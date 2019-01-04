package com.wangzhen.admin.customvideoview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.ImageView;

/**
 * Created by admin on 2019/1/4.
 */

public class CustomImageView extends ImageView {

    public static final int VERTICAL = 1;//竖屏
    public static final int HORIZONTAL = 2;//横屏
    public static final int SQUARE = 3;//方屏

    private Paint paint_border;
    private Paint paint_shade; //阴影部分画笔
    private float screentSize; //裁剪框比例
    private float videoWidth;//视频宽高
    private float videoHeight;//视频高度
    private int screen; //竖屏 横屏 方屏
    private boolean isFirst = true;//初次绘制
    private int screenHeight;//矩形框高度
    private int screenWidth;//矩形框宽度


    private boolean isSide = false; //按下的时候是否在裁剪框内

    private int top = 0, right = 0, left = 0, bottom = 0;

    public CustomImageView(Context context) {
        super(context);
        initData();
    }

    public CustomImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initData();
    }

    public CustomImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initData();
    }

    private void initData() {
        //设置白色边框
        paint_border = new Paint();
        paint_border.setColor(Color.WHITE);
        paint_border.setStrokeWidth(10);
        paint_border.setStyle(Paint.Style.STROKE);
        //透明影音部分
        //绘制四周矩形阴影区域
        paint_shade = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint_shade.setColor(Color.BLACK);
        paint_shade.setStyle(Paint.Style.FILL);
        paint_shade.setAlpha(170);//取值范围为0~255，数值越小越透明

    }

    public void setScreentSize(float width, float height) {
        videoWidth = width;
        videoHeight = height;
        screentSize = width / height;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        screenHeight = getHeight();
        screenWidth = getWidth();
        int height = screenHeight;
        int width = screenWidth;
        if (isFirst) {
            if (screentSize != 1) {
                //如果width 大于 height 就已width作为标准 反之则已height作为标准
                float heightScreenSize = height / videoHeight;
                float widthScreenSize = width / videoWidth;
                if (heightScreenSize > widthScreenSize) {
                    //横
                    height = (int) (width * videoHeight / videoWidth);
                    left = 0;
                    right = left + width;
                    top = getHeight() / 2 - (height / 2);
                    bottom = top + height;
                    screen = HORIZONTAL;
                } else {
                    //竖
                    width = (int) (height * videoWidth / videoHeight);
                    top = 0;
                    bottom = top + height;
                    left = getWidth() / 2 - (width / 2);
                    right = left + width;
                    screen = VERTICAL;
                }
                canvas.drawRect(left, top, right, bottom, paint_border);
            } else {
                //方
                screen = SQUARE;
                if (screenWidth >= screenHeight) {
                    //横屏
                    width = getHeight();
                    top = 0;
                    bottom = width;
                    left = getWidth() / 2 - (width / 2);
                    right = left + width;
                } else {
                    //竖屏
                    height = getWidth();
                    left = 0;
                    right = height;
                    top = getHeight() / 2 - (height / 2);
                    bottom = top + height;
                }
            }
        }
        canvas.drawRect(left, top, right, bottom, paint_border);
        drawMaskBitmap(canvas, top, left, right, bottom);
    }

    //画阴影部分
    private void drawMaskBitmap(Canvas canvas, int top, int left, int right, int bottom) {
        //左边
        canvas.drawRect(0, 0, left, bottom, paint_shade);
        //右边
        canvas.drawRect(right, 0, getWidth(), bottom, paint_shade);
        //上边
        canvas.drawRect(0, 0, getWidth(), top, paint_shade);
        //下边
        canvas.drawRect(0, bottom, getWidth(), getHeight(), paint_shade);
    }

    private int pressDownX, pressDownY;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        isFirst = false;
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Rect rect = new Rect(left, top, right, bottom);
                pressDownX = (int) event.getX();
                pressDownY = (int) event.getY();
                isSide = rect.contains(pressDownX, pressDownY);
                break;
            case MotionEvent.ACTION_MOVE:
                if (isSide) {
                    //在边框内
                    int moveX = (int) event.getX();
                    int moveY = (int) event.getY();
                    moveByTouch(moveX, moveY);
                }
                break;
            case MotionEvent.ACTION_UP:
                isSide = false;
                break;
            default:
                break;
        }
        return true;
    }

    /**
     * 矩形移动
     *
     * @param mx
     * @param my
     */
    private void moveByTouch(int mx, int my) {
        int dx = mx - pressDownX;
        int dy = my - pressDownY;
        left += dx;
        right += dx;
        top += dy;
        bottom += dy;
        //同时改变left和right值, 达到左右移动的效果
        if (left < 0 || right > screenWidth) {//判断x轴的移动边界
            left -= dx;
            right -= dx;
        }
        //同时改变top和bottom值, 达到上下移动的效果
        if (top < 0 || bottom > screenHeight) {//判断y轴的移动边界
            top -= dy;
            bottom -= dy;
        }
        //实时触发onDraw()方法
        postInvalidate();
        pressDownX = mx;
        pressDownY = my;
    }
}
