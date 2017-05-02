package com.example.jin.customtextview;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;

import com.example.progressbar.HorizontalProgressBar;
import com.example.progressbar.RoundProgressBar;

public class MainActivity extends AppCompatActivity {
    private CTextView mTv;
    private HorizontalProgressBar mPb;
    private RoundProgressBar mPb2;


    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            int progress = mPb2.getProgress();
            progress=progress+1;
            mPb2.setProgress(progress);
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
        mPb2 = (RoundProgressBar) findViewById(R.id.pb2);
    }

}
