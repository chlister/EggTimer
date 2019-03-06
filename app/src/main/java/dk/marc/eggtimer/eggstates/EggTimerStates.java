package dk.marc.eggtimer.eggstates;

public interface EggTimerStates {
    void start(EggTimerContext context,long timeToCount);
    void stop(EggTimerContext context);
}
