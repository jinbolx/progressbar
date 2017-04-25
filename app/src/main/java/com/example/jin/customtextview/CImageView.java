package com.example.jin.customtextview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;

/**
 * Created by jin on 2017/4/20
 */

public class CImageView extends View {
    private String mTitle;
    private int mTitleSize;
    private int mTitleColor;
    private Bitmap mImage;
    private int mImageScale;
    private Rect rect;
    private Rect mBound;
    private Paint mPaint;

    public String getmTitle() {
        return mTitle;
    }

    public void setmTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public int getmTitleSize() {
        return mTitleSize;
    }

    public void setmTitleSize(int mTitleSize) {
        this.mTitleSize = mTitleSize;
    }

    public int getmTitleColor() {
        return mTitleColor;
    }

    public void setmTitleColor(int mTitleColor) {
        this.mTitleColor = mTitleColor;
    }

    public Bitmap getmImage() {
        return mImage;
    }

    public void setmImage(Bitmap mImage) {
        this.mImage = mImage;
    }

    public int getmImageScale() {
        return mImageScale;
    }

    public void setmImageScale(int mImageScale) {
        this.mImageScale = mImageScale;
    }

    public CImageView(Context context) {
        this(context, null);

    }

    public CImageView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CImageView(Context context, @Nullable AttributeSet attrs, int deStyleAttr) {
        super(context, attrs, deStyleAttr);
        mTitleSize = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 16, getResources().getDisplayMetrics());
        mTitleColor = Color.BLACK;
        mImageScale = 0;
        TypedArray array = context.getTheme().obtainStyledAttributes(attrs, R.styleable.CImageView, deStyleAttr, 0);
        int num = array.getIndexCount();
        for (int i = 0; i < num; i++) {
            int att = array.getIndex(i);
            switch (att) {
                case R.styleable.CImageView_titleText:
                    mTitle = array.getString(att);
                    break;
                case R.styleable.CImageView_titleSize:
                    mTitleSize = array.getDimensionPixelSize(att, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 16, getResources().getDisplayMetrics()));
                    break;
                case R.styleable.CImageView_titleColor:
                    mTitleColor = array.getColor(att, Color.BLACK);
                    break;
                case R.styleable.CImageView_image:
                    mImage = BitmapFactory.decodeResource(getResources(), array.getResourceId(att, 0));
                    Log.i("mimage", "m" + mImage);
                    break;
                case R.styleable.CImageView_imageScaleType:
                    mImageScale = array.getInt(att, 0);
                    break;
            }
        }
        array.recycle();
        rect = new Rect();
        mPaint = new Paint();
        mBound = new Rect();
        mPaint.setTextSize(mTitleSize);
        mPaint.getTextBounds(getmTitle(), 0, mTitle.length(), mBound);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int withSize = MeasureSpec.getSize(widthMeasureSpec);
        int width;
        int height;

        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        if (widthMode == MeasureSpec.EXACTLY) {
            width = withSize;
        } else {
            int desiredByImg = getPaddingLeft() + mImage.getWidth() + getPaddingRight();
            int desiredByText = getPaddingLeft() + mBound.width() + getPaddingRight();
            width = Math.max(desiredByImg, desiredByText);
        }
        if (heightMode == MeasureSpec.EXACTLY) {
            height = heightSize;
        } else {
            int desiredByImgHeight = getPaddingLeft() + mImage.getWidth() + getPaddingRight();
            int desiredByTextHeight = getPaddingTop() + mBound.width() + getPaddingBottom();
            height = Math.max(desiredByImgHeight, desiredByTextHeight);
        }
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //  super.onDraw(canvas);
        mPaint.setStrokeWidth(4);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(Color.CYAN);
        canvas.drawRect(0, 0, getMeasuredWidth(), getMeasuredHeight(), mPaint);
        mPaint.setColor(mTitleColor);
        mPaint.setStyle(Paint.Style.FILL);
        if (mImageScale == 1) {
            rect.left = getPaddingLeft();
            rect.right = getWidth() - getPaddingRight();
            rect.top = getPaddingTop();
            rect.bottom = getHeight() - getPaddingBottom();
        } else {
            rect.left = getWidth() / 2 - mImage.getWidth() / 2;
            rect.right = getWidth() / 2 + mImage.getWidth() / 2;
            rect.top = (getHeight() -mBound.height())/ 2 - mImage.getHeight() / 2;
            rect.bottom = (getHeight()-mBound.height()) / 2 + mImage.getHeight() / 2;

        }
        canvas.drawBitmap(mImage, null, rect, mPaint);
        canvas.drawText(mTitle, getWidth() / 2 - mBound.width() / 2, getHeight()-getPaddingBottom()/2, mPaint);
    }
}
