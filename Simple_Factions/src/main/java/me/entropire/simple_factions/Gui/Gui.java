package me.entropire.simple_factions.Gui;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;

enum GuiSize {
    Small,
    Large
}
public class Gui implements InventoryHolder
{
    private final String name;
    private final int size;
    private final Map<Integer, Button> buttons = new HashMap<>();

    public Gui(String name, GuiSize size)
    {
        this.name = name;
        this.size = size == GuiSize.Small ? 27 : 54;
    }

    @Override
    public Inventory getInventory() {
        return null;
    }

    public void addButton(int slot, String name, Material material, String lore, ButtonPressAction action)
    {
        addButton(slot, name, material, Arrays.asList(lore), action);
    }

    public void addButton(int slot, String name, Material material, List<String> lore, ButtonPressAction action)
    {
        Button button = new Button(material, action);
        ItemMeta buttonMeta = button.getItemMeta();
        buttonMeta.setDisplayName(name);
        buttonMeta.setLore(lore);
        button.setItemMeta(buttonMeta);
        buttons.put(slot, button);
    }

    public Button getButton(int slot)
    {
        return buttons.get(slot);
    }

    public Inventory create()
    {
        Inventory inventory = Bukkit.createInventory(this, size, name);

        for(int slot : buttons.keySet())
        {
            inventory.setItem(slot, buttons.get(slot));
        }

        return inventory;
    }
}
