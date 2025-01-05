package me.entropire.simple_factions.Gui;

import org.bukkit.Material;
import org.bukkit.entity.Player;

public class SimpleFactionGui extends BaseGui
{
    @Override
    public void open(Player player)
    {
        Gui gui = new Gui("Simple-Factions", GuiSize.Small);

        gui.addButton(11, "Create", Material.ANVIL, "Create a new faction.",
                (btn, event) -> new CreateFactionGui().open(player));

        gui.addButton(15, "Join", Material.NAME_TAG, "Join a existing faction.",
                (btn, event) -> new FactionListGui(1).open(player));

        player.openInventory(gui.create());
    }
}
