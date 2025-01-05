package me.entropire.simple_factions.events;

import me.entropire.simple_factions.Simple_Factions;
import me.entropire.simple_factions.objects.Faction;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class OnJoin implements Listener
{
    @EventHandler
    public void onJoin(PlayerJoinEvent e)
    {
        Player player = e.getPlayer();
        if(!Simple_Factions.playerDatabase.playerExists(player.getName()))
        {
            Simple_Factions.playerDatabase.addPlayer(e.getPlayer());
        }

        int factionId = Simple_Factions.playerDatabase.getFactionId(player);
        if(factionId > 0)
        {
            Faction faction = Simple_Factions.factionDatabase.getFactionDataById(factionId);

            player.setDisplayName(faction.getColor() + "[" + faction.getName() + "] " + player.getName());
            player.setPlayerListName(faction.getColor() + "[" + faction.getName() + "] " + player.getName());
            e.setJoinMessage(faction.getColor() + "[" + faction.getName() + "] " + player.getName() + ChatColor.YELLOW + " joined the game.");
        }
    }
}
