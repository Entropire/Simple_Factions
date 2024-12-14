package me.entropire.simple_factions.Gui;

import org.bukkit.event.inventory.InventoryClickEvent;

@FunctionalInterface
public interface ButtonPressAction
{
    void onPress(Button button, InventoryClickEvent event);
}