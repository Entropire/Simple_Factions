package me.entropire.simple_factions.objects;

import me.entropire.simple_factions.Gui.Button;
import org.bukkit.event.inventory.InventoryClickEvent;

@FunctionalInterface
public interface ButtonPressAction {
    void onPress(Button button, InventoryClickEvent event);
}