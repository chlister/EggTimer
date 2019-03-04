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
            timeLeft -= 1000;
            try {
                notifyPropertyChanged(timeLeft);
                System.out.println("Current time is: " + timeLeft);
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } while (timeLeft > 0);
        notifyTimerStopped();

    }

    private void notifyTimerStopped() {
        for (EggTimerListener l : listeners) {
            l.onEggTimerStopped();
        }
    }

    private void notifyPropertyChanged(long timeLeft) {
        for (EggTimerListener l : listeners) {
            l.onCountDown(timeLeft);
        }
    }

    public EggTimer(long timeLeft) {
        this.timeLeft = timeLeft;
    }

    public void resetTimer() {
        timeLeft = 0;
        notifyPropertyChanged(timeLeft);
    }

    public void addListener(EggTimerListener listener) {
        listeners.add(listener);
    }

    public void removeListener(EggTimerListener listener) {
        listeners.remove(listener);
    }


}

