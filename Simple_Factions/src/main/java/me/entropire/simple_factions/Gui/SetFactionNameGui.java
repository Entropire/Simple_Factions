package me.entropire.simple_factions.Gui;

import me.entropire.simple_factions.Simple_Factions;
import me.entropire.simple_factions.objects.Faction;
import net.wesjd.anvilgui.AnvilGUI;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.Collections;

import static org.bukkit.ChatColor.*;

public class SetFactionNameGui extends BaseGui
{
    private Faction faction;

    public SetFactionNameGui(Faction faction)
    {
        this.faction = faction;
    }

    @Override
    public void open(Player player)
    {
        new AnvilGUI.Builder()
                .onClick((slot, stateSnapshot) -> {
                    if(slot != AnvilGUI.Slot.OUTPUT) {
                        return Collections.emptyList();
                    }

                    String factionName = stateSnapshot.getText().toLowerCase();

                    if(stateSnapshot.getText().equalsIgnoreCase(faction.getName()))
                    {
                        return Arrays.asList(AnvilGUI.ResponseAction.replaceInputText(faction.getName()));
                    }

                    if(Simple_Factions.factionDatabase.factionExistsByName(factionName))
                    {
                        player.sendMessage(RED + "the name " + factionName + " is already in use by another faction!");
                        return Arrays.asList(AnvilGUI.ResponseAction.close());
                    }

                    Simple_Factions.createFactions.get(player.getUniqueId()).setName(factionName);
                    new CreateFactionGui().open(player);
                    return Arrays.asList(AnvilGUI.ResponseAction.close());
                })
                .text(faction.getName())
                .title("Set faction name")
                .plugin(Simple_Factions.plugin)
                .open(player);
    }
}
