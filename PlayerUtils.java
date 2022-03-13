package fr.manu.ssuhc.utils.player;

import fr.manu.ssuhc.UHC;
import fr.manu.ssuhc.manager.players.PlayerManager;
import fr.manu.ssuhc.manager.players.PlayerUHC;
import fr.manu.ssuhc.mode.dbz.event.PlayerDbzDeathListener;
import fr.manu.ssuhc.scenarios.King;
import fr.manu.ssuhc.scenarios.SuperHeroes;
import fr.manu.ssuhc.utils.maths.Interval;
import fr.manu.ssuhc.utils.time.Cooldown;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;

public class PlayerUtils {
    public static void clearInventory(Player player) {
        player.getInventory().clear();
        player.getInventory().setHelmet(new ItemStack(Material.AIR));
        player.getInventory().setChestplate(new ItemStack(Material.AIR));
        player.getInventory().setLeggings(new ItemStack(Material.AIR));
        player.getInventory().setBoots(new ItemStack(Material.AIR));
    }

    public static void clearEffect(Player player) {
        for (PotionEffect effects : player.getActivePotionEffects())
            player.removePotionEffect(effects.getType());
    }

    public static boolean isPlayer(String name) {
        if (Bukkit.getPlayer(name) == null)
            return false;
        return true;
    }

    public static Player getPlayer(String name) {
        if (isPlayer(name))
            return Bukkit.getPlayer(name);
        return null;
    }

    public static void addMetadata(Player player, String key) {
        player.setMetadata(key, (MetadataValue)new FixedMetadataValue((Plugin)UHC.getInstance(), key));
    }

    public static boolean haveMetadata(Player player, String key) {
        return player.hasMetadata(key);
    }

    public static void removeMetadata(Player player, String key) {
        player.removeMetadata(key, (Plugin)UHC.getInstance());
    }

    public static void revivePlayer(Player player, boolean afterRevive) {
        PlayerUHC playerUHC = PlayerManager.getPlayer(player.getUniqueId());
        clearInventory(player);
        player.setHealth(player.getMaxHealth());
        SuperHeroes.setEffect(player);
        King.setEffect(player);
        playerUHC.setSpectator(false);
        playerUHC.setReallyDeath(false);
        int size = (int)Bukkit.getWorld("world").getWorldBorder().getSize() / 2 - 50;
        Integer locationX = (new Interval(-size, size)).getAsRandomInt();
        Integer locationZ = (new Interval(-size, size)).getAsRandomInt();
        int locationY = Bukkit.getWorld("world").getHighestBlockYAt(locationX, locationZ);
        Location location = new Location(Bukkit.getWorld("world"), locationX, (locationY + 20), locationZ, 0.0F, 0.0F);
        player.teleport(location);
        player.setGameMode(GameMode.SURVIVAL);
        if (!playerUHC.getLastArmor().isEmpty())
            player.getInventory().setArmorContents(playerUHC.getLastArmorContent());
        for (ItemStack item : playerUHC.getLastInventory()) {
            player.getInventory().addItem(item);
        }
        player.updateInventory();
        noDamage(player, 10);
        if (afterRevive)
            PlayerDbzDeathListener.onRevive(player);
    }

    public static void noDamage(final Player player, int time) {
        addMetadata(player, "invincibility");
        new Cooldown(time) {
            public void onFinish() {
                PlayerUtils.removeMetadata(player, "invincibility");
            }
        };
    }

    public static double getAbsoHearths(Player player) {
        return ((CraftPlayer)player).getHandle().getAbsorptionHearts();
    }

    public static int getPing(Player player) {
        return (((CraftPlayer)player).getHandle()).ping;
    }

    public static String getIp(Player player) {
        return player.getAddress().getHostString();
    }
}
