package dk.marc.eggtimer.EggStates;

public interface EggTimerStates {
    void start(EggTimerContext context,long timeToCount);
    void stop(EggTimerContext context);
}
