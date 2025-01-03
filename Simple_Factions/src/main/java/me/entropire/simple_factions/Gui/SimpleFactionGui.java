package me.entropire.simple_factions.Gui;

import me.entropire.simple_factions.Simple_Factions;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class SimpleFactionGui extends BaseGui
{
    @Override
    public void open(Player player)
    {
        Gui gui = new Gui("Simple-Factions", GuiSize.Small);

        gui.addButton(11, "Create new faction", Material.ANVIL, "Create a new faction.",
                (btn, event) -> new CreateFactionGui().open(player));

        gui.addButton(15, "Join faction", Material.NAME_TAG, "Join a faction.",
                (btn, event) -> new FactionListGui(0).open(player));

        player.openInventory(gui.Create());
    }
}
