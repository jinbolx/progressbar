package com.example.jin.customtextview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;

/**
 * Created by jin on 2017/4/21
 */

public class CProgressBar extends View {
    private int firstColor;
    private int secondColor;
    private int speed=10;
    private int circleWidth;
    private Paint mPaint;
    private int mProgress = 0;
    private boolean isNext=false;
    private RectF oval;

    public int getFirstColor() {
        return firstColor;
    }

    public void setFirstColor(int firstColor) {
        this.firstColor = firstColor;
    }

    public int getSecondColor() {
        return secondColor;
    }

    public void setSecondColor(int secondColor) {
        this.secondColor = secondColor;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getCircleWidth() {
        return circleWidth;
    }

    public void setCircleWidth(int circleWidth) {
        this.circleWidth = circleWidth;
    }

    public CProgressBar(Context context) {
        this(context, null);
    }

    public CProgressBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CProgressBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.getTheme().obtainStyledAttributes(attrs, R.styleable.CProgressBar, defStyleAttr, 0);
        int att = typedArray.getIndexCount();
        for (int i = 0; i < att; i++) {
            int a = typedArray.getIndex(i);
            switch (a) {
                case R.styleable.CProgressBar_firstColor:
                    firstColor = typedArray.getColor(a, Color.BLACK);
                    break;
                case R.styleable.CProgressBar_secondColor:
                    secondColor = typedArray.getColor(a, Color.BLUE);
                    break;
                case R.styleable.CProgressBar_circleWidth:
                    circleWidth = typedArray.getDimensionPixelSize(a, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,20,getResources().getDisplayMetrics()));
                    break;
                case R.styleable.CProgressBar_speed:
                    speed = typedArray.getInt(a, 0);
                    break;
            }
        }
        Log.i("tag","f: "+firstColor+" s: "+secondColor+" cir: "+circleWidth+ "  s: "+speed);
        typedArray.recycle();
        mPaint = new Paint();

        new Thread() {
            @Override
            public void run() {
                super.run();
                while (true) {
                    mProgress++;
                    if (mProgress == 360) {
                        mProgress = 0;
                        isNext = !isNext;
                    }
                    postInvalidate();
                    try {
                        Thread.sleep(1000/speed);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();
    }

    @SuppressLint("DrawAllocation")
    @Override
    protected void onDraw(Canvas canvas) {
        int center = getWidth() / 2;
        int radius = center - circleWidth/2;
        oval = new RectF(center - radius, center - radius, center + radius, center + radius);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(circleWidth);
        mPaint.setAntiAlias(true);
        if (isNext) {
            mPaint.setColor(firstColor);
            canvas.drawCircle(center, center, radius, mPaint);
            mPaint.setColor(secondColor);
            canvas.drawArc(oval,-90,mProgress,false,mPaint);
        }else {
            mPaint.setColor(secondColor);
            canvas.drawCircle(center, center, radius, mPaint);
            mPaint.setColor(firstColor);
            canvas.drawArc(oval,-90,mProgress,false,mPaint);
        }

    }
}
