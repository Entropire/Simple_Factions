package me.entropire.simple_factions.Gui;

import me.entropire.simple_factions.Simple_Factions;
import me.entropire.simple_factions.objects.Faction;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class FactionGui extends BaseGui
{
    private Faction faction;

    public FactionGui(Simple_Factions plugin, Faction faction)
    {
        super(plugin);
        this.faction = faction;
    }

    @Override
    public void open(Player player)
    {
        Gui gui = new Gui(faction.getName(), 27);

        gui.addButton(10, "Faction name", Material.NAME_TAG, faction.getName(),
                (btn, event) -> new ChangeFactionNameGui(plugin).open(player));

        gui.addButton(12, "Faction color", colors.getMaterialWithChatColor(faction.getColor()), faction.getColor() + colors.getColorNameWithChatColor(faction.getColor()),
                (btn, event) -> new ChangeFactionColorGui(plugin).open(player));

        gui.addButton(14, "Owner", Material.PLAYER_HEAD, plugin.playerDatabase.getPlayerName(faction.getOwner().toString()), null);
        ArrayList<String> members = new ArrayList<>();
        for (int i = 0; i < Math.min(9, faction.getMembers().size()); i++)
        {
            members.add(faction.getMembers().get(i));
        }

        gui.addButton(16, "Members", Material.OAK_SIGN, members, (btn, event) -> {});
        if(faction.getOwner().equals(player.getUniqueId()))
        {
            gui.addButton(18, "Invite Player", Material.PAPER,  "", (btn, event) -> {});
            gui.addButton(26, "Delete Faction", Material.RED_WOOL,  "", (btn, event) -> {});
        }
        else
        {
            gui.addButton(26, "Leave Faction", Material.RED_WOOL,  "", (btn, event) -> {});
        }

        player.openInventory(gui.Create());
    }
}
