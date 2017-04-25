package com.example.jin.customtextview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

/**
 * Created by jin on 2017/4/19 
 */

public class CTextView extends View {
    private String title;
    private int titleColor;
    private int titleSize;
    private Paint mPaint;
    private Rect mBound;
    public ChangeTextListener textListener;
    public String getTitle() {
        return title;
    }
    public void setTextListener(ChangeTextListener listener){
        this.textListener=listener;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    public int getTitleColor() {
        return titleColor;
    }

    public void setTitleColor(int titleColor) {
        this.titleColor = titleColor;
    }

    public int getTitleSize() {
        return titleSize;
    }

    public void setTitleSize(int titleSize) {
        this.titleSize = titleSize;
    }

    public CTextView(Context context) {
        this(context,null);
    }

    public CTextView(Context context, @Nullable AttributeSet attrs) {
        this(context,attrs,0);
    }

    public CTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray=context.getTheme().obtainStyledAttributes(attrs,R.styleable.CTextView,defStyleAttr,0);
        int n=typedArray.getIndexCount();
        int m=typedArray.length();
        Log.i("typedArray","m :" +m+ " n: "+n);
        for (int i = 0; i < n; i++) {
            int attr=typedArray.getIndex(i);
            switch (attr){
                case R.styleable.CTextView_titleColor:
                    titleColor=typedArray.getColor(attr, Color.BLACK);
                    break;
                case R.styleable.CTextView_titleSize:
                    titleSize=typedArray.getDimensionPixelSize(attr, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,16,getResources().getDisplayMetrics()));
                    break;
                case R.styleable.CTextView_titleText:
                    title=typedArray.getString(attr);
                    break;
                case R.styleable.CTextView_titlePercent:
                    break;
            }
        }
        typedArray.recycle();
        mPaint=new Paint();
        mPaint.setColor(titleColor);
        mPaint.setTextSize(titleSize);
        mBound=new Rect();
        mPaint.getTextBounds(title,0,title.length(),mBound);
        this.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (textListener!=null) {
                    textListener.change();
                }
                //title=randomText();
                //postInvalidate();
            }
        });
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //suer.onDraw(canvas);
        mPaint.setColor(Color.YELLOW);

        canvas.drawRect(0,0,getMeasuredWidth(),getMeasuredHeight(),mPaint);
        Log.i("length","width "+getWidth()+" m: "+getMeasuredWidth());
        mPaint.setColor(titleColor);
        //绘制原点是文字的左底部
        canvas.drawText(title,getWidth()/2-mBound.width()/2,getHeight()/2+mBound.height()/2,mPaint);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthMode=MeasureSpec.getMode(widthMeasureSpec);
        int widthSize=MeasureSpec.getSize(widthMeasureSpec);
        int heightMode=MeasureSpec.getMode(heightMeasureSpec);
        int heightSize=MeasureSpec.getSize(heightMeasureSpec);
        int width;
        int height;
        if (widthMode == MeasureSpec.EXACTLY) {
            width=widthSize;
        }else {
           // mPaint.setTextSize(titleSize);
            mPaint.getTextBounds(title,0,title.length(),mBound);
            float textWidth=mBound.width();
            width= (int) (getPaddingLeft()+textWidth+getPaddingRight());
        }
        if (heightMode == MeasureSpec.EXACTLY) {
            height=heightSize;
        }else {
          // mPaint.setTextSize(titleSize);
            mPaint.getTextBounds(title,0,title.length(),mBound);
            float textHeight=mBound.height();
            height= (int) (getPaddingTop()+textHeight+getPaddingBottom());
        }
        setMeasuredDimension(width,height);
    }
    private String randomText(){
        Random random=new Random();
        Set<Integer> set=new HashSet<>();
        while (set.size()<4){
            int randomInt =random.nextInt(10);
            set.add(randomInt);
        }
        StringBuilder stringBuffer=new StringBuilder();
        for (Integer i :
                set) {
            stringBuffer.append("").append(i);
        }
        return stringBuffer.toString();
    }
    public interface ChangeTextListener{
        public void change();
    }
    public void changeText(){
        postInvalidate();
    }
}
