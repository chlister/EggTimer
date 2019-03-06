package dk.marc.eggtimer;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import static dk.marc.eggtimer.utility.TimeConverter.getCurrentSelectedMillisAsString;

public class EggViewActivity extends AppCompatActivity implements EggTimerListener {

    private static final long START_TIME_SOFT_BOILED_IN_MILLIS = 300000; // 5 mins
    private static final long START_TIME_MEDIUM_BOILED_IN_MILLIS = 420000; // 7 mins
    private static final long START_TIME_HARD_BOILED_IN_MILLIS = 600000; // 10 mins

    private EggTimer eggTimer;
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
        // Save variables which we would want to save
        outState.putLong("millisLeft", timer);
        outState.putBoolean("timerRunning", isRunning);
        eggTimer.resetTimer();
        if (eggTimer != null) {
            stopTimer();
        }
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        // Get the values needed from saved state
        timer = savedInstanceState.getLong("millisLeft");
        isRunning = savedInstanceState.getBoolean("timerRunning");
        // if the timer is running we need to start it again from where it left off
        if (isRunning) {
            mButtonStartStop.setEnabled(true);
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
    private void updateCountDownText(long timeLeft) {
        String timeLeftFormatted = getCurrentSelectedMillisAsString(timeLeft);

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
            eggTimer.resetTimer();
    }

    /**
     * Starts a timer counting down from given milliseconds
     *
     * @param timeInMillis <i>long</i> milliseconds
     */
    private void startTimer(long timeInMillis) {
        isRunning = true;
        timer = timeInMillis;
        mButtonStartStop.setText(R.string.eggtivity_stop);
        eggTimer = new EggTimer(timeInMillis);
        eggTimer.addListener(this);
        eggTimer.start();
    }

    /**
     * When this method is called, the button is renamed to "Start" and enabled set to false
     * the boolean isRunning is set to false
     */
    private void stopTimer() {
        eggTimer.removeListener(this);
        mTextViewCountDown.setText(R.string.eggtivity_default_time);
        mButtonStartStop.setText(R.string.eggtivity_start);
        isRunning = false;
        mButtonStartStop.setEnabled(false);
    }

    @Override
    public void onCountDown(final long timeLeft) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                timer = timeLeft;
                updateCountDownText(timeLeft);
            }
        });
    }

    @Override
    public void onEggTimerStopped() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                stopTimer();
            }
        });
    }
}
