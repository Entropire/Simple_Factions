package me.entropire.simple_factions.Gui;

import me.entropire.simple_factions.FactionEditor;
import me.entropire.simple_factions.Simple_Factions;
import me.entropire.simple_factions.objects.Faction;
import net.wesjd.anvilgui.AnvilGUI;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.Collections;

public class ChangeFactionNameGui extends BaseGui
{
    @Override
    public void open(Player player)
    {
        int factionId = Simple_Factions.playerDatabase.getFactionId(player);
        Faction faction = Simple_Factions.factionDatabase.getFactionDataById(factionId);

        if(faction == null)
        {
            player.sendMessage(ChatColor.RED + "You can only change the faction name if your in a faction and the owner of the faction!");
            return;
        }

        new AnvilGUI.Builder()
                .onClick((slot, stateSnapshot) -> {
                    if(slot != AnvilGUI.Slot.OUTPUT) {
                        return Collections.emptyList();
                    }

                    FactionEditor.modifyName(player, stateSnapshot.getText());

                    return Arrays.asList(AnvilGUI.ResponseAction.close());
                })
                .text(faction.getName())
                .title("Change faction name")
                .plugin(Simple_Factions.plugin)
                .open(player);
    }
}
