package dk.marc.eggtimer;

import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Locale;

@SuppressWarnings("SpellCheckingInspection")
public class EggTivity extends AppCompatActivity {

    private static final long START_TIME_SOFT_BOILED_IN_MILLIS = 300000; // 5 mins
    private static final long START_TIME_MEDIUM_BOILED_IN_MILLIS = 420000; // 7 mins
    private static final long START_TIME_HARD_BOILED_IN_MILLIS = 600000; // 10 mins

    private long mCurrentTimeSelected;
    private Button mButtonStartStop;
    private TextView mTextViewCountDown;
    private CountDownTimer mCountDownTimer;
    private boolean isRunning;
    private long timer;

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

        outState.putLong("millisLeft", timer);
        outState.putBoolean("timerRunning", isRunning);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        // Get the values needed from saved state
        isRunning = savedInstanceState.getBoolean("timerRunning");
        timer = savedInstanceState.getLong("millisLeft");
        // We need to set the text again
        updateCountDownText();

        // if the timer is running we need to start it again from where it left off
        if (isRunning) {
            startTimer(timer);
            mButtonStartStop.setEnabled(true);
        }
    }

    /**
     * Sets the timer text and enables the button for starting the timer.
     *
     * @param view View
     */
    public void onButtonEggSelectedClicked(View view) {
        if (!isRunning) {
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
        int minutes = (int) (timer / 1000) / 60;
        int seconds = (int) (timer / 1000) % 60;
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
        if (!isRunning)
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

        timer = timeInMillis;
        // TODO: Update buttontext
        mButtonStartStop.setText(R.string.eggtivity_stop);
        Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        timer -= 1000;
                        updateCountDownText();

                        if (timer <= 0) {
                            stopTimer();
                        } else {
                            handler.postDelayed(this, 1000);
                        }
                    }
                }, 1000);
            }
        });
        isRunning = !isRunning;
        mButtonStartStop.setText(R.string.eggtivity_stop);
    }

    /**
     * When this method is called, the button is renamed to "Start" and enabled set to false
     * the boolean isRunning is set to false
     */
    private void stopTimer() {
        mTextViewCountDown.setText(R.string.eggtivity_default_time);
        isRunning = !isRunning;
        mButtonStartStop.setEnabled(false);
    }

}
