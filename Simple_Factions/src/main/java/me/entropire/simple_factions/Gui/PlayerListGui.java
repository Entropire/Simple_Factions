package me.entropire.simple_factions.Gui;

import me.entropire.simple_factions.Simple_Factions;
import me.entropire.simple_factions.objects.Faction;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import java.util.ArrayList;
import java.util.Iterator;

public class PlayerListGui extends BaseGui
{
    private int pageNumber;
    private Faction faction;

    public PlayerListGui(int pageNumber, Faction faction)
    {
        this.pageNumber = pageNumber;
        this.faction = faction;
    }

    public void open(Player player)
    {
        Gui gui = new Gui("Player page " + pageNumber, GuiSize.Large);
        ArrayList<String> playerNames = Simple_Factions.playerDatabase.getPlayerWithNoFaction();
        Iterator<String> playerNamesIterator = playerNames.iterator();

        while(playerNamesIterator.hasNext())
        {
            Player noFactionPlayer = Bukkit.getPlayer(playerNamesIterator.next());
            if(noFactionPlayer == null || !noFactionPlayer.isOnline())
            {
                playerNamesIterator.remove();
            }
        }



        if(!playerNames.isEmpty())
        {
            int index = 0;
            for(int i = 45 * pageNumber, j = Math.min(45 * (pageNumber + 1), playerNames.size()); i < j; i++)
            {
                gui.addButton(index, playerNames.get(i), Material.PLAYER_HEAD, "",
                        (btn, event) -> new FactionInfoGui(btn.getItemMeta().getDisplayName()).open(player));

                index++;
            }
        }

        float pageAmount = (float) playerNames.size() / 45;

        if(pageNumber < pageAmount - 1)
        {
            gui.addButton(53, "Next", Material.STONE_BUTTON, "Go to the next page.", (btn, event) -> {
                String inventoryName = event.getView().getTitle().replace("Factions page ", "");
                int eventPageNumber = Integer.parseInt(inventoryName) + 1;

                new FactionListGui(eventPageNumber).open(player);
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

                new FactionListGui(eventPageNumber).open(player);
            });
        }
        else
        {
            gui.addButton(45, "", Material.GRAY_STAINED_GLASS_PANE, "", (btn, event) -> {});
        }

        gui.addButton(49, "Return", Material.RED_WOOL, "Go back to the main menu.",
                (btn, event) -> new FactionGui(faction).open(player));

        gui.addButton(46, ".", Material.GRAY_STAINED_GLASS_PANE, "", (btn, event) -> {});
        gui.addButton(47, ".", Material.GRAY_STAINED_GLASS_PANE, "", (btn, event) -> {});
        gui.addButton(48, ".", Material.GRAY_STAINED_GLASS_PANE, "", (btn, event) -> {});
        gui.addButton(50, ".", Material.GRAY_STAINED_GLASS_PANE, "", (btn, event) -> {});
        gui.addButton(51, ".", Material.GRAY_STAINED_GLASS_PANE, "", (btn, event) -> {});
        gui.addButton(52, ".", Material.GRAY_STAINED_GLASS_PANE, "", (btn, event) -> {});

        player.openInventory(gui.Create());
    }
}
