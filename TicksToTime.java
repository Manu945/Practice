package fr.manu.ssuhc.utils.time;

public class TicksToTime {
    public static String converse(int ticks) {
        int secondes = ticks / 20;
        int minutes = 0;
        int heures = 0;
        while (secondes >= 60) {
            secondes -= 60;
            minutes++;
        }
        while (minutes >= 60) {
            minutes -= 60;
            heures++;
        }
        return (((heures != 0) ? (heures + " heure(s) ") : "") + ((minutes != 0) ? (minutes + " minute(s) ") : "") + secondes + " seconde(s)").toString();
    }
}
