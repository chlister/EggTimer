package dk.marc.eggtimer;

@SuppressWarnings("WeakerAccess")
public class EggTimerPresenterStatePattern implements EggTimerListener {
    private View view;
    private EggState state;
    private long timer;
    private EggTimer eggTimer;

    //region Getters&Setters

    public void setView(View view) {
        this.view = view;
    }

    public EggState getState() {
        return state;
    }

    public long getTimer() {
        return timer;
    }
    //endregion

    public EggTimerPresenterStatePattern(View view) {
        this.view = view;
        timer = 0;
        state = EggState.STOPPED;
    }

    public void stop() {
        state = EggState.STOPPED;
        eggTimer.resetTimer();
        eggTimer.removeListener(this);
        timer = 0;
        onEggTimerStopped();
    }

    public void start(long timeToCook) {
        state = EggState.RUNNING;
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

