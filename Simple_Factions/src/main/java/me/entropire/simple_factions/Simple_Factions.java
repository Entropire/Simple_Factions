package me.entropire.simple_factions;

import me.entropire.simple_factions.commands.FactionCommand;
import me.entropire.simple_factions.commands.chatCommands;
import me.entropire.simple_factions.database.DataBaseContext;
import me.entropire.simple_factions.database.FactionDatabase;
import me.entropire.simple_factions.database.PlayerDatabase;
import me.entropire.simple_factions.events.Message;
import me.entropire.simple_factions.events.OnInventoryClick;
import me.entropire.simple_factions.events.OnJoin;
import me.entropire.simple_factions.objects.Faction;
import me.entropire.simple_factions.objects.Invite;
import me.entropire.simple_factions.objects.Join;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public final class Simple_Factions extends JavaPlugin
{
    public static Simple_Factions plugin;

    public static FactionDatabase factionDatabase;
    public static PlayerDatabase playerDatabase;
    public static Map<UUID, Faction> createFactions = new HashMap<>();
    public static final Map<UUID, Invite> invites = new HashMap<>();
    public static final Map<UUID, Join> joins = new HashMap<>();

    @Override
    public void onEnable()
    {
        if(plugin == null) plugin = this;

        //loads events
        this.getServer().getPluginManager().registerEvents(new OnJoin(this), this);
        this.getServer().getPluginManager().registerEvents(new Message(this), this);
        this.getServer().getPluginManager().registerEvents(new OnInventoryClick(), this);

        //loads commands
        getCommand("faction").setExecutor(new FactionCommand());
        getCommand("f").setExecutor(new FactionCommand());
        getCommand("chat").setExecutor(new chatCommands());

        //loads commands tab completer
        getCommand("faction").setTabCompleter(new FactionCommand());
        getCommand("f").setTabCompleter(new FactionCommand());
        getCommand("chat").setTabCompleter(new chatCommands());

        if (!getDataFolder().exists()) getDataFolder().mkdir();
        DataBaseContext dataBaseContext = new DataBaseContext(getDataFolder().getAbsolutePath() + "/Simple-Faction.db");
        factionDatabase = new FactionDatabase(dataBaseContext);
        playerDatabase = new PlayerDatabase(dataBaseContext);

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
}
