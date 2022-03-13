package fr.manu.ssuhc.utils.time;

import fr.manu.ssuhc.UHC;

public class SecondsConverter {
    public static String convert(int seconds, boolean text) {
        int minutes = 0;
        int hours = 0;
        while (seconds >= 60) {
            seconds -= 60;
            minutes++;
        }
        if (text) {
            while (minutes >= 60) {
                minutes -= 60;
                hours++;
            }
            return UHC.getInstance().getStyle().getSecondColor() + ((hours != 0) ? (hours + UHC.getInstance().getStyle().getFirstColor().toString() + " heure(s) " + UHC.getInstance().getStyle().getSecondColor()) : "") + ((minutes != 0) ? (minutes + UHC.getInstance().getStyle().getFirstColor().toString() + " minute(s) " + UHC.getInstance().getStyle().getSecondColor()) : "") + ((seconds != 0) ? (seconds + UHC.getInstance().getStyle().getFirstColor().toString() + " seconde(s) ") : "");
        }
        return ((minutes != 0) ? (minutes + "m") : "") + ((seconds < 10) ? ("0" + seconds + "s") : (seconds + "s"));
    }
}
