package com.example.progressbar;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;

/**
 * Created by jin on 2017/4/24
 */

public class RoundProgressBar extends HorizontalProgressBar {
    private int mRadius = dp2px(30);
    RectF rectF = new RectF();

    public RoundProgressBar(Context context) {
        this(context, null);
        Log.i("round", "1");
    }

    public RoundProgressBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        Log.i("round", "2");
        reachedBarHeight = (int) (unReachedBarHeight * 2.5f);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.RoundProgressBar);
        mRadius = (int) typedArray.getDimension(R.styleable.RoundProgressBar_radius, mRadius);
        textSize = sp2px(14);
        textSize = (int) typedArray.getDimension(R.styleable.HorizontalProgressBar_progress_text_size, sp2px(14));
        typedArray.recycle();
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setDither(true);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        rectF.left = 0;
        rectF.top = 0;
        rectF.right = mRadius * 2;
        rectF.bottom = mRadius * 2;
        int padding=dp2px(10);
        setPadding(padding,padding,padding,padding);
    }

    public RoundProgressBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        Log.i("round", "3");
    }

    @Override
    protected synchronized void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int paintWidth = Math.max(reachedBarHeight, unReachedBarHeight);
        if (heightMode != MeasureSpec.EXACTLY) {
            int exceptWidth = getPaddingTop() + getPaddingBottom() + mRadius * +paintWidth;
            heightMeasureSpec = MeasureSpec.makeMeasureSpec(exceptWidth, MeasureSpec.EXACTLY);
        }
        if (widthMode != MeasureSpec.EXACTLY) {
            int exceptHeight = getPaddingLeft() + getPaddingRight() + mRadius * 2 + paintWidth;
            widthMeasureSpec = MeasureSpec.makeMeasureSpec(exceptHeight, MeasureSpec.EXACTLY);
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected synchronized void onDraw(Canvas canvas) {
        //super.onDraw(canvas);
        String text = getProgress() + "%";
        float textWidth = mPaint.measureText(text);
        float textHeight = (mPaint.descent() + mPaint.ascent()) / 2;
        canvas.save();
        canvas.translate(getPaddingLeft(), getPaddingTop());
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setColor(unReachedColor);
        mPaint.setAntiAlias(true);
        mPaint.setStrokeWidth(unReachedBarHeight);
        canvas.drawCircle(mRadius, mRadius, mRadius, mPaint);
        mPaint.setColor(reachedColor);
        mPaint.setStrokeWidth(reachedBarHeight);
        float sweepAngle = getProgress() * 1.0f / getMax() * 360;
        canvas.drawArc(rectF, -90, sweepAngle, false, mPaint);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(textColor);
        canvas.drawText(text, mRadius - textWidth / 2, mRadius - textHeight, mPaint);
        canvas.restore();
    }
}
