package com.serhatsurguvec.continuablecirclecountdownviewdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.serhatsurguvec.continuablecirclecountdownview.ContinuableCircleCountDownView;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    private ContinuableCircleCountDownView countDownView;

    private Button btnCancel;
    private Button btnStart;
    private Button btnStop;
    private Button btnContinue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        countDownView = (ContinuableCircleCountDownView) findViewById(R.id.circleCountDownView);

        btnCancel = (Button) findViewById(R.id.btnCancel);
        btnStart = (Button) findViewById(R.id.btnStart);
        btnStop = (Button) findViewById(R.id.btnStop);
        btnContinue = (Button) findViewById(R.id.btnContinue);

        countDownView.setTimer(60000);//30secs

        countDownView.setListener(new ContinuableCircleCountDownView.OnCountDownCompletedListener() {
            @Override
            public void onTick(long passedMillis) {
                Log.w(TAG, "Tick." + passedMillis);
            }

            @Override
            public void onCompleted() {
                Log.w(TAG, "Completed.");
            }
        });

        init();
    }

    private void init() {

        btnCancel.setEnabled(false);
        btnContinue.setEnabled(false);
        btnStop.setEnabled(false);
        btnStart.setEnabled(true);

    }

    public void onClick(View view) {

        int id = view.getId();

        switch (id) {
            case R.id.btnStart:
                btnCancel.setEnabled(true);
                btnContinue.setEnabled(false);
                btnStop.setEnabled(true);
                btnStart.setEnabled(false);

                countDownView.start();
                break;

            case R.id.btnStop:
                btnCancel.setEnabled(true);
                btnContinue.setEnabled(true);
                btnStop.setEnabled(false);
                btnStart.setEnabled(false);

                countDownView.stop();
                break;

            case R.id.btnContinue:
                btnCancel.setEnabled(true);
                btnContinue.setEnabled(false);
                btnStop.setEnabled(true);
                btnStart.setEnabled(false);

                countDownView.continueE();
                break;

            case R.id.btnCancel:
                btnCancel.setEnabled(false);
                btnContinue.setEnabled(false);
                btnStop.setEnabled(false);
                btnStart.setEnabled(true);

                countDownView.cancel();
                break;

            default:
                btnCancel.setEnabled(false);
                btnContinue.setEnabled(false);
                btnStop.setEnabled(false);
                btnStart.setEnabled(true);

                countDownView.cancel();
                break;
        }

    }
}
