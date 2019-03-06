package dk.marc.eggtimer.EggStates;

public class EggTimerContext {
    private EggTimerStates currentState;

    //region Getters&Setters
    public EggTimerStates getCurrentState(){
        return currentState;
    }

    void setCurrentState(EggTimerStates state){
        currentState = state;
    }
    //endregion


    public EggTimerContext() {
        currentState =  new EggTimerPendingState();
    }

    public void start(){
        currentState.start();
    }

    public void stop(){
        currentState.stop();
    }
}
