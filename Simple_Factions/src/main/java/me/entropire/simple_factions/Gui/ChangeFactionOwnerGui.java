package me.entropire.simple_factions.Gui;

import me.entropire.simple_factions.Simple_Factions;
import me.entropire.simple_factions.objects.Faction;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class ChangeFactionOwnerGui extends BaseGui
{
    public ChangeFactionOwnerGui(Simple_Factions plugin)
    {
        super(plugin);
    }

    @Override
    public void open(Player player)
    {
        Gui gui = new Gui("Change faction owner", 54);

        int factionId = plugin.playerDatabase.getFactionId(player);
        ArrayList<String> members = plugin.factionDatabase.getFactionDataById(factionId).getMembers();

        members.remove(player.getDisplayName());


    }
}
