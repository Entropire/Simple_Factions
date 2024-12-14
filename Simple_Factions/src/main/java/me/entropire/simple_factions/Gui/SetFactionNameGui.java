package me.entropire.simple_factions.Gui;

import me.entropire.simple_factions.Simple_Factions;
import net.wesjd.anvilgui.AnvilGUI;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.Collections;

public class SetFactionNameGui extends BaseGui
{
    public SetFactionNameGui(Simple_Factions plugin) {
        super(plugin);
    }

    @Override
    public void open(Player player)
    {
        new AnvilGUI.Builder()
                .onClick((slot, stateSnapshot) -> {
                    if(slot != AnvilGUI.Slot.OUTPUT) {
                        return Collections.emptyList();
                    }

                    if(!stateSnapshot.getText().equalsIgnoreCase("name"))
                    {
                        plugin.createFactions.get(player.getUniqueId()).setName(stateSnapshot.getText());
                        new CreateFactionGui(plugin).open(player);
                        return Arrays.asList(AnvilGUI.ResponseAction.close());
                    }
                    else
                    {
                        return Arrays.asList(AnvilGUI.ResponseAction.replaceInputText("name"));
                    }
                })
                .text("name")
                .title("Set faction name")
                .plugin(plugin)
                .open(player);
    }
}
