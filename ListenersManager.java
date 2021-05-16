package fr.manu.practice.listeners;

import fr.manu.practice.Main;
import fr.manu.practice.listeners.players.PlayerJoinQuitChat;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;

public class ListenersManager {

    private final PluginManager pm = Bukkit.getServer().getPluginManager();

    public ListenersManager() {
        this.pm.registerEvents((Listener)new PlayerJoinQuitChat(), (Plugin) Main.getInstance());
        //this.pm.registerEvents((Listener)new PlayerClick(), (Plugin) Main.getInstance());
        //this.pm.registerEvents((Listener)new PlayerInteract(), (Plugin) Main.getInstance());
        //this.pm.registerEvents((Listener)new PlayerOther(), (Plugin) Main.getInstance());

    }
}
