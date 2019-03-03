package dk.marc.eggtimer;

public class EggTimer extends Thread {
    private long timeInMillis;
    private volatile boolean bRunning;

    public boolean isbRunning() {
        return bRunning;
    }

    public void setbRunning(boolean bRunning) {
        this.bRunning = bRunning;
    }

    public long getTimeInMillis() {
        return timeInMillis;
    }

    public void setTimeInMillis(long timeInMillis) {
        this.timeInMillis = timeInMillis;
    }


    @Override
    public void run() {
        setbRunning(true);
        while (timeInMillis <= 0) {
            if (!bRunning)
                break;
            // wait a sec
            timeInMillis -= 1000;
            System.out.println("Running. Time left: " + timeInMillis);
        }
    }

    public EggTimer(long timeInMillis) {
        this.timeInMillis = timeInMillis;
    }
}
