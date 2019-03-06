package dk.marc.eggtimer.eggstates;

public class EggTimerRunningState implements EggTimerStates {
    @Override
    public void start(final EggTimerContext context, long timeToCount) {
        // We could throw an error here ;)
    }

    @Override
    public void stop(final EggTimerContext context) {
        context.getTimer().resetTimer();
        context.setCurrentState(new EggTimerPendingState());
    }
}
