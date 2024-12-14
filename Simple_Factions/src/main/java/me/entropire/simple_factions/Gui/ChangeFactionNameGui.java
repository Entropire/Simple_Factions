package me.entropire.simple_factions.Gui;

import me.entropire.simple_factions.Simple_Factions;
import me.entropire.simple_factions.objects.Faction;
import net.wesjd.anvilgui.AnvilGUI;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.Collections;

public class ChangeFactionNameGui extends BaseGui
{
    public ChangeFactionNameGui(Simple_Factions plugin)
    {
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
                    Faction faction;

                    int factionId = plugin.playerDatabase.getFactionId(player);
                    faction  = plugin.factionDatabase.getFactionDataById(factionId);

                    factionManager.modifyName(player, faction.getName());

                    return Arrays.asList(AnvilGUI.ResponseAction.close());
                })
                .text("")
                .title("Change faction name")
                .plugin(plugin)
                .open(player);
    }
}
