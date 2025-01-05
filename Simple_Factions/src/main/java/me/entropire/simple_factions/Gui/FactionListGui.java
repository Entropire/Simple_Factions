package me.entropire.simple_factions.Gui;

import me.entropire.simple_factions.Simple_Factions;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class FactionListGui extends BaseGui
{
    private final int pageNumber;

    public FactionListGui(int pageNumber)
    {
        this.pageNumber = pageNumber;
    }

    public void open(Player player)
    {
        ArrayList<String> factions = Simple_Factions.factionDatabase.getFactions();

        int maxPageNumber = (int)Math.ceil(factions.size() / 45) + 1;

        Gui gui = new Gui("Factions - " + pageNumber + "/" + maxPageNumber, GuiSize.Large);

        if(!factions.isEmpty())
        {
            int endIndex = Math.min(45 * pageNumber, factions.size());
            int startIndex = 45 * (pageNumber - 1);
            int index = 0;
            for(int i = startIndex; i < endIndex; i++)
            {
                gui.addButton(index, factions.get(i), Material.PLAYER_HEAD, "",
                        (btn, event) -> new FactionInfoGui(btn.getItemMeta().getDisplayName()).open(player));

                index++;
            }
        }


        if(pageNumber < maxPageNumber)
        {
            gui.addButton(53, "Next", Material.STONE_BUTTON, "Go to the next page.",
                    (btn, event) -> {
                String inventoryName = event.getView().getTitle().replace("Factions - ", "");
                int eventPageNumber = Integer.parseInt( inventoryName.split("/")[0]) + 1;
                new FactionListGui(eventPageNumber).open(player);
            });
        }
        else
        {
            gui.addButton(53, "§r", Material.GRAY_STAINED_GLASS_PANE, "", null);
        }

        if(pageNumber > 1)
        {
            gui.addButton(45, "Previous", Material.STONE_BUTTON, "Go to the previous page.",
                    (btn, event) -> {
                String inventoryName = event.getView().getTitle().replace("Factions - ", "");
                int eventPageNumber = Integer.parseInt( inventoryName.split("/")[0]) - 1;
                new FactionListGui(eventPageNumber).open(player);
            });
        }
        else
        {
            gui.addButton(45, "§r", Material.GRAY_STAINED_GLASS_PANE, "", null);
        }

        gui.addButton(49, "Return", Material.RED_WOOL, "Go back to the main menu.",
                (btn, event) -> new SimpleFactionGui().open(player));

        gui.addButton(46, "§r", Material.GRAY_STAINED_GLASS_PANE, "", null);
        gui.addButton(47, "§r", Material.GRAY_STAINED_GLASS_PANE, "", null);
        gui.addButton(48, "§r", Material.GRAY_STAINED_GLASS_PANE, "", null);
        gui.addButton(50, "§r", Material.GRAY_STAINED_GLASS_PANE, "", null);
        gui.addButton(51, "§r", Material.GRAY_STAINED_GLASS_PANE, "", null);
        gui.addButton(52, "§r", Material.GRAY_STAINED_GLASS_PANE, "", null);

        player.openInventory(gui.create());
    }
}
