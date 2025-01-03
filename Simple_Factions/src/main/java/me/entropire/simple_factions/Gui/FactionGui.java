package me.entropire.simple_factions.Gui;

import me.entropire.simple_factions.FactionEditor;
import me.entropire.simple_factions.Simple_Factions;
import me.entropire.simple_factions.objects.Colors;
import me.entropire.simple_factions.objects.Faction;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class FactionGui extends BaseGui
{
    private Faction faction;

    public FactionGui(Faction faction)
    {
        this.faction = faction;
    }

    @Override
    public void open(Player player)
    {
        Gui gui = new Gui(faction.getName(), GuiSize.Small);

        gui.addButton(10, "Faction name", Material.NAME_TAG, faction.getName(),
                (btn, event) ->
                {
                    if (player.getUniqueId().equals(faction.getOwner()))
                    {
                        new ChangeFactionNameGui().open(player);
                    }
                });

        gui.addButton(12, "Faction color", Colors.getMaterialWithChatColor(faction.getColor()), faction.getColor() + Colors.getColorNameWithChatColor(faction.getColor()),
                (btn, event) -> {
                    if(player.getUniqueId().equals(faction.getOwner()))
                    {
                        new ChangeFactionColorGui().open(player);
                    }
                });

        gui.addButton(14, "Owner", Material.PLAYER_HEAD, Simple_Factions.playerDatabase.getPlayerName(faction.getOwner().toString()),
                (btn, event) -> {
            if (faction.getOwner().equals(player.getUniqueId()))
            {
                new FactionMembersListGui(0).open(player);
            }
        });

        ArrayList<String> members = new ArrayList<>();
        for (int i = 0; i < Math.min(9, faction.getMembers().size()); i++)
        {
            members.add(faction.getMembers().get(i));
        }

        gui.addButton(16, "Members", Material.OAK_SIGN, members,
                (btn, event) -> {
            new FactionMembersListGui(0).open(player);
        });

        if(faction.getOwner().equals(player.getUniqueId()))
        {
            gui.addButton(18, "Invite Player", Material.PAPER,  "", (btn, event) -> {

            });

            gui.addButton(26, "Delete Faction", Material.RED_WOOL,  "", (btn, event) -> {
                FactionEditor.delete(player);
                player.closeInventory();
            });
        }
        else
        {
            gui.addButton(26, "Leave Faction", Material.RED_WOOL,  "", (btn, event) -> {
                FactionEditor.leave(player);
                player.closeInventory();
            });
        }

        player.openInventory(gui.Create());
    }
}
