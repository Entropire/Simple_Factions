package me.entropire.simple_factions.Gui;

import me.entropire.simple_factions.Simple_Factions;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class SimpleFactionGui extends BaseGui
{
    public SimpleFactionGui(Simple_Factions plugin) {
        super(plugin);
    }

    @Override
    public void open(Player player)
    {
        Gui gui = new Gui("Simple-Factions", 27);

        gui.addButton(11, "Create new faction", Material.ANVIL, "Create a new faction.",
                (btn, event) -> new CreateFactionGui(plugin).open(player));

        gui.addButton(15, "Join faction", Material.NAME_TAG, "Join a faction.",
                (btn, event) -> new FactionListGui(plugin, 0).open(player));

        player.openInventory(gui.Create());
    }
}
