package com.example.jin.customtextview;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.progressbar.HorizontalProgressBar;

public class MainActivity extends AppCompatActivity {
    private CTextView mTv;
    private HorizontalProgressBar mPb;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            int progress = mPb.getProgress();
            progress=progress+1;
            mPb.setProgress(progress);
            if (progress >= 100) {
                mHandler.removeMessages(1);
            }
            mHandler.sendEmptyMessageDelayed(1, 100);
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mPb = (HorizontalProgressBar) findViewById(R.id.pb);
        mHandler.sendEmptyMessage(1);
        mTv = (CTextView) findViewById(R.id.tv);
        mTv.setTitle("jjj");
        mTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTv.setTitle("ppp");
                v.postInvalidate();
            }
        });
    }

}
