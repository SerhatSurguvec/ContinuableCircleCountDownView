package com.serhatsurguvec.continuablecirclecountdownviewdemo;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.serhatsurguvec.continuablecirclecountdownview.ContinuableCircleCountDownView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    private ContinuableCircleCountDownView countDownView;

    private Button btnCancel;
    private Button btnStart;
    private Button btnStop;
    private Button btnContinue;
    private Button startFromButton;
    private Button changeShapeRateButton;
    private EditText startFromEditText;
    private EditText shapeRateEditText;
    private CheckBox startFromAnimateCb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        countDownView = (ContinuableCircleCountDownView) findViewById(R.id.circleCountDownView);

        btnCancel = (Button) findViewById(R.id.btnCancel);
        btnStart = (Button) findViewById(R.id.btnStart);
        btnStop = (Button) findViewById(R.id.btnStop);
        btnContinue = (Button) findViewById(R.id.btnContinue);
        startFromButton = (Button) findViewById(R.id.startFromButton);
        changeShapeRateButton = (Button) findViewById(R.id.changeShapeRateButton);
        startFromEditText = (EditText) findViewById(R.id.startFromEditText);
        shapeRateEditText = (EditText) findViewById(R.id.shapeRateEditText);
        startFromAnimateCb = (CheckBox) findViewById(R.id.startFromAnimateCb);

        countDownView.setTimer(10000);//60secs

        countDownView.setListener(new ContinuableCircleCountDownView.OnCountDownCompletedListener() {
            @Override
            public void onTick(long passedMillis) {
                Log.w(TAG, "Tick." + passedMillis);
            }

            @Override
            public void onCompleted() {
                Log.w(TAG, "Completed.");
                btnCancel.setEnabled(true);
                btnContinue.setEnabled(false);
                btnStop.setEnabled(false);
                btnStart.setEnabled(false);
                startFromButton.setEnabled(false);

            }
        });

        init();
    }

    private void init() {

        btnCancel.setEnabled(false);
        btnContinue.setEnabled(false);
        btnStop.setEnabled(false);
        btnStart.setEnabled(true);
        startFromButton.setEnabled(true);

        changeShapeRateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                countDownView.setRATE(Integer.parseInt(shapeRateEditText.getText().toString()));
            }
        });

    }

    public void onClick(View view) {

        int id = view.getId();

        switch (id) {
            case R.id.btnStart:
                btnCancel.setEnabled(true);
                btnContinue.setEnabled(false);
                btnStop.setEnabled(true);
                btnStart.setEnabled(false);
                startFromButton.setEnabled(false);
                countDownView.start();
                break;

            case R.id.btnStop:
                btnCancel.setEnabled(true);
                btnContinue.setEnabled(true);
                btnStop.setEnabled(false);
                btnStart.setEnabled(false);
                startFromButton.setEnabled(false);
                countDownView.stop();
                break;

            case R.id.btnContinue:
                btnCancel.setEnabled(true);
                btnContinue.setEnabled(false);
                btnStop.setEnabled(true);
                btnStart.setEnabled(false);
                startFromButton.setEnabled(false);
                countDownView.continueE();
                break;

            case R.id.btnCancel:
                btnCancel.setEnabled(false);
                btnContinue.setEnabled(false);
                btnStop.setEnabled(false);
                btnStart.setEnabled(true);
                startFromButton.setEnabled(true);
                countDownView.cancel();
                break;

            case R.id.startFromButton:
                btnCancel.setEnabled(true);
                btnContinue.setEnabled(false);
                btnStop.setEnabled(true);
                btnStart.setEnabled(false);
                startFromButton.setEnabled(false);
                String str = startFromEditText.getText().toString();
                countDownView.startFrom(Float.parseFloat(str), startFromAnimateCb.isChecked());
                break;

            default:
                btnCancel.setEnabled(false);
                btnContinue.setEnabled(false);
                btnStop.setEnabled(false);
                btnStart.setEnabled(true);
                startFromButton.setEnabled(true);
                countDownView.cancel();
                break;
        }

    }

    public void onColorClick(View view) {

        int id = view.getId();

        switch (id) {
            case R.id.innerColorButton:
                countDownView.setINNER_COLOR(getRandomColor());
                break;
            case R.id.outerColorButton:
                countDownView.setOUTER_COLOR(getRandomColor());
                break;
            case R.id.progressColorButton:
                countDownView.setPROGRESS_COLOR(getRandomColor());
                break;
            case R.id.textColorButton:
                countDownView.setTEXT_COLOR(getRandomColor());
                break;
            default:
                break;

        }

    }

    public static int getRandomColor() {
        Random rnd = new Random();
        return Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
    }
}
