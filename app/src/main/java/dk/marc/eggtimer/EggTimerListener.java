package dk.marc.eggtimer;

public interface EggTimerListener {
    void onCountDown(long timeLeft);

    void onEggTimerStopped();
}
