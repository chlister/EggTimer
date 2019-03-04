package dk.marc.eggtimer;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONObject;

import static dk.marc.eggtimer.Utility.TimeConverter.getCurrentSelectedMillisAsString;

public class EggViewMVP extends AppCompatActivity implements EggTimerPresenter.View {

    private static final long START_TIME_SOFT_BOILED_IN_MILLIS = 300000; // 5 mins
    private static final long START_TIME_MEDIUM_BOILED_IN_MILLIS = 420000; // 7 mins
    private static final long START_TIME_HARD_BOILED_IN_MILLIS = 600000; // 10 mins

    private static EggTimerPresenter presenter;
    private long mCurrentTimeSelected;
    private Button mButtonStartStop;
    private TextView mTextViewCountDown;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_egg_svg);
        mButtonStartStop = findViewById(R.id.button_start);
        mTextViewCountDown = findViewById(R.id.timer_value);
        if (presenter == null)
            presenter = new EggTimerPresenter(this);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        // Save variables which we would want to save
        super.onSaveInstanceState(outState);
        // Save the egg state
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        // TODO: restore the view
        presenter.setView(this);
    }

    /**
     * Sets the timer text and enables the button for starting the timer.
     *
     * @param view View
     */
    public void onButtonEggSelectedClicked(View view) {
        if (!(presenter.getState() == EggState.RUNNING || presenter.getState() == EggState.PAUSED)) {
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
        if (presenter.getState() != EggState.RUNNING) {
            startTimer(mCurrentTimeSelected);
        } else
            presenter.stop();
    }

    /**
     * Starts a timer counting down from given milliseconds
     *
     * @param timeInMillis <i>long</i> milliseconds
     */
    private void startTimer(long timeInMillis) {
        mButtonStartStop.setText(R.string.eggtivity_stop);
        presenter.start(timeInMillis);
    }

    /**
     * When this method is called, the button is renamed to "Start" and enabled set to false
     * the boolean isRunning is set to false
     */
    private void stopTimer() {
        mCurrentTimeSelected = 0;
        mTextViewCountDown.setText(R.string.eggtivity_default_time);
        mButtonStartStop.setText(R.string.eggtivity_start);
        mButtonStartStop.setEnabled(false);
    }

    @Override
    public void onCountDown(final long time) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                updateCountDownText(time);
            }
        });
    }

    @Override
    public void onEggTimerStopped() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                System.out.println("Hello from this thread " + Thread.currentThread().getName());
                stopTimer();
            }
        });
    }
}
