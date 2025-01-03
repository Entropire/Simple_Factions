package me.entropire.simple_factions.Gui;

import me.entropire.simple_factions.Simple_Factions;
import me.entropire.simple_factions.objects.Faction;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class FactionMemberInfoGui extends BaseGui
{
    private String memberName;
    private Faction faction;

    public FactionMemberInfoGui(String memberName, Faction faction)
    {
        this.memberName = memberName;
        this.faction = faction;
    }

    @Override
    public void open(Player player)
    {
        Gui gui = new Gui("Info of " + memberName, GuiSize.Small);

        gui.addButton(13, memberName, Material.PLAYER_HEAD, "", null);

        if(faction.getOwner().equals(player.getUniqueId()))
        {
            gui.addButton(11, "Make owner", Material.PAPER, "Make this member the owner of the faction", null);

            gui.addButton(15, "Kick member", Material.RED_WOOL, "Kick player from the faction", null);
        }

        player.openInventory(gui.Create());
    }
}
