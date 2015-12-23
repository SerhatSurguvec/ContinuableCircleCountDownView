
/**
 * Copyright Serhat Sürgüveç All Rights Reserved.
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.serhatsurguvec.continuablecirclecountdownview;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.RectF;
import android.os.Build;
import android.os.CountDownTimer;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Transformation;

public class ContinuableCircleCountDownView extends View {

    private static final int START_ANGLE_POINT = 270;

    private static final int DEFAULT_SIZE = 200;
    private static final int DEFAULT_RATE = 7;
    private static final int DEFAULT_TIME = 10000;
    private static final int MAX_TIME = 60000;
    private static final int DEFAULT_INTERVAL = 1000;
    private static final int DEFAULT_OUTER_COLOR = Color.parseColor("#02ADC6");
    private static final int DEFAULT_INNER_COLOR = Color.parseColor("#02A5BE");
    private static final int DEFAULT_PROGRESS_COLOR = Color.RED;
    private static final int DEFAULT_TEXT_COLOR = Color.BLACK;
    private static final int DEFAULT_PADDING = 20;

    private RectF rect;
    private RectF rect1;
    private RectF rect2;
    private PointF textPoint;

    private Paint paint;
    private Paint inner;
    private Paint outer;
    private Paint text;

    private int SIZE = DEFAULT_SIZE;
    private int RATE = DEFAULT_RATE;
    private long TIME_MILLIS = DEFAULT_TIME;
    private long INTERVAL_TIME_MILLIS = DEFAULT_INTERVAL;
    private int OUTER_COLOR = DEFAULT_OUTER_COLOR;
    private int INNER_COLOR = DEFAULT_INNER_COLOR;
    private int PROGRESS_COLOR = DEFAULT_PROGRESS_COLOR;
    private int TEXT_COLOR = DEFAULT_TEXT_COLOR;


    private OnCountDownCompletedListener listener;
    private CountDownTimer timer;
    private CircleAngleAnimation angleAnimation;

    private boolean finished = false;
    private boolean stopped = false;
    private boolean started = false;

    private long mMillisUntilFinished;//For Text
    private float angle;
    private int previousLengthOfText = -1;

    public ContinuableCircleCountDownView(Context context) {
        super(context);
        init();
    }

    public ContinuableCircleCountDownView(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.ContinuableCircleCountDownView, 0, 0);

        //Read
        RATE = a.getInt(R.styleable.ContinuableCircleCountDownView_shapeRate, 7);
        INNER_COLOR = a.getColor(R.styleable.ContinuableCircleCountDownView_innerColor, DEFAULT_INNER_COLOR);
        OUTER_COLOR = a.getColor(R.styleable.ContinuableCircleCountDownView_outerColor, DEFAULT_OUTER_COLOR);
        PROGRESS_COLOR = a.getColor(R.styleable.ContinuableCircleCountDownView_progressColor, DEFAULT_PROGRESS_COLOR);
        TEXT_COLOR = a.getColor(R.styleable.ContinuableCircleCountDownView_textColor, DEFAULT_TEXT_COLOR);
        angle = a.getInteger(R.styleable.ContinuableCircleCountDownView_progress, 0);

        a.recycle();

        init();
    }

    public ContinuableCircleCountDownView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.ContinuableCircleCountDownView, 0, 0);

        //Read
        RATE = a.getInt(R.styleable.ContinuableCircleCountDownView_shapeRate, 7);
        INNER_COLOR = a.getColor(R.styleable.ContinuableCircleCountDownView_innerColor, DEFAULT_INNER_COLOR);
        OUTER_COLOR = a.getColor(R.styleable.ContinuableCircleCountDownView_outerColor, DEFAULT_OUTER_COLOR);
        PROGRESS_COLOR = a.getColor(R.styleable.ContinuableCircleCountDownView_progressColor, DEFAULT_PROGRESS_COLOR);
        TEXT_COLOR = a.getColor(R.styleable.ContinuableCircleCountDownView_textColor, DEFAULT_TEXT_COLOR);
        angle = a.getInteger(R.styleable.ContinuableCircleCountDownView_progress, 0);

        a.recycle();

        init();

    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public ContinuableCircleCountDownView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.ContinuableCircleCountDownView, 0, 0);

        //Read
        RATE = a.getInt(R.styleable.ContinuableCircleCountDownView_shapeRate, 7);
        INNER_COLOR = a.getColor(R.styleable.ContinuableCircleCountDownView_innerColor, DEFAULT_INNER_COLOR);
        OUTER_COLOR = a.getColor(R.styleable.ContinuableCircleCountDownView_outerColor, DEFAULT_OUTER_COLOR);
        PROGRESS_COLOR = a.getColor(R.styleable.ContinuableCircleCountDownView_progressColor, DEFAULT_PROGRESS_COLOR);
        TEXT_COLOR = a.getColor(R.styleable.ContinuableCircleCountDownView_textColor, DEFAULT_TEXT_COLOR);
        angle = a.getInteger(R.styleable.ContinuableCircleCountDownView_progress, 0);

        a.recycle();

        init();
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private void init() {

        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(PROGRESS_COLOR);

        inner = new Paint();
        inner.setStyle(Paint.Style.FILL);
        inner.setAntiAlias(true);
        inner.setShadowLayer(10, 6, 6, 0xbf000000);
        inner.setShader(null);
        inner.setColor(INNER_COLOR);

        outer = new Paint();
        outer.setStyle(Paint.Style.STROKE);
        outer.setAntiAlias(true);
        outer.setShadowLayer(10, 6, 6, 0xbf000000);
        outer.setColor(OUTER_COLOR);

        text = new Paint();
        text.setStyle(Paint.Style.FILL);
        text.setAntiAlias(true);
        text.setColor(TEXT_COLOR);

        setLayerType(LAYER_TYPE_SOFTWARE, null);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int w = MeasureSpec.getSize(widthMeasureSpec);
        int h = MeasureSpec.getSize(heightMeasureSpec);
        this.setMeasuredDimension(w, h);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        //make sure square
        if (w > h) {
            SIZE = h;
        } else {
            SIZE = w;
        }

        initMeasurements();
    }

    private void initMeasurements() {

        SIZE -= DEFAULT_PADDING;

        final int strokeWidth = SIZE / RATE;

        //animation
        rect = new RectF(strokeWidth / 2 + strokeWidth + DEFAULT_PADDING, strokeWidth / 2 + strokeWidth + DEFAULT_PADDING, SIZE - (strokeWidth / 2 + strokeWidth), SIZE - (strokeWidth / 2 + strokeWidth));

        //outer
        rect1 = new RectF(strokeWidth / 2 + DEFAULT_PADDING, strokeWidth / 2 + DEFAULT_PADDING, SIZE - strokeWidth / 2, SIZE - strokeWidth / 2);

        //inner
        rect2 = new RectF(strokeWidth * 2f + DEFAULT_PADDING, strokeWidth * 2f + DEFAULT_PADDING, SIZE - strokeWidth * 2f, SIZE - strokeWidth * 2f);

        paint.setStrokeWidth(strokeWidth);
        outer.setStrokeWidth(strokeWidth);
        text.setTextSize(strokeWidth);

        calculateTextPosition();

    }

    /**
     * millis must be lower than 60000 millis and greater than 0
     *
     * @param millis
     */
    public void setTimer(long millis) {

        if (millis > MAX_TIME) {
            throw new IllegalArgumentException("millis must be lower than 60000");
        }

        if (millis < 1) {
            throw new IllegalArgumentException("millis must be greater than 0");
        }

        this.TIME_MILLIS = millis;
        this.mMillisUntilFinished = TIME_MILLIS;
        this.invalidate();

    }

    /**
     * millis must be lower than 60000 millis and greater than 0
     * <p/>
     * interval must be lower than millis and greater than 0
     *
     * @param millis
     * @param interval
     */
    public void setTimer(long millis, long interval) {

        if (millis > MAX_TIME) {
            throw new IllegalArgumentException("millis must be lower than 60000");
        }

        if (millis < 1) {
            throw new IllegalArgumentException("millis must be greater than 0");
        }

        if (interval < 0) {
            throw new IllegalArgumentException("interval must be greater than 0");
        }

        if (interval >= millis) {
            throw new IllegalArgumentException("interval must be lower than millis");
        }

        this.TIME_MILLIS = millis;
        this.INTERVAL_TIME_MILLIS = interval;
        this.mMillisUntilFinished = TIME_MILLIS;
        this.invalidate();
    }

    private void calculateTextPosition() {
        //Text Size Calculations
        RectF bounds = new RectF(rect2);
        // measure text width
        bounds.right = text.measureText(mMillisUntilFinished / 100 + "", 0, (mMillisUntilFinished / 1000 + "").length());
        // measure text height
        bounds.bottom = text.descent() - text.ascent();
        bounds.left += (rect2.width() - bounds.right) / 2.0f;
        bounds.top += (rect2.height() - bounds.bottom) / 2.0f;

        //text
        textPoint = new PointF(bounds.left, bounds.top - text.ascent());
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawArc(rect, START_ANGLE_POINT, angle, false, paint);
        canvas.drawArc(rect1, 0, 360, false, outer);
        canvas.drawArc(rect2, 0, 360, false, inner);

        String secs = mMillisUntilFinished / 1000 + "";
        if (secs.length() != previousLengthOfText) {
            calculateTextPosition();
            previousLengthOfText = secs.length();
        }

        canvas.drawText(secs, textPoint.x, textPoint.y, text);
    }

    public void start() {

        //Already Running..
        if (started)
            return;

        //set bools
        finished = false;
        stopped = false;
        started = true;

        //Start timer
        timer = new CountDownTimer(TIME_MILLIS, INTERVAL_TIME_MILLIS) {
            @Override
            public void onTick(long millisUntilFinished) {
                mMillisUntilFinished = millisUntilFinished;
                invalidate();
                listener.onTick(TIME_MILLIS - mMillisUntilFinished);
            }

            @Override
            public void onFinish() {
                mMillisUntilFinished = 0;
                listener.onCompleted();

                started = false;
                finished = true;
                stopped = false;
            }
        }.start();

        //Start angle animation
        angleAnimation = new CircleAngleAnimation(this, 360);
        angleAnimation.setDuration(TIME_MILLIS);
        angleAnimation.setFillAfter(true);
        this.startAnimation(angleAnimation);

    }

    public void continueE() {

        //Used when stopped.
        if (stopped) {

            //set bools
            started = true;
            finished = false;
            stopped = false;

            //Start timer
            timer = new CountDownTimer(mMillisUntilFinished, INTERVAL_TIME_MILLIS) {
                @Override
                public void onTick(long millisUntilFinished) {
                    mMillisUntilFinished = millisUntilFinished;
                    invalidate();
                    listener.onTick(TIME_MILLIS - mMillisUntilFinished);
                }

                @Override
                public void onFinish() {
                    mMillisUntilFinished = 0;
                    listener.onCompleted();

                    started = false;
                    finished = true;
                    stopped = false;
                }
            }.start();

            //Reuse animation
            angleAnimation.reset();
            angleAnimation.setDuration(mMillisUntilFinished);
            angleAnimation.setFillAfter(true);
            this.startAnimation(angleAnimation);

        } else {
            throw new IllegalStateException("This method must be called after the timer has stopped.");
        }


    }

    public void cancel() {

        //Usable when timer already started or stopped.
        if (started || stopped) {

            started = false;
            finished = false;
            stopped = false;

            this.timer.cancel();
            this.angleAnimation.cancel();
            this.clearAnimation();

            this.angleAnimation = null;
            this.angle = 0;
            this.mMillisUntilFinished = TIME_MILLIS;
            this.invalidate();

        } else {
            throw new IllegalStateException("This method must be called after started or stopped.");
        }

    }

    public void stop() {

        //Usable when timer already started.
        if (started) {

            started = false;
            finished = false;
            stopped = true;

            this.timer.cancel();
            this.clearAnimation();

        } else {

            throw new IllegalStateException("This method must be called after the timer has started ");

        }

    }

    public void setListener(OnCountDownCompletedListener listener) {
        this.listener = listener;
    }

    public float getAngle() {
        return angle;
    }

    protected void setAngle(float angle) {
        this.angle = angle;
    }

    public boolean isFinished() {
        return finished;
    }

    public boolean isStopped() {
        return stopped;
    }

    public boolean isStarted() {
        return started;
    }

    //If you want to animate use this animation class
    public static class CircleAngleAnimation extends Animation {

        private ContinuableCircleCountDownView circle;

        private float oldAngle;
        private float newAngle;

        public CircleAngleAnimation(ContinuableCircleCountDownView circle, int newAngle) {
            this.oldAngle = circle.getAngle();
            this.newAngle = newAngle;
            this.circle = circle;
        }

        @Override
        public void reset() {
            super.reset();
            oldAngle = circle.getAngle();
            newAngle = 360;
        }

        @Override
        protected void applyTransformation(float interpolatedTime, Transformation transformation) {
            //Calculates Angle
            float angle = oldAngle + ((newAngle - oldAngle) * interpolatedTime);
            //Sets Angle
            circle.setAngle(angle);
            //Layer Type Software
            circle.invalidate();
        }


    }

    public interface OnCountDownCompletedListener {
        void onTick(long passedMillis);

        void onCompleted();
    }
}