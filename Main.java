package fr.manu.practice;

import fr.manu.practice.database.MySQL;
import fr.manu.practice.gestion.Account;
import fr.manu.practice.gestion.ranks.RankUnit;
import fr.manu.practice.gestion.world.World;
import fr.manu.practice.listeners.ListenersManager;
import fr.manu.practice.scoreboard.ScoreboardManager;
import fr.manu.practice.scoreboard.ScoreboardTeam;
import fr.manu.practice.tools.TabPrefix;
import org.apache.commons.dbcp2.BasicDataSource;
import org.bukkit.Bukkit;
import org.bukkit.Difficulty;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public class Main extends JavaPlugin {
    private static Main instance;

    private BasicDataSource connectionPool;
    private MySQL mysql;

    private List<Account> accounts = new ArrayList<>();

    private ScoreboardManager scoreboardManager;

    private ScheduledExecutorService executorMonoThread;
    private ScheduledExecutorService scheduledExecutorService;

    private List<ScoreboardTeam> teams = new ArrayList<>();

    @Override
    public void onEnable() {
        instance = this;
        worldSetting();
        System.out.println("[PRACTICE] LAUNCHED");
        registerCommands();
        initConnection();
        new ListenersManager();

        scheduledExecutorService = Executors.newScheduledThreadPool(16);
        executorMonoThread = Executors.newScheduledThreadPool(1);
        this.scoreboardManager = new ScoreboardManager();


        Arrays.stream(RankUnit.values()).forEach(rank -> teams.add(new ScoreboardTeam(Integer.toString(rank.getPower()), rank.getPrefix())));

        TabPrefix.enable();
        super.onEnable();
    }

    private void registerCommands() {
    }

    @Override
    public void onDisable() {
        getScoreboardManager().onDisable();
        System.out.println("[PRACTICE] STOP");
        super.onDisable();
    }

    private void initConnection() {
        connectionPool = new BasicDataSource();
        connectionPool.setDriverClassName("com.mysql.jdbc.Driver");
        connectionPool.setUsername("pmauser");
        connectionPool.setPassword("5f,yscke=ZL?44XG37N&9H)xzRK<9_+2");
        connectionPool.setUrl("jdbc:mysql://154.44.181.34:3306/practice?autoReconnect=true");
        connectionPool.setInitialSize(1);
        connectionPool.setMaxTotal(10);
        mysql = new MySQL(connectionPool);
        mysql.createTables();
    }

    public static Main getInstance() { return instance; }

    public ScoreboardManager getScoreboardManager( ) {
        return scoreboardManager;
    }

    public ScheduledExecutorService getExecutorMonoThread( ) {
        return executorMonoThread;
    }

    public ScheduledExecutorService getScheduledExecutorService( ) {
        return scheduledExecutorService;
    }

    public MySQL getMySQL() {
        return mysql;
    }

    public List<Account> getAccounts() {
        return accounts;
    }

    public Optional<Account> getAccount(Player player){
        return new ArrayList<>(accounts).stream().filter(a -> a.getUUID().equals(player.getUniqueId().toString())).findFirst();
    }

    public List<ScoreboardTeam> getTeams() {
        return teams;
    }

    public Optional<ScoreboardTeam> getSbTeam(RankUnit rank){
        return teams.stream().filter(t -> t.getRank() == rank).findFirst();
    }

    private void worldSetting() {
        for (World world : World.values()) {
            Bukkit.getWorld(world.getWorldName()).setPVP(false);
            Bukkit.getWorld(world.getWorldName()).setDifficulty(Difficulty.PEACEFUL);
            Bukkit.getWorld(world.getWorldName()).setTime(6000L);
            Bukkit.getWorld(world.getWorldName()).setGameRuleValue("naturalRegeneration", "false");
            Bukkit.getWorld(world.getWorldName()).setGameRuleValue("doFireTick", "false");
            Bukkit.getWorld(world.getWorldName()).setGameRuleValue("doDaylightCycle", "false");
            Bukkit.getWorld(world.getWorldName()).setGameRuleValue("doWeatherCycle", "false");
        }
    }
}
