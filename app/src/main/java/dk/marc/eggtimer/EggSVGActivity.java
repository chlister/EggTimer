package dk.marc.eggtimer;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import static dk.marc.eggtimer.Utility.TimeConverter.getCurrentSelectedMillisAsString;

public class EggSVGActivity extends AppCompatActivity {

    private static final long START_TIME_SOFT_BOILED_IN_MILLIS = 30000; // 5 mins
    private static final long START_TIME_MEDIUM_BOILED_IN_MILLIS = 420000; // 7 mins
    private static final long START_TIME_HARD_BOILED_IN_MILLIS = 600000; // 10 mins

    private long mCurrentTimeSelected;
    private Button mButtonStartStop;
    private TextView mTextViewCountDown;
    private static boolean isRunning;
    private static long timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_egg_svg);
        mButtonStartStop = findViewById(R.id.button_start);
        mTextViewCountDown = findViewById(R.id.timer_value);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        // Save variables which we would want to save
        outState.putLong("millisLeft", timer);
        outState.putBoolean("timerRunning", isRunning);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        // Get the values needed from saved state
        timer = savedInstanceState.getLong("millisLeft");
        System.out.println("Current timer: " + timer);
        isRunning = savedInstanceState.getBoolean("timerRunning");
        System.out.println("Is the application running? " + isRunning);
        // We need to set the text again
        updateCountDownText();

        // if the timer is running we need to start it again from where it left off
        if (isRunning) {
            mButtonStartStop.setEnabled(true);
//            mButtonStartStop.setText(R.string.eggtivity_stop);
            startTimer(timer);
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
     * Used for updating the timer text.
     */
    private void updateCountDownText() {
        String timeLeftFormatted = getCurrentSelectedMillisAsString(timer);

        mTextViewCountDown.setText(timeLeftFormatted);
    }


    /**
     * On clicked method.
     * Starts and stops the timer.
     *
     * @param view View
     */
    public void OnButtonStartStopClicked(View view) {
        if (!isRunning)
            startTimer(mCurrentTimeSelected);
        else
            timer = 0;
    }

    /**
     * Starts a timer counting down from given milliseconds
     *
     * @param timeInMillis <i>long</i> milliseconds
     */
    private void startTimer(long timeInMillis) {

        timer = timeInMillis;
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
                        if (timer <= 0 || !isRunning) {
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
        mButtonStartStop.setText(R.string.eggtivity_start);
        isRunning = !isRunning;
        mButtonStartStop.setEnabled(false);
    }
}
