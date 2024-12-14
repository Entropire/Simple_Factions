package me.entropire.simple_factions.Gui;

import me.entropire.simple_factions.FactionManager;
import me.entropire.simple_factions.Simple_Factions;
import me.entropire.simple_factions.objects.Colors;
import org.bukkit.entity.Player;

public abstract class BaseGui
{
    protected final Simple_Factions plugin;
    protected final FactionManager factionManager;
    protected final Colors colors = new Colors();

    public BaseGui(Simple_Factions plugin) {
        this.plugin = plugin;
        this.factionManager = new FactionManager(plugin);
    }

    public abstract void open(Player player);
}
