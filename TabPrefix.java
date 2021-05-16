package fr.manu.practice.tools;

import fr.manu.practice.Main;
import fr.manu.practice.gestion.Account;
import fr.manu.practice.gestion.ranks.RankUnit;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import java.util.Optional;

public class TabPrefix {

    private static boolean enable;

    public TabPrefix(){
        enable = false;
    }

    public static void enable()
    {
        enable = true;
        Scoreboard scoreboard = Bukkit.getScoreboardManager().getMainScoreboard();
        for(RankUnit ranks : RankUnit.values())
        {
            String rankName = getTeamName(ranks);
            Team team;
            if(scoreboard.getTeam(rankName) == null)
            {
                team = scoreboard.registerNewTeam(rankName);
            }
            else
            {
                team = scoreboard.getTeam(rankName);
            }
            team.setPrefix(ranks.getPrefix());
        }
    }

    public static void disable()
    {
        enable = false;
        Scoreboard scoreboard = Bukkit.getScoreboardManager().getMainScoreboard();
        for(RankUnit ranks : RankUnit.values())
        {
            Team team = scoreboard.getTeam(getTeamName(ranks));
            if(team != null)
            {
                team.unregister();
            }
        }

        for(Player players : Bukkit.getOnlinePlayers())
        {
            players.setDisplayName(ChatColor.WHITE + players.getName());
        }
    }

    public static void updateAsyncFor(Player player)
    {
        if(!enable)return;
        new Thread(() -> {
            RankUnit rank = RankUnit.JOUEUR;
            Optional<Account> account = Main.getInstance().getAccount(player);
            if(account.isPresent()){
                rank = account.get().getDataRank().getRank();
            }
            if(rank == null)return;
            Team team = Bukkit.getScoreboardManager().getMainScoreboard().getTeam(getTeamName(rank));
            if(team != null)team.addPlayer(player);
        }).start();
    }

    public static String getTeamName(RankUnit rank)
    {
        return rank.getPower() + "_" + rank.getShortName();
    }

    public static boolean isEnable()
    {
        return enable;
    }

}
