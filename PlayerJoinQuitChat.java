package fr.manu.practice.listeners.players;

import fr.manu.practice.Main;
import fr.manu.practice.gestion.Account;
import fr.manu.practice.gestion.ranks.RankUnit;
import fr.manu.practice.tools.ItemBuilder;
import fr.manu.practice.tools.TabPrefix;
import fr.manu.practice.tools.Title;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PlayerJoinQuitChat implements Listener {

    private List<Player> cooldown = new ArrayList<>();

    @EventHandler
    public void OnJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        Account account = new Account(player.getUniqueId());
        account.onLogin();

        event.setJoinMessage(account.getDataRank().getRank().getPrefix() + player.getName() + " §fjoined the hub!");
        Main.getInstance().getScoreboardManager().onLogin(player);

        player.getInventory().clear();
        player.getEquipment().clear();
        player.setGameMode(GameMode.ADVENTURE);
        player.setHealth(20);
        player.setFoodLevel(20);
        player.setLevel(0);
        player.setExp(0);
        player.teleport(Bukkit.getWorlds().get(0).getSpawnLocation());

        Title title = new Title("§e§l►§6§l► §6§lPRACTICE §6§l◄§e§l◄", "§fGet a good workout!");
        title.send(player, 1, 5, 1);

        player.getInventory().setItem(0 , (new ItemBuilder(Material.IRON_SWORD).setName("§6§lUnranked Queue").toItemStack()));
        player.getInventory().setItem(1 , (new ItemBuilder(Material.DIAMOND_SWORD).setName("§a§lRanked Queue").toItemStack()));
        player.getInventory().setItem(4 , (new ItemBuilder(Material.NETHER_STAR).setName("§d§lParty").toItemStack()));
        player.getInventory().setItem(8 , (new ItemBuilder(Material.BOOK).setName("§2§lKit Editor").toItemStack()));

        player.updateInventory();

        TabPrefix.updateAsyncFor(player);
        }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent e){
        Player player = e.getPlayer();

        RankUnit rank = RankUnit.JOUEUR;
        Optional<Account> account = Main.getInstance().getAccount(player);
        if(account.isPresent()){
            rank = account.get().getDataRank().getRank();
        }

        e.setFormat(rank.getPrefix() + "%1$s §8»§7 " + (rank == RankUnit.JOUEUR ? "§7" : "§f") + "%2$s");

        if(cooldown.contains(player)){
            e.setCancelled(true);
            player.sendMessage("§cPlease wait between each message!");
            return;
        }

        if(rank.getPower() > RankUnit.STAFF.getPower()){
            cooldown.add(player);
            Bukkit.getScheduler().runTaskLater(Main.getInstance(), () -> cooldown.remove(player), 2 * 20L);
        }
    }

    public void OnQuit(PlayerQuitEvent event) {

        Player player = event.getPlayer();
        Main.getInstance().getAccount(player).ifPresent(Account::onLogout);
        Main.getInstance().getScoreboardManager().onLogout(player);
    }
}
