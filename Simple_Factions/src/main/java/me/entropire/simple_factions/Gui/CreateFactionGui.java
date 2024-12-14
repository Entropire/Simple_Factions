package me.entropire.simple_factions.Gui;

import me.entropire.simple_factions.Simple_Factions;
import me.entropire.simple_factions.objects.Faction;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class CreateFactionGui extends BaseGui
{
    public CreateFactionGui(Simple_Factions plugin)
    {
        super(plugin);
    }

    @Override
    public void open(Player player)
    {
        Gui gui = new Gui("New faction", 27);

        String factionName;
        ChatColor factionColor;

        if(plugin.createFactions.containsKey(player.getUniqueId()))
        {
            Faction faction = plugin.createFactions.get(player.getUniqueId());
            factionName = faction.getName();
            factionColor = faction.getColor();
        }
        else
        {
            ArrayList<String> members = new ArrayList<>();
            members.add(player.getName());

            Faction faction = new Faction(0, "New Faction",  ChatColor.WHITE, player.getUniqueId(), members);
            plugin.createFactions.put(player.getUniqueId(), faction);

            factionName = faction.getName();
            factionColor = faction.getColor();
        }

        gui.addButton(2, "Faction name", Material.NAME_TAG, factionName,
                (btn, event) -> new SetFactionNameGui(plugin).open(player));

        gui.addButton(6, "Faction color", colors.getMaterialWithChatColor(factionColor), factionColor + colors.getColorNameWithChatColor(factionColor),
                (btn, event) -> new SetFactionColorGui(plugin).open(player));

        gui.addButton(23, "Discard", Material.RED_WOOL, "Discard your faction creation.", (btn, event) -> {
            factionManager.DeleteFactionCreation((Player)event.getView().getPlayer());
            event.getView().getPlayer().closeInventory();
        });

        if(!factionName.contains("New Faction"))
        {
            gui.addButton(21, "Create", Material.GREEN_WOOL, "Create your new faction.", (btn, event) -> {
                factionManager.create((Player)event.getView().getPlayer());
                player.closeInventory();
            });
        }

        player.openInventory(gui.Create());
    }
}
