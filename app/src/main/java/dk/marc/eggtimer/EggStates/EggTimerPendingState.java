package dk.marc.eggtimer.EggStates;

import dk.marc.eggtimer.EggTimer;

public class EggTimerPendingState implements EggTimerStates {

    @Override
    public void start(final EggTimerContext context, long timeToCount) {
        context.getTimer().setTimeLeft(timeToCount);
        context.getTimer().start();
        context.setCurrentState(new EggTimerRunningState());
    }

    @Override
    public void stop(final EggTimerContext context) {
        // We could throw an error here ;)
    }
}
