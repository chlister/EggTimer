package dk.marc.eggtimer.Utility;

import java.util.Locale;

public class TimeConverter {
    /**
     * Returns a given <i>long</i> as a string formatted as mm:ss
     * @param milliseconds long
     * @return String
     */
    public static String getCurrentSelectedMillisAsString(long milliseconds) {
        int minutes = (int) (milliseconds / 1000) / 60;
        int seconds = (int) (milliseconds / 1000) % 60;
        return String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);
    }
}
