package dk.marc.eggtimer;

import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Locale;

@SuppressWarnings("SpellCheckingInspection")
public class EggTivity extends AppCompatActivity {

    private static final long START_TIME_SOFT_BOILED_IN_MILLIS = 3000;
    private static final long START_TIME_MEDIUM_BOILED_IN_MILLIS = 420000;
    private static final long START_TIME_HARD_BOILED_IN_MILLIS = 600000;

    private long mCurrentTimeSelected;
    private Button mButtonStartStop;
    private TextView mTextViewCountDown;
    private CountDownTimer mCountDownTimer;
    private boolean mTimerRunning;
    private long mTimeLeftInMillis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_egg);
        mButtonStartStop = findViewById(R.id.button_start);
        mTextViewCountDown = findViewById(R.id.timer_value);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putLong("millisLeft", mTimeLeftInMillis);
        outState.putBoolean("timerRunning", mTimerRunning);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        // Get the values needed from saved state
        mTimerRunning = savedInstanceState.getBoolean("timerRunning");
        mTimeLeftInMillis = savedInstanceState.getLong("millisLeft");
        // We need to set the text again
        updateCountDownText();

        // if the timer is running we need to start it again from where it left off
        if (mTimerRunning) {
            startTimer(mTimeLeftInMillis);
            mButtonStartStop.setEnabled(true);
        }
    }
    /**
     * Sets the timer text and enables the button for starting the timer.
     *
     * @param view
     */
    public void onButtonEggSelectedClicked(View view) {
        if (!mTimerRunning) {
            switch (view.getId()) {
                case R.id.button_soft:
                    // Enable start_button
                    mButtonStartStop.setEnabled(true);
                    // Set timer to 5:00
                    mTextViewCountDown.setText(getCurrentSelectedMillisAsString(START_TIME_SOFT_BOILED_IN_MILLIS));
                    mCurrentTimeSelected = START_TIME_SOFT_BOILED_IN_MILLIS;
                    break;
                case R.id.button_medium:
                    mButtonStartStop.setEnabled(true);
                    // set timer to 7:00
                    mTextViewCountDown.setText(getCurrentSelectedMillisAsString(START_TIME_MEDIUM_BOILED_IN_MILLIS));
                    mCurrentTimeSelected = START_TIME_MEDIUM_BOILED_IN_MILLIS;
                    break;
                case R.id.button_hard:
                    mButtonStartStop.setEnabled(true);
                    // set timer to 10:00
                    mTextViewCountDown.setText(getCurrentSelectedMillisAsString(START_TIME_HARD_BOILED_IN_MILLIS));
                    mCurrentTimeSelected = START_TIME_HARD_BOILED_IN_MILLIS;
                    break;
                default:
                    throw new RuntimeException("Unknown button id");
            }
        }
    }


    /**
     * Used for updating the text in the view.
     */
    private void updateCountDownText() {
        int minutes = (int) (mTimeLeftInMillis / 1000) / 60;
        int seconds = (int) (mTimeLeftInMillis / 1000) % 60;
        String timeLeftFormatted = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);

        mTextViewCountDown.setText(timeLeftFormatted);
    }

    private String getCurrentSelectedMillisAsString(long selected) {
        int minutes = (int) (selected / 1000) / 60;
        int seconds = (int) (selected / 1000) % 60;
        return String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);
    }

    /**
     * On clicked method.
     * Starts and stops the timer.
     *
     * @param view
     */
    public void OnButtonStartStopClicked(View view) {
        if (!mTimerRunning)
            startTimer(mCurrentTimeSelected);
        else
            stopTimer();
    }
    /**
     * Starts a timer counting down from given milliseconds
     *
     * @param timeInMillis <i>long</i> milliseconds
     */
    private void startTimer(long timeInMillis) {
        mCountDownTimer = new CountDownTimer(timeInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                mTimeLeftInMillis = millisUntilFinished;
                updateCountDownText();
            }

            @Override
            public void onFinish() {
                mTimerRunning = !mTimerRunning;
                mButtonStartStop.setText(R.string.eggtivity_start);
                mButtonStartStop.setEnabled(false);
                mTextViewCountDown.setText("00:00");
            }
        }.start(); // Because we want to start this timer right now
        mTimerRunning = !mTimerRunning;
        mButtonStartStop.setText(R.string.eggtivity_stop);
    }

    /**
     * Stops the timer, and sets Enabled to false
     */
    private void stopTimer() {
        mCountDownTimer.cancel();
        mTextViewCountDown.setText(R.string.eggtivity_default_time);
        mTimerRunning = !mTimerRunning;
        mButtonStartStop.setEnabled(false);
    }

}
