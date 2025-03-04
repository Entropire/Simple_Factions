package me.entropire.simple_factions.Gui;

import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class Button extends ItemStack
{
    private final ButtonPressAction action;

    public Button(Material material, ButtonPressAction action)
    {
        super(material);
        this.action = action;
    }

    public void onPressed(InventoryClickEvent event)
    {
        if (action != null)
        {
            action.onPress(this, event);
        }
    }
}
