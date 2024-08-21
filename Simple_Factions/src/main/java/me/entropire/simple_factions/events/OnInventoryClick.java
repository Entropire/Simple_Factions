package me.entropire.simple_factions.events;

import me.entropire.simple_factions.FactionManager;
import me.entropire.simple_factions.Gui;
import me.entropire.simple_factions.Simple_Factions;
import me.entropire.simple_factions.objects.MenuHolder;
import me.entropire.simple_factions.objects.MenuTypes;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.sql.SQLException;

public class OnInventoryClick  implements Listener
{
    FactionManager factionManager;
    Gui gui;

    public OnInventoryClick(Simple_Factions simpleFactionsPlugin)
    {
        factionManager = new FactionManager(simpleFactionsPlugin);
        gui = new Gui(simpleFactionsPlugin);
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e)
    {

        e.getWhoClicked().sendMessage(e.getCurrentItem().toString());


        if(!(e.getInventory().getHolder() instanceof MenuHolder)) return;
        e.getWhoClicked().sendMessage(e.getCurrentItem().toString());

        if(e.getCurrentItem() == null) return;

        String inventoryName = e.getView().getTitle();
        ItemStack clickedItem = e.getCurrentItem();
        Player player = (Player) e.getView().getPlayer();

        MenuTypes menuType = ((MenuHolder) e.getInventory().getHolder()).menuType;

        switch (menuType)
        {
            case SimpleFaction:
                HandleSimpleFactionGui(clickedItem, player);
                break;
            case FactionList:
                HandleFactionListGui(clickedItem, player, inventoryName);
                break;
            case FactionInfo:
                HandleFactionJoinGui(clickedItem, player, inventoryName);
                break;
            case CreateFaction:
                HandleFactionCreateGui(clickedItem, player);
                break;
            case ChangeFactionName:
                HandleChangeFactionNameGui(clickedItem, player);
                break;
        }
    }

    private void HandleSimpleFactionGui(ItemStack clickedItem, Player player)
    {
        if(clickedItem.getItemMeta().getDisplayName().equals("Create new faction") && clickedItem.getType().equals(Material.ANVIL))
        {
            gui.CreateFaction(player);
        }

        if(clickedItem.getItemMeta().getDisplayName().equals("Join faction") && clickedItem.getType().equals(Material.NAME_TAG))
        {
            try
            {
                gui.FactionList(player, 0);
            } catch (SQLException e)
            {
                throw new RuntimeException(e);
            }
        }
    }

    private void HandleFactionListGui(ItemStack clickedItem, Player player, String inventoryName)
    {
        if(clickedItem.getType().equals(Material.PLAYER_HEAD))
        {
            try
            {
                gui.FactionInfo(player, clickedItem.getItemMeta().getDisplayName());
            } catch (SQLException e)
            {
                throw new RuntimeException(e);
            }
        }

        if(clickedItem.getItemMeta().getDisplayName().equals("Leave") && clickedItem.getType().equals(Material.RED_WOOL))
        {
            gui.SimpleFaction(player);
        }

        if(clickedItem.getType().equals(Material.STONE_BUTTON))
        {
            inventoryName = inventoryName.replace("Factions page ", "");
            int pageNumber = Integer.parseInt(inventoryName);

            if(clickedItem.getItemMeta().getDisplayName().equals("Previous"))
            {
                pageNumber--;
            }
            if(clickedItem.getItemMeta().getDisplayName().equals("Next"))
            {
                pageNumber++;
            }

            if(pageNumber < 0)
            {
                pageNumber = 0;
            }

            try
            {
                gui.FactionList(player, pageNumber);
            } catch (SQLException e)
            {
                throw new RuntimeException(e);
            }
        }
    }

    private void HandleFactionJoinGui(ItemStack clickedItem, Player player, String inventoryName)
    {
        if(clickedItem.getItemMeta().getDisplayName().equals("Join") && clickedItem.getType().equals(Material.GREEN_WOOL))
        {
            player.closeInventory();

            inventoryName = inventoryName.replace("Info of ", "");

            try
            {
                factionManager.join(player,inventoryName);
            } catch (SQLException e)
            {
                throw new RuntimeException(e);
            }
        }

        if(clickedItem.getItemMeta().getDisplayName().equals("Leave") && clickedItem.getType().equals(Material.RED_WOOL))
        {
            try
            {
                gui.FactionList(player, 0);
            } catch (SQLException e)
            {
                throw new RuntimeException(e);
            }
        }
    }

    private void HandleFactionCreateGui(ItemStack clickedItem, Player player)
    {
        if(clickedItem.getItemMeta().getDisplayName().equals("Create") && clickedItem.getType().equals(Material.GREEN_WOOL))
        {
            try
            {
                factionManager.create(player);
            } catch (SQLException e)
            {
                throw new RuntimeException(e);
            }
        }

        if(clickedItem.getItemMeta().getDisplayName().equals("Discard") && clickedItem.getType().equals(Material.RED_WOOL))
        {
            factionManager.DeleteFactionCreation(player);
            player.closeInventory();
        }

        if(clickedItem.getItemMeta().getDisplayName().equals("Faction name") && clickedItem.getType().equals(Material.NAME_TAG))
        {
            gui.ChangeFactionName(player);
        }

        if(clickedItem.getItemMeta().getDisplayName().equals("Faction color"))
        {

        }
    }

    private void HandleChangeFactionNameGui(ItemStack clickedItem, Player player)
    {
        player.sendMessage(clickedItem.getItemMeta().getDisplayName());
    }
}