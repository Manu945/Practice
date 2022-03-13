package fr.manu.ssuhc.utils.time;

import fr.manu.ssuhc.UHC;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

public abstract class Cooldown {
    public Cooldown(int seconds) {
        Bukkit.getScheduler().runTaskLater((Plugin)UHC.getInstance(), new Runnable() {
            public void run() {
                Cooldown.this.onFinish();
            }
        },  (20 * seconds));
    }

    public abstract void onFinish();
}
