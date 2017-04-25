package com.example.progressbar;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.widget.ProgressBar;

/**
 * Created by jin on 2017/4/21
 */

public class HorizontalProgressBar extends ProgressBar {
    private final int DEFAULT_UNREACHED_COLOR = 0xFFeeFF;
    private final int DEFAULT_REACHED_COLOR = 0xFFeeFF;
    private final int DEFAULT_UNREACHED_BAR_HEIGHT = 10;
    private final int DEFAULT_REACHED_BAR_HEIGHT = 10;
    private final int DEFAULT_TEXT_SIZE = 16;
    private final int DEFAULT_TEXT_COLOR = 0xff0000;
    private final int DEFAULT_TEXT_OFFSET = 20;
    private final int DEFAULT_TEXT_VISIBILITY = 1;

    private int unReachedColor = DEFAULT_UNREACHED_COLOR;
    private int reachedColor = DEFAULT_REACHED_COLOR;
    private int unReachedBarHeight = DEFAULT_UNREACHED_BAR_HEIGHT;
    private int reachedBarHeight = DEFAULT_REACHED_BAR_HEIGHT;
    private int textSize = DEFAULT_TEXT_SIZE;
    private int textOffset = DEFAULT_TEXT_OFFSET;
    private int text_visible = DEFAULT_TEXT_VISIBILITY;
    private int textColor = DEFAULT_TEXT_COLOR;
    private Paint mPaint = new Paint();
    private boolean drawText = true;
    protected int mRealWidth;
    public HorizontalProgressBar(Context context) {
       this(context,null);
    }

    public HorizontalProgressBar(Context context, AttributeSet attrs) {
       this(context,attrs,0);
    }

    public HorizontalProgressBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        setHorizontalScrollBarEnabled(true);
        obtainStyledAttributes(context,attrs,defStyleAttr);
        mPaint.setTextSize(textSize);
        mPaint.setColor(textColor);
    }

    private void obtainStyledAttributes(Context context, AttributeSet attrs, int defStyleAttr) {
        TypedArray typedArray = context.getTheme().obtainStyledAttributes(attrs,R.styleable.HorizontalProgressBar,defStyleAttr,0);
        unReachedColor = typedArray.getColor(R.styleable.HorizontalProgressBar_progress_unreached_color, Color.RED);
        reachedColor = typedArray.getColor(R.styleable.HorizontalProgressBar_progress_reached_color, Color.BLUE);
        unReachedBarHeight = typedArray.getDimensionPixelSize(R.styleable.HorizontalProgressBar_progress_unreached_bar_height, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 16, getResources().getDisplayMetrics()));
        reachedBarHeight = typedArray.getDimensionPixelSize(R.styleable.HorizontalProgressBar_progress_reached_bar_height, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 16, getResources().getDisplayMetrics()));
        text_visible = typedArray.getInt(R.styleable.HorizontalProgressBar_progress_text_visible, 1);
        textOffset = typedArray.getDimensionPixelSize(R.styleable.HorizontalProgressBar_progress_text_offset, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 16, getResources().getDisplayMetrics()));
        textSize = typedArray.getDimensionPixelSize(R.styleable.HorizontalProgressBar_progress_text_size, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 18, getResources().getDisplayMetrics()));
        textColor = typedArray.getColor(R.styleable.HorizontalProgressBar_progress_text_color, Color.BLACK);
        drawText = text_visible != 0;
        typedArray.recycle();
    }

    @Override
    protected synchronized void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        if (heightMode != MeasureSpec.EXACTLY) {
            float textHeight = mPaint.descent() + mPaint.ascent();
            int exceptHeight = (int) (getPaddingTop() + getPaddingBottom() +
                    Math.max(Math.abs(textHeight), Math.max(unReachedBarHeight, reachedBarHeight)));
            heightMeasureSpec = MeasureSpec.makeMeasureSpec(exceptHeight, MeasureSpec.EXACTLY);
            setMeasuredDimension(widthMeasureSpec, heightMeasureSpec);
        }
    }

    @Override
    protected synchronized void onDraw(Canvas canvas) {
        canvas.save();
        canvas.translate(getPaddingLeft(),getHeight()/2);
        boolean noNeedBg=false;
        float radio=getProgress()*1.0f/getMax();
        float progressPosX=getWidth()*radio;
        String text=getProgress()+"%";
        float textWidth=mPaint.measureText(text);
        float textHeight=(mPaint.descent()+mPaint.ascent())/2;
        if (progressPosX+textWidth>getWidth()) {
            progressPosX=getWidth()-textWidth;
            noNeedBg=true;
        }
        float endX=progressPosX-textOffset/2;
        if (endX >0) {
            mPaint.setColor(reachedColor);
            mPaint.setStrokeWidth(reachedBarHeight);
            canvas.drawLine(0,0,endX,0,mPaint);
        }
        if (drawText) {
            mPaint.setColor(textColor);
            canvas.drawText(text,progressPosX,-textHeight,mPaint);
        }
        if (!noNeedBg) {
            float start=progressPosX+textOffset/2+textWidth;
            mPaint.setColor(unReachedColor);
            mPaint.setStrokeWidth(unReachedBarHeight);
            canvas.drawLine(start,0,getWidth(),0,mPaint);
        }
        canvas.restore();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mRealWidth=w-getPaddingLeft()-getPaddingRight();
    }
}

