/*
 * Copyright (C) 2019 The beasonshu Android Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package tk.beason.common.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.OnLifecycleEvent;


import java.util.Calendar;
import java.util.List;
import java.util.concurrent.TimeUnit;

import tk.beason.common.R;

public class VerifyCodeButton extends FrameLayout implements LifecycleObserver{

    private static final String SHARED_PREFERENCES_FILE = "CountDownTextView";
    private static final String SHARED_PREFERENCES_FIELD_TIME = "last_count_time";
    private static final String SHARED_PREFERENCES_FIELD_TIMESTAMP = "last_count_timestamp";
    private static final String SHARED_PREFERENCES_FIELD_INTERVAL = "count_interval";
    private static final String SHARED_PREFERENCES_FIELD_COUNTDOWN = "is_countdown";
    /**
     * 最大时间
     */
    private static final int VERIFY_MAX_TIME = 60;

    /**
     * 进度条
     */
    private ProgressBar mProgressBar;
    /**
     * 时间显示
     */
    private TextView mDisplayView;
    /**
     * 时间监听
     */
    private OnTimingListener mTimingListener;
    private String mTextStr;
    private String mTimingTextStr;

    private final String unId = getUniqueId();

    /**
     * 字体的颜色
     */
    private ColorStateList mTextColor;
    /**
     * 字体的大小
     */
    private int mTextSize;

    /**
     * 倒计时期间是否允许点击
     */
    private boolean mClickable = false;



    /**
     * 页面关闭后倒计时是否保持，再次开启倒计时继续；
     */
    private boolean mCloseKeepCountDown = false;
    /**
     * 是否把时间格式化成时分秒
     */
    private boolean mShowFormatTime = false;
    /**
     * 倒计时间隔
     */
    private TimeUnit mIntervalUnit = TimeUnit.SECONDS;

    private OnCountDownStartListener mOnCountDownStartListener;
    private OnCountDownTickListener mOnCountDownTickListener;
    private OnCountDownFinishListener mOnCountDownFinishListener;


    private CountDownTimer mCountDownTimer;



    public VerifyCodeButton(Context context) {
        this(context, null);
    }

    public VerifyCodeButton(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public VerifyCodeButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setClickable(true);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.VerifyCodeButton);
        int padding = a.getDimensionPixelSize(R.styleable.VerifyCodeButton_android_padding, getDefaultPadding());
        mTextStr = a.getString(R.styleable.VerifyCodeButton_text);
        mTimingTextStr = a.getString(R.styleable.VerifyCodeButton_timingText);
        mTextColor = a.getColorStateList(R.styleable.VerifyCodeButton_textColor);
        mTextSize = a.getDimensionPixelSize(R.styleable.VerifyCodeButton_textSize, getDefaultTextSize());
        a.recycle();

        if (mTextColor == null) {
            mTextColor = getDefaultTextColor();
        }

        if (TextUtils.isEmpty(mTextStr)) {
            mTextStr = getResources().getString(R.string.get_verify_code);
        }

        setPadding(padding, padding, padding, padding);
        mProgressBar = getProgressBar();
        mProgressBar.setVisibility(INVISIBLE);
        addView(mProgressBar);

        mDisplayView = getDisplayView();
        mDisplayView.setVisibility(VISIBLE);
        addView(mDisplayView);
        init(context);
    }

    private void init(Context context) {
        autoBindLifecycle(context);
    }
    /**
     * 控件自动绑定生命周期,宿主可以是activity或者fragment
     */
    private void autoBindLifecycle(Context context) {
        if (context instanceof AppCompatActivity) {
            FragmentActivity activity = (FragmentActivity) context;
            FragmentManager fm = activity.getSupportFragmentManager();
            List<Fragment> fragments = fm.getFragments();
            for (Fragment fragment : fragments) {
                View parent = fragment.getView();
                if (parent != null) {
                    View find = parent.findViewById(getId());
                    if (find == this) {
                        fragment.getLifecycle().addObserver(this);
                        return;
                    }
                }
            }
        }
        if (context instanceof LifecycleOwner) {
            ((LifecycleOwner) context).getLifecycle().addObserver(this);
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    private void onResume() {
        if (mCountDownTimer == null) {
            checkLastCountTimestamp();
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    private void onDestroy() {
        if (mCountDownTimer != null) {
            mCountDownTimer.cancel();
            mCountDownTimer = null;
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        onDestroy();
    }

    /**
     * 计时方案
     *
     * @param time        计时时长
     * @param timeUnit    时间单位
     * @param isCountDown 是否是倒计时，false正向计时
     */
    private void count(final long time, final long offset, final TimeUnit timeUnit, final boolean isCountDown) {
        if (mCountDownTimer != null) {
            mCountDownTimer.cancel();
            mCountDownTimer = null;
        }
        setEnabled(mClickable);
        final long millisInFuture = timeUnit.toMillis(time) + 500;
        long interval = TimeUnit.MILLISECONDS.convert(1, mIntervalUnit);
        if (mCloseKeepCountDown && offset == 0) {
            setLastCountTimestamp(millisInFuture, interval, isCountDown);
        }
        if (offset == 0 && mOnCountDownStartListener != null) {
            mOnCountDownStartListener.onStart();
        }


        mCountDownTimer = new CountDownTimer(millisInFuture, interval) {
            @Override
            public void onTick(long millisUntilFinished) {
                long count = isCountDown ? millisUntilFinished : (millisInFuture - millisUntilFinished + offset);
                long l = timeUnit.convert(count, TimeUnit.MILLISECONDS);
                String showTime;
                if (mShowFormatTime) {
                    showTime = generateTime(count);
                } else {
                    showTime = String.valueOf(l);
                }
                mDisplayView.setText(String.format(mTimingTextStr, showTime));
                if (mOnCountDownTickListener != null) {
                    mOnCountDownTickListener.onTick(l);
                }
            }

            @Override
            public void onFinish() {
                reset();
                mDisplayView.setText(mTextStr);
                if (mOnCountDownFinishListener != null) {
                    mOnCountDownFinishListener.onFinish();
                }
            }
        };
        mCountDownTimer.start();
    }



    /**
     * 持久化
     *
     * @param time        倒计时时长
     * @param interval    倒计时间隔
     * @param isCountDown 是否是倒计时而不是正向计时
     */
    @SuppressLint("ApplySharedPref")
    private void setLastCountTimestamp(long time, long interval, boolean isCountDown) {
        getContext()
                .getSharedPreferences(SHARED_PREFERENCES_FILE, Context.MODE_PRIVATE)
                .edit()
                .putLong(SHARED_PREFERENCES_FIELD_TIME + unId, time)
                .putLong(SHARED_PREFERENCES_FIELD_TIMESTAMP + unId, Calendar.getInstance().getTimeInMillis() + time)
                .putLong(SHARED_PREFERENCES_FIELD_INTERVAL + unId, interval)
                .putBoolean(SHARED_PREFERENCES_FIELD_COUNTDOWN + unId, isCountDown)
                .commit();

    }

    /**
     * 检查持久化参数
     *
     * @return 是否要保持持久化计时
     */
    private boolean checkLastCountTimestamp() {
        SharedPreferences sp = getContext().getSharedPreferences(SHARED_PREFERENCES_FILE, Context.MODE_PRIVATE);
        long lastCountTimestamp = sp.getLong(SHARED_PREFERENCES_FIELD_TIMESTAMP + unId, -1);
        long nowTimeMillis = Calendar.getInstance().getTimeInMillis();
        long diff = lastCountTimestamp - nowTimeMillis;
        if (diff <= 0) {
            return false;
        }
        long time = sp.getLong(SHARED_PREFERENCES_FIELD_TIME + unId, -1);
        long interval = sp.getLong(SHARED_PREFERENCES_FIELD_INTERVAL + unId, -1);
        boolean isCountDown = sp.getBoolean(SHARED_PREFERENCES_FIELD_COUNTDOWN + unId, true);
        for (TimeUnit timeUnit : TimeUnit.values()) {
            long convert = timeUnit.convert(interval, TimeUnit.MILLISECONDS);
            if (convert == 1) {
                long last = timeUnit.convert(diff, TimeUnit.MILLISECONDS);
                long offset = time - diff;
                count(last, offset, timeUnit, isCountDown);
                return true;
            }
        }
        return false;
    }

    private String getUniqueId(){
        return  getContext().getClass().getName().replace(".", "_") + "_" + getId();
    }


    /**
     * 将毫秒转时分秒
     */
    @SuppressLint("DefaultLocale")
    public static String generateTime(long time) {
        String format;
        int totalSeconds = (int) (time / 1000);
        int seconds = totalSeconds % 60;
        int minutes = (totalSeconds / 60) % 60;
        int hours = totalSeconds / 3600;
        if (hours > 0) {
            format = String.format("%02d时%02d分%02d秒", hours, minutes, seconds);
        } else if (minutes > 0) {
            format = String.format("%02d分%02d秒", minutes, seconds);
        } else {
            format = String.format("%2d秒", seconds);
        }
        return format;
    }


    /**
     * 显示Loading
     */
    public void showLoading() {
        // 如果当前是空闲状态
        if (mTimingListener != null) {
            mTimingListener.onSending();
        }
        mProgressBar.setVisibility(VISIBLE);
        mDisplayView.setVisibility(INVISIBLE);
        setClickable(false);
    }

    /**
     * 开始计时
     */
    public void startTiming() {
        startTiming(VERIFY_MAX_TIME);
    }

    /**
     * 开始计时
     */
    public void startTiming(int currentTime) {
        setClickable(false);
        mProgressBar.setVisibility(INVISIBLE);
        mDisplayView.setVisibility(VISIBLE);
        startCountDown(currentTime);
    }


    /**
     * 默认按秒倒计时
     *
     * @param second 多少秒
     */
    public void startCountDown(long second) {
        startCountDown(second, TimeUnit.SECONDS);
    }

    public void startCountDown(int second){
        startCountDown(second, TimeUnit.SECONDS);
    }

    public void startCountDown(long time, final TimeUnit timeUnit) {
        if (mCloseKeepCountDown && checkLastCountTimestamp()) {
            return;
        }
        count(time, 0, timeUnit, true);
    }

    /**
     * 重新计时
     */

    public void reset() {
        setEnabled(true);
        setClickable(true);
        mProgressBar.setVisibility(INVISIBLE);
        mDisplayView.setVisibility(VISIBLE);
        onDestroy();
    }

    /**
     * 获取时间显示
     */
    private TextView getDisplayView() {
        final int padding = getDefaultPadding();
        TextView textView = new TextView(getContext());
        textView.setIncludeFontPadding(false);
        textView.setLayoutParams(getDefaultParam());
        textView.setGravity(Gravity.CENTER);
        textView.setText(mTextStr);
        textView.setTextColor(mTextColor);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, mTextSize);
        textView.setPadding(padding * 2, padding, padding * 2, padding);
        return textView;
    }

    /**
     * 获取进度条
     */
    private ProgressBar getProgressBar() {
        int padding = getDefaultPadding();
        ProgressBar progressBar = new ProgressBar(getContext(), null, android.R.attr.progressBarStyleSmall);
        progressBar.setLayoutParams(getDefaultParam());
        progressBar.setPadding(padding, padding, padding, padding);
        return progressBar;
    }

    /**
     * 获取默认的布局参数
     */
    private LayoutParams getDefaultParam() {
        return new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
    }

    /**
     * 默认padding
     */
    private int getDefaultPadding() {
        return getContext().getResources().getDimensionPixelSize(R.dimen.dp_3);
    }

    /**
     * 默认边距
     */
    private int getDefaultTextSize() {
        return getContext().getResources().getDimensionPixelSize(R.dimen.H_title);
    }
    public VerifyCodeButton setCloseKeepCountDown(boolean mCloseKeepCountDown) {
        this.mCloseKeepCountDown = mCloseKeepCountDown;
        return this;
    }



    /**
     * 获取默认的颜色值
     */
    private ColorStateList getDefaultTextColor() {
        return getContext().getResources().getColorStateList(R.color.text_grey_light_normal);
    }

    /**
     * 添加时间监听
     */
    public void setOnTimingListener(OnTimingListener listener) {
        mTimingListener = listener;
    }

    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        mDisplayView.setEnabled(enabled);
    }

    /**
     * 是否正在倒计时
     */
    public boolean isTiming() {
        return mDisplayView.isEnabled();
    }





    /**
     * 时间监听
     */
    public interface OnTimingListener {
        /**
         * 正在发送验证码
         */
        void onSending();

        /**
         * 正在倒计时
         *
         * @param time 当前时间
         */
        void onTiming(int time);

        /**
         * 倒计时结束
         */
        void onTimingEnd();
    }






    public interface OnCountDownStartListener {
        /**
         * 计时开始回调;反序列化时不会回调
         */
        void onStart();
    }

    public interface OnCountDownTickListener {
        /**
         * 计时回调
         *
         * @param untilFinished 剩余时间,单位为开始计时传入的单位
         */
        void onTick(long untilFinished);
    }

    public interface OnCountDownFinishListener {
        /**
         * 计时结束回调
         */
        void onFinish();
    }

}
