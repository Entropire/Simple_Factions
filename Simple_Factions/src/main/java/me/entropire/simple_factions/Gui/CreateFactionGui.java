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

                    String factionName = stateSnapshot.getText().toLowerCase();

                    if(factionName.matches(".*[^a-zA-Z].*"))
                    {
                        player.sendMessage(ChatColor.RED + "Special characters are not allowed in an faction names!");
                        return Arrays.asList(AnvilGUI.ResponseAction.replaceInputText("faction"));
                    }

                    if(stateSnapshot.getText().equalsIgnoreCase("faction"))
                    {
                        return Arrays.asList(AnvilGUI.ResponseAction.replaceInputText("faction"));
                    }

                    if(Simple_Factions.factionDatabase.factionExistsByName(factionName))
                    {
                        player.sendMessage(RED + "the name " + factionName + " is already in use by another faction!");
                        return Arrays.asList(AnvilGUI.ResponseAction.close());
                    }

                    FactionEditor.create(player, factionName);
                    return Arrays.asList(AnvilGUI.ResponseAction.close());
                })
                .text("faction")
                .title("Set faction name")
                .plugin(Simple_Factions.plugin)
                .open(player);
    }
}
