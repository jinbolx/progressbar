package com.example.progressbar;

import android.content.Context;
import android.util.AttributeSet;

/**
 * Created by jin on 2017/4/24
 */

public class RoundProgressBar extends HorizontalProgressBar {
    public RoundProgressBar(Context context) {
        this(context,null);
    }

    public RoundProgressBar(Context context, AttributeSet attrs) {
        this(context,attrs,0);
    }

    public RoundProgressBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
}
