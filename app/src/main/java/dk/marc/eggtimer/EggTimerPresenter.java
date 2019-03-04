package dk.marc.eggtimer;

@SuppressWarnings("WeakerAccess")
public class EggTimerPresenter implements EggTimerListener {
    private View view;
    private boolean isRunning;
    private long timer;
    private EggTimer eggTimer;

    //region Getters&Setters
    public boolean getIsRunning() {
        return isRunning;
    }

    public long getTimer() {
        return timer;
    }
    //endregion


    public EggTimerPresenter(View view) {
        this.view = view;
        isRunning = false;
        timer = 0;
    }

    public void stop() {
        eggTimer.resetTimer();
        eggTimer.removeListener(this);
        isRunning = false;
        timer = 0;
        onEggTimerStopped();
    }

    public void start(long timeToCook) {
        isRunning = true;
        timer = timeToCook;
        eggTimer = new EggTimer(timeToCook);
        eggTimer.addListener(this);
        eggTimer.start();
    }


    @Override
    public void onCountDown(final long timeLeft) {
        view.onCountDown(timeLeft);
    }

    @Override
    public void onEggTimerStopped() {
        view.onEggTimerStopped();
    }

    public interface View {
        void onCountDown(long time);

        void onEggTimerStopped();
    }
}

