package me.entropire.simple_factions.Gui;

import me.entropire.simple_factions.Simple_Factions;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class FactionMemberInfoGui extends BaseGui
{
    private String memberName;

    public FactionMemberInfoGui(Simple_Factions plugin, String memberName)
    {
        super(plugin);
        this.memberName = memberName;
    }

    @Override
    public void open(Player player)
    {
        Gui gui = new Gui("Info of " + memberName, 27);

        player.openInventory(gui.Create());
    }
}
