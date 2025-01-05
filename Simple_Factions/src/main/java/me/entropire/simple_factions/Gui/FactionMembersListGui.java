package me.entropire.simple_factions.Gui;

import me.entropire.simple_factions.Simple_Factions;
import me.entropire.simple_factions.objects.Faction;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class FactionMembersListGui extends BaseGui
{
    private final int pageNumber;

    public FactionMembersListGui(int pageNumber)
    {
        this.pageNumber = pageNumber;
    }

    @Override
    public void open(Player player)
    {
        Gui gui = new Gui("Members page " + pageNumber, GuiSize.Large);

        int factionId = Simple_Factions.playerDatabase.getFactionId(player);
        Faction faction = Simple_Factions.factionDatabase.getFactionDataById(factionId);
        ArrayList<String> members = faction.getMembers();
        members.remove(player.getName());

        if(!members.isEmpty())
        {
            members.remove(player.getDisplayName());

            int index = 0;
            for(int i = 45 * pageNumber, j = Math.min(45 * (pageNumber + 1), members.size()); i < j; i++)
            {
                gui.addButton(index, members.get(i), Material.PLAYER_HEAD, "",
                        (btn, event) -> new FactionMemberInfoGui(btn.getItemMeta().getDisplayName(), faction).open(player));

                index++;
            }
        }

        float pageAmount = (float) members.size() / 45;

        if(pageNumber < pageAmount - 1)
        {
            gui.addButton(53, "Next", Material.STONE_BUTTON, "Go to the next page.",
                    (btn, event) -> {
                String inventoryName = event.getView().getTitle().replace("Factions page ", "");
                int eventPageNumber = Integer.parseInt(inventoryName) + 1;
                new FactionListGui(eventPageNumber).open(player);
            });
        }
        else
        {
            gui.addButton(53, "§r", Material.GRAY_STAINED_GLASS_PANE, "", null);
        }

        if(pageNumber > 0)
        {
            gui.addButton(45, "Previous", Material.STONE_BUTTON, "Go to the previous page.",
                    (btn, event) -> {
                String inventoryName = event.getView().getTitle().replace("Factions page ", "");
                int eventPageNumber = Integer.parseInt(inventoryName) - 1;
                new FactionListGui(eventPageNumber).open(player);
            });
        }
        else
        {
            gui.addButton(45, "§r", Material.GRAY_STAINED_GLASS_PANE, "", null);
        }

        gui.addButton(49, "Return", Material.RED_WOOL, "Go back to the faction menu.",
                (btn, event) -> new FactionGui(faction).open(player));

        gui.addButton(46, "§r", Material.GRAY_STAINED_GLASS_PANE, "", null);
        gui.addButton(47, "§r", Material.GRAY_STAINED_GLASS_PANE, "", null);
        gui.addButton(48, "§r", Material.GRAY_STAINED_GLASS_PANE, "", null);
        gui.addButton(50, "§r", Material.GRAY_STAINED_GLASS_PANE, "", null);
        gui.addButton(51, "§r", Material.GRAY_STAINED_GLASS_PANE, "", null);
        gui.addButton(52, "§r", Material.GRAY_STAINED_GLASS_PANE, "", null);

        player.openInventory(gui.create());
    }
}
