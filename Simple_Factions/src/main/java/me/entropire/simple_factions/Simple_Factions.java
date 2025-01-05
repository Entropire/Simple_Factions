package me.entropire.simple_factions;

import me.entropire.simple_factions.commands.FactionCommand;
import me.entropire.simple_factions.commands.ChatCommands;
import me.entropire.simple_factions.database.DataBaseContext;
import me.entropire.simple_factions.database.FactionDatabase;
import me.entropire.simple_factions.database.PlayerDatabase;
import me.entropire.simple_factions.events.Message;
import me.entropire.simple_factions.events.OnInventoryClick;
import me.entropire.simple_factions.events.OnJoin;
import me.entropire.simple_factions.objects.Invite;
import me.entropire.simple_factions.objects.Join;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public final class Simple_Factions extends JavaPlugin
{
    private File bannedWordsFile;
    private FileConfiguration bannedWordsConfig;

    private File settingsFile;
    private FileConfiguration settingsConfig;

    public static Simple_Factions plugin;

    public static FactionDatabase factionDatabase;
    public static PlayerDatabase playerDatabase;
    public static final Map<UUID, Invite> invites = new HashMap<>();
    public static final Map<UUID, Join> joins = new HashMap<>();

    @Override
    public void onEnable()
    {
        if(plugin == null) plugin = this;

        //loads settings
        createConfig("bannedNames.yml");
        bannedWordsFile = new File(getDataFolder().getAbsolutePath(), "bannedNames.yml");
        bannedWordsConfig = YamlConfiguration.loadConfiguration(bannedWordsFile);

        createConfig("settings.yml");
        settingsFile = new File(getDataFolder().getAbsolutePath(), "settings.yml");
        settingsConfig = YamlConfiguration.loadConfiguration(settingsFile);

        //loads events
        this.getServer().getPluginManager().registerEvents(new OnJoin(), this);
        this.getServer().getPluginManager().registerEvents(new Message(), this);
        this.getServer().getPluginManager().registerEvents(new OnInventoryClick(), this);

        //loads commands
        getCommand("faction").setExecutor(new FactionCommand());
        getCommand("f").setExecutor(new FactionCommand());
        getCommand("chat").setExecutor(new ChatCommands());
        getCommand("cp").setExecutor(new ChatCommands());
        getCommand("cf").setExecutor(new ChatCommands());

        //loads commands tab completer
        getCommand("faction").setTabCompleter(new FactionCommand());
        getCommand("f").setTabCompleter(new FactionCommand());
        getCommand("chat").setTabCompleter(new ChatCommands());

        //loads database
        if (!getDataFolder().exists()) getDataFolder().mkdir();
        DataBaseContext dataBaseContext = new DataBaseContext(getDataFolder().getAbsolutePath() + "/Simple-Faction.db");
        factionDatabase = new FactionDatabase(dataBaseContext);
        playerDatabase = new PlayerDatabase(dataBaseContext);

        //adds timer to bukkit scheduler for join and invite requests
        Bukkit.getScheduler().runTaskTimer(this, () ->
                {
                    long currentTime = System.currentTimeMillis();
                    invites.entrySet().removeIf(entry -> entry.getValue().expireDate() < currentTime);
                    joins.entrySet().removeIf(entry -> entry.getValue().expireDate() < currentTime);
                }
                , 0L, 20L);

        Bukkit.getServer().getConsoleSender().sendMessage("Simple_Factions enabled");
    }

    @Override
    public void onDisable()
    {
        Bukkit.getServer().getConsoleSender().sendMessage("Simple_Factions disabled");
    }

    private void createConfig(String fileName) {
        File file = new File(getDataFolder(), fileName);
        if (!file.exists()) {
            getDataFolder().mkdirs();
            saveResource(fileName, false);
        }
    }

    public FileConfiguration getBannedWordsConfig() {
        return bannedWordsConfig;
    }

    public FileConfiguration getSettingsConfig() {
        return settingsConfig;
    }

    public void saveBannedWordsConfig() {
        try {
            bannedWordsConfig.save(bannedWordsFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveSettingsConfig() {
        try {
            settingsConfig.save(settingsFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
