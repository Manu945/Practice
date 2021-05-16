package fr.manu.practice.scoreboard;

import fr.manu.practice.Main;
import fr.manu.practice.gestion.Account;
import fr.manu.practice.gestion.ranks.RankUnit;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

import java.util.Optional;
import java.util.UUID;

public class PersonalScoreboard {
    private Player player;
    private final UUID uuid;
    private final ObjectiveSign objectiveSign;
    private Optional<Account> account;
    private String grade;
    private int playerCount;

    PersonalScoreboard(Player player) {
        this.player = player;
        uuid = player.getUniqueId();
        objectiveSign = new ObjectiveSign("sidebar", "Nakama");
        account = Main.getInstance().getAccount(player);

        reloadData();
        objectiveSign.addReceiver(player);

        for (ScoreboardTeam team : Main.getInstance().getTeams()) {
            ((CraftPlayer) Bukkit.getPlayer(uuid)).getHandle().playerConnection.sendPacket(team.createTeam());
        }

        for(Player players1 : Bukkit.getOnlinePlayers()) {
            for (Player players2 : Bukkit.getOnlinePlayers()) {
                RankUnit rank = RankUnit.JOUEUR;
                Optional<Account> account = Main.getInstance().getAccount(players2);
                if(account.isPresent()){
                    rank = account.get().getDataRank().getRank();
                }

                Optional<ScoreboardTeam> team = Main.getInstance().getSbTeam(rank);
                team.ifPresent(t -> ((CraftPlayer) players1).getHandle().playerConnection.sendPacket(t.addOrRemovePlayer(3, players2.getName())));
            }
        }
    }
    void reloadData(){
        playerCount = Bukkit.getOnlinePlayers().size();

        account.ifPresent(a -> {
            grade = a.getDataRank().getRank().getShortName();
        });
    }

    void setLines(String ip){
        objectiveSign.setDisplayName("§e§lNakama");

        objectiveSign.setLine(0, "§1");
        objectiveSign.setLine(1, "§6Joueurs: §a" + playerCount + "/30");
        objectiveSign.setLine(2, "§6Grades: " + grade);
        objectiveSign.setLine(3, "§2");
        objectiveSign.setLine(4, ip);

        objectiveSign.updateLines();
    }

    void onLogout(){
        objectiveSign.removeReceiver(Bukkit.getServer().getOfflinePlayer(uuid));
    }

}
