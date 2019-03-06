package dk.marc.eggtimer;

import java.util.ArrayList;

public class EggTimer extends Thread {
    private volatile long timeLeft;
    private ArrayList<EggTimerListener> listeners = new ArrayList<>();

    //region Getters&Setters

    public long getTimeLeft() {
        return timeLeft;
    }

    public void setTimeLeft(long timeLeft) {
        this.timeLeft = timeLeft;
    }
    //endregion

    @Override
    public void run() {
        System.out.println("Hello from Thread " + Thread.currentThread().getName());
        do {
            // We are working in milliseconds -> 1000 = 1 sec
            timeLeft -= 1000;
            try {
                notifyPropertyChanged(timeLeft);
                System.out.println("Current time is: " + timeLeft);
                Thread.sleep(1000); // Wait a second
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } while (timeLeft > 0);
        notifyTimerStopped(); // notify that timer has stopped

    }

    /**
     * Notifies all listeners that the timer has stopped counting
     */
    private void notifyTimerStopped() {
        for (EggTimerListener l : listeners) {
            l.onEggTimerStopped();
        }
    }

    /**
     * Notifies all listeners that the timers counter has changed
     *
     * @param timeLeft <i>long</i>
     */
    private void notifyPropertyChanged(long timeLeft) {
        for (EggTimerListener l : listeners) {
            l.onCountDown(timeLeft);
        }
    }

    public EggTimer(long timeLeft) {
        this.timeLeft = timeLeft;
    }

    /**
     * Method for stopping the timer
     */
    public void resetTimer() {
        timeLeft = 0;
        notifyPropertyChanged(timeLeft);
    }

    /**
     * Adding listener to the internal list of listeners
     *
     * @param listener <i>EggTimerListener</i>
     */
    public void addListener(EggTimerListener listener) {
        listeners.add(listener);
    }

    /**
     * Removing listener to the internal list of listeners
     *
     * @param listener <i>EggTimerListener</i>
     */
    public void removeListener(EggTimerListener listener) {
        listeners.remove(listener);
    }


}

