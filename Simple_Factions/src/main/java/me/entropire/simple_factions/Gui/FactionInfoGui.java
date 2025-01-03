package me.entropire.simple_factions.Gui;

import me.entropire.simple_factions.FactionInvitor;
import me.entropire.simple_factions.Simple_Factions;
import me.entropire.simple_factions.objects.Faction;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.List;

public class FactionInfoGui extends BaseGui
{
    private String factionName;

    public FactionInfoGui(String factionName)
    {
        this.factionName = factionName;
    }

    @Override
    public void open(Player player)
    {
        Gui gui = new Gui("Info of " + factionName, GuiSize.Small);

        Faction faction = Simple_Factions.factionDatabase.getFactionDataByName(factionName);

        if(faction == null) { player.sendMessage(ChatColor.RED + "Somthing whent rong while getting the factions information."); return; }

        Player owner =  Bukkit.getPlayer(faction.getOwner());
        String ownerName;
        if(owner == null)
        {
            ownerName = "";
        }
        else
        {
            ownerName = owner.getName();
        }
        List<String> members = faction.getMembers();

        if (members.size() > 10) {
            members = members.subList(0, 10);
        }

        gui.addButton(2, "Faction name", Material.NAME_TAG, factionName, (btn, event) -> {});
        gui.addButton(4, "Faction owner", Material.PLAYER_HEAD, ownerName, (btn, event) -> {});
        gui.addButton(6, "Faction members", Material.OAK_SIGN, members,(btn, event) -> {});
        gui.addButton(21, "Join", Material.GREEN_WOOL, "Request to join this faction.", (btn, event) -> {
            String EventFactionName = event.getView().getTitle().replace("Info of ", "");
            FactionInvitor.join(player, EventFactionName);
            player.closeInventory();
        });

        gui.addButton(23, "Return", Material.RED_WOOL, "Go back to the previous page.",
                (btn, event) -> new FactionListGui(0).open(player));

        player.openInventory(gui.create());
    }
}
