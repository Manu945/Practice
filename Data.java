package fr.manu.practice.gestion;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.UUID;

abstract class Data {
    public UUID uuid;
    public String getUUID() { return uuid.toString(); }
    Player getPlayer(){
        return Bukkit.getPlayer(uuid);
    }
}
