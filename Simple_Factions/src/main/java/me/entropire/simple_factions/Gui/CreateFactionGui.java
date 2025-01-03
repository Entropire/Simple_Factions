package me.entropire.simple_factions.Gui;

import me.entropire.simple_factions.FactionEditor;
import me.entropire.simple_factions.Simple_Factions;
import me.entropire.simple_factions.objects.Colors;
import me.entropire.simple_factions.objects.Faction;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class CreateFactionGui extends BaseGui
{
    @Override
    public void open(Player player)
    {
        Gui gui = new Gui("New faction", GuiSize.Small);

        String factionName;
        ChatColor factionColor;
        Faction faction;
        if(Simple_Factions.createFactions.containsKey(player.getUniqueId()))
        {
            faction = Simple_Factions.createFactions.get(player.getUniqueId());
            factionName = faction.getName();
            factionColor = faction.getColor();
        }
        else
        {
            ArrayList<String> members = new ArrayList<>();
            members.add(player.getName());

            faction = new Faction(0, "New Faction",  ChatColor.WHITE, player.getUniqueId(), members);
            Simple_Factions.createFactions.put(player.getUniqueId(), faction);

            factionName = faction.getName();
            factionColor = faction.getColor();
        }

        gui.addButton(2, "Faction name", Material.NAME_TAG, factionName,
                (btn, event) -> new SetFactionNameGui(faction).open(player));

        gui.addButton(6, "Faction color", Colors.getMaterialWithChatColor(factionColor), factionColor + Colors.getColorNameWithChatColor(factionColor),
                (btn, event) -> new SetFactionColorGui().open(player));

        gui.addButton(factionName.equals("New Faction") ? 22 : 23, "Discard", Material.RED_WOOL, "Discard your faction creation.", (btn, event) -> {
            FactionEditor.DeleteFactionCreation(player);
            event.getView().getPlayer().closeInventory();
        });

        if(!factionName.equals("New Faction"))
        {
            gui.addButton(21, "Create", Material.GREEN_WOOL, "Create your new faction.", (btn, event) -> {
                FactionEditor.create(player);
                player.closeInventory();
            });
        }

        player.openInventory(gui.create());
    }
}
