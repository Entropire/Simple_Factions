package me.entropire.simple_factions.Gui;

import me.entropire.simple_factions.FactionEditor;
import me.entropire.simple_factions.Simple_Factions;
import net.wesjd.anvilgui.AnvilGUI;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.Collections;

import static org.bukkit.ChatColor.*;

public class CreateFactionGui extends BaseGui
{
    @Override
    public void open(Player player)
    {
        new AnvilGUI.Builder()
                .onClick((slot, stateSnapshot) -> {
                    if(slot != AnvilGUI.Slot.OUTPUT) {
                        return Collections.emptyList();
                    }

                    FactionEditor.create(player, stateSnapshot.getText());
                    return Arrays.asList(AnvilGUI.ResponseAction.close());
                })
                .text("faction")
                .title("Set faction name")
                .plugin(Simple_Factions.plugin)
                .open(player);
    }
}
