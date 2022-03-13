package fr.manu.ssuhc.scoreboard;

import fr.manu.ssuhc.manager.players.PlayerManager;
import fr.manu.ssuhc.manager.game.State;
import fr.manu.ssuhc.UHC;
import fr.manu.ssuhc.manager.game.Mode;
import fr.manu.ssuhc.manager.game.State;
import fr.manu.ssuhc.manager.players.PlayerManager;
import fr.manu.ssuhc.mode.dbz.roles.manager.DbzRolesList;
import fr.manu.ssuhc.utils.time.SecondsConverter;
import java.util.UUID;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

public class PersonalScoreboard {
    private Player player;

    private final UUID uuid;

    private final ObjectiveSign objectiveSign;

    int i;

    PersonalScoreboard(Player player) {
        this.player = player;
        this.uuid = player.getUniqueId();
        this.objectiveSign = new ObjectiveSign("sidebar", "Attente");
        reloadData();
        this.objectiveSign.addReceiver((OfflinePlayer)player);
    }

    public void reloadData() {}

    private String base() {
        return UHC.getInstance().getStyle().getFirstColor().toString();
    }

    private String transition() {
        return ChatColor.DARK_GRAY + " ▶ " + UHC.getInstance().getStyle().getSecondColor();
    }

    public void setLines(String ip) {
        this.objectiveSign.setDisplayName(UHC.getInstance().getStyle().getScoreboardName());
        if (UHC.getInstance().getGame().getState().equals(State.WAITING)) {
            this.objectiveSign.setLine(0, ChatColor.DARK_GRAY.toString() + ChatColor.STRIKETHROUGH + "                          ");
            this.objectiveSign.setLine(1, base() + "Host" + transition() + UHC.getInstance().getSuperHostName());
            this.objectiveSign.setLine(2, base() + "Joueur(s)" + transition() + PlayerManager.getScoreboardPlayer() + ChatColor.DARK_GRAY + "/" + UHC.getInstance().getStyle().getSecondColor() + UHC.getInstance().getSlot());
            this.objectiveSign.setLine(3, ChatColor.AQUA.toString());
            this.objectiveSign.setLine(4, base() + "Mode" + transition() + UHC.getInstance().getGame().getMode().getName());
            this.objectiveSign.setLine(5, ChatColor.DARK_GRAY.toString() + ChatColor.STRIKETHROUGH + "                          " + ChatColor.AQUA);
        } else {
            this.i = 0;
            if (UHC.getInstance().getGame().getMode().equals(Mode.DBZ) && getSeconds(UHC.getInstance().getShortTask().getTime()) > 20) {
                for (DbzRolesList roles : DbzRolesList.values()) {
                    this.i++;
                    if (getSeconds(UHC.getInstance().getShortTask().getTime()) > 20 && this.i <= 7) {
                        this.objectiveSign.setLine(this.i - 1, roles.getTeams().getColor() + ChatColor.BOLD.toString() + roles.getNumber() + roles.getTeams().getColor() + " " + roles.getName());
                    } else if (getSeconds(UHC.getInstance().getShortTask().getTime()) > 30 && this.i > 7 && this.i <= 14) {
                        this.objectiveSign.setLine(this.i - 8, roles.getTeams().getColor() + ChatColor.BOLD.toString() + roles.getNumber() + roles.getTeams().getColor() + " " + roles.getName());
                    } else if (getSeconds(UHC.getInstance().getShortTask().getTime()) > 40 && this.i > 14 && this.i <= 21) {
                        this.objectiveSign.setLine(this.i - 15, roles.getTeams().getColor() + ChatColor.BOLD.toString() + roles.getNumber() + roles.getTeams().getColor() + " " + roles.getName());
                    } else if (getSeconds(UHC.getInstance().getShortTask().getTime()) > 50 && this.i > 21 && this.i <= 28) {
                        this.objectiveSign.setLine(this.i - 22, roles.getTeams().getColor() + ChatColor.BOLD.toString() + roles.getNumber() + roles.getTeams().getColor() + " " + roles.getName());
                    }
                }
            } else {
                this.objectiveSign.setLine(0, ChatColor.DARK_GRAY.toString() + ChatColor.STRIKETHROUGH + "                          ");
                this.objectiveSign.setLine(1, base() + "Host" + transition() + UHC.getInstance().getSuperHostName());
                this.objectiveSign.setLine(2, base() + "Joueur(s)" + transition() + PlayerManager.getScoreboardPlayer());
                this.objectiveSign.setLine(3, base() + "Kill(s)" + transition() + PlayerManager.getPlayer(this.uuid).getKills());
                this.objectiveSign.setLine(4, ChatColor.AQUA.toString());
                this.objectiveSign.setLine(5, base() + "Temps" + transition() + SecondsConverter.convert(UHC.getInstance().getShortTask().getTime(), false));
                this.objectiveSign.setLine(6, base() + "PvP" + transition() + ((UHC.getInstance().getShortTask().getTimePvp() == 0) ? "✔" : SecondsConverter.convert(UHC.getInstance().getShortTask().getTimePvp(), false)));
                this.objectiveSign.setLine(7, base() + "Bordure" + transition() + ((UHC.getInstance().getShortTask().getTimeBorder() == 0) ? "✔" : SecondsConverter.convert(UHC.getInstance().getShortTask().getTimeBorder(), false)));
                this.objectiveSign.setLine(8, base() + "Taille" + transition() + (UHC.getInstance().getGame().getState().equals(State.TP) ? "" : Integer.valueOf((int)Bukkit.getWorld("world").getWorldBorder().getSize() / 2)));
                this.objectiveSign.setLine(9, ChatColor.DARK_GRAY.toString() + ChatColor.STRIKETHROUGH + "                          " + ChatColor.AQUA);
            }
        }
        this.objectiveSign.updateLines();
        this.objectiveSign.clearScores();
    }

    public void onLogout() {
        this.objectiveSign.removeReceiver(Bukkit.getServer().getOfflinePlayer(this.uuid));
    }

    private int getSeconds(int time) {
        while (time > 60)
            time -= 60;
        return time;
    }
}

