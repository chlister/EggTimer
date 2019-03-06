package dk.marc.eggtimer.eggstates;

import dk.marc.eggtimer.EggTimer;
import dk.marc.eggtimer.EggTimerListener;

public class EggTimerContext {
    private EggTimerStates currentState;
    private EggTimer timer;

    //region Getters&Setters

    void setCurrentState(EggTimerStates state) {
        currentState = state;
    }

    EggTimer getTimer() {
        return timer;
    }

    void setTimer(EggTimer timer) {
        this.timer = timer;
    }

    //endregion

    /**
     * Instantiates a new EggTimerContext
     * (Private EggTimerStates is set to pending)
     */
    public EggTimerContext() {
        currentState = new EggTimerPendingState();
        timer = new EggTimer(0);
    }

    /**
     * Starts a timer that counts down from the timeToCount in milliseconds
     *
     * @param timeToCount <i>long</i> time to count down from
     */
    public void start(long timeToCount) {
        currentState.start(this, timeToCount);
    }

    /**
     * Stops the timer, if there is a timer running
     */
    public void stop() {
        currentState.stop(this);
    }



    /**
     * To subscribe to the events from the timer
     *
     * @param listener <i>EggTimerListener</i>
     */
    public void addListener(EggTimerListener listener) {
        timer.addListener(listener);
    }

    /**
     * To unsubscribe from the events from the timer
     *
     * @param listener <i>EggTimerListener</i>
     */
    public void removeListener(EggTimerListener listener) {
        timer.removeListener(listener);
    }

}
