package me.entropire.simple_factions.events;

import me.entropire.simple_factions.Simple_Factions;
import me.entropire.simple_factions.objects.Faction;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.ArrayList;

public class Message implements Listener
{
    @EventHandler
    public void MessageSend(AsyncPlayerChatEvent e)
    {
        Player player = e.getPlayer();
        if(Simple_Factions.playerDatabase.hasFaction(player))
        {
            String message = e.getMessage();
            int factionId = Simple_Factions.playerDatabase.getFactionId(player);
            Faction faction = Simple_Factions.factionDatabase.getFactionDataById(factionId);
            switch (Simple_Factions.playerDatabase.getChat(player.getUniqueId()).toLowerCase()){
                case "public":
                    e.setFormat(faction.getColor() + "[" + faction.getName() + "] " + player.getName() + ": " + ChatColor.GRAY + message);
                    break;
                case "faction":
                    e.setCancelled(true);
                    handleFactionChat(faction, player, message);
                    break;
                default:
                    player.sendMessage(ChatColor.RED + "Could not retrieve chat type!");
            }
        }
        else
        {
            e.setFormat(ChatColor.WHITE + player.getName() + ": " + ChatColor.GRAY + e.getMessage());
        }
    }

    private void handleFactionChat(Faction faction, Player player, String message)
    {
        ArrayList<String> members = faction.getMembers();
        for(String s : members)
        {
            Player member = Bukkit.getPlayer(s);
            if (member != null)
            {
                member.sendMessage(ChatColor.GREEN + "FACTION: " + faction.getColor() + "[" + faction.getName() + "] " + player.getName() + ": " + ChatColor.GRAY + message);
            }
        }
    }
}
