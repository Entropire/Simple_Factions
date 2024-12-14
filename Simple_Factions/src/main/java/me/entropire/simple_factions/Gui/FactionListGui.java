package me.entropire.simple_factions.Gui;

import me.entropire.simple_factions.Simple_Factions;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class FactionListGui extends BaseGui
{
    private int pageNumber;

    public FactionListGui(Simple_Factions plugin, int pageNumber) {
        super(plugin);
        this.pageNumber = pageNumber;
    }

    public void open(Player player)
    {
        Gui gui = new Gui("Factions page " + pageNumber, 54);
        ArrayList<String> factions = plugin.factionDatabase.getFactions();

        if(!factions.isEmpty())
        {
            int index = 0;
            for(int i = 45 * pageNumber, j = Math.min(45 * (pageNumber + 1), factions.size()); i < j; i++)
            {
                gui.addButton(index, factions.get(i), Material.PLAYER_HEAD, "",
                        (btn, event) -> new FactionInfoGui(plugin, btn.getItemMeta().getDisplayName()).open(player));

                index++;
            }
        }

        float pageAmount = (float) factions.size() / 45;

        if(pageNumber < pageAmount - 1)
        {
            gui.addButton(53, "Next", Material.STONE_BUTTON, "Go to the next page.", (btn, event) -> {
                String inventoryName = event.getView().getTitle().replace("Factions page ", "");
                int eventPageNumber = Integer.parseInt(inventoryName) + 1;

                new FactionListGui(plugin, eventPageNumber).open(player);
            });
        }
        else
        {
            gui.addButton(53, "", Material.GRAY_STAINED_GLASS_PANE, "", (btn, event) -> {});
        }

        if(pageNumber > 0)
        {
            gui.addButton(45, "Previous", Material.STONE_BUTTON, "Go to the previous page.", (btn, event) -> {
                String inventoryName = event.getView().getTitle().replace("Factions page ", "");
                int eventPageNumber = Integer.parseInt(inventoryName) - 1;

                new FactionListGui(plugin, eventPageNumber).open(player);
            });
        }
        else
        {
            gui.addButton(45, "", Material.GRAY_STAINED_GLASS_PANE, "", (btn, event) -> {});
        }

        gui.addButton(49, "Leave", Material.RED_WOOL, "Go back to the main menu.",
                (btn, event) -> new SimpleFactionGui(plugin).open(player));

        gui.addButton(46, "", Material.GRAY_STAINED_GLASS_PANE, "", (btn, event) -> {});
        gui.addButton(47, "", Material.GRAY_STAINED_GLASS_PANE, "", (btn, event) -> {});
        gui.addButton(48, "", Material.GRAY_STAINED_GLASS_PANE, "", (btn, event) -> {});
        gui.addButton(50, "", Material.GRAY_STAINED_GLASS_PANE, "", (btn, event) -> {});
        gui.addButton(51, "", Material.GRAY_STAINED_GLASS_PANE, "", (btn, event) -> {});
        gui.addButton(52, "", Material.GRAY_STAINED_GLASS_PANE, "", (btn, event) -> {});

        player.openInventory(gui.Create());
    }
}
