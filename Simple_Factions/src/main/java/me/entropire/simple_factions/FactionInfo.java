package me.entropire.simple_factions;

import me.entropire.simple_factions.objects.Faction;
import org.bukkit.entity.Player;

import java.util.ArrayList;

import static org.bukkit.ChatColor.RED;

public class FactionInfo
{
    public static void list(Player player)
    {
        ArrayList<String> factionsNames = Simple_Factions.factionDatabase.getFactions();
        player.sendMessage("Factions: ");
        for (String factionsName : factionsNames)
        {
            player.sendMessage("- " + factionsName);
        }
    }

    public static void members(Player player, String factionName)
    {
        if(!Simple_Factions.playerDatabase.hasFaction(player) && factionName == null)
        {
            player.sendMessage(RED + "Command usage /faction members [Faction name]");
            return;            
        }
       
        if(!Simple_Factions.factionDatabase.factionExistsByName(factionName))
        {
            player.sendMessage(RED + "There is no faction with the name " + factionName);
            return;
        } 
        
        Faction faction;
        
        if(factionName == null)
        {
            int factionId = Simple_Factions.playerDatabase.getFactionId(player);
            faction = Simple_Factions.factionDatabase.getFactionDataById(factionId);
        }
        else
        {
            faction = Simple_Factions.factionDatabase.getFactionDataByName(factionName);
        }
        
        player.sendMessage("Members: ");
        for (String member : faction.getMembers())
        {
            player.sendMessage(" -" + member);
        }
    }

    public static void owner(Player player, String factionName)
    {
        if(Simple_Factions.playerDatabase.hasFaction(player) && factionName == null)
        {
            player.sendMessage(RED + "Command usage /faction owner [Faction name]");
            return;    
        }
        
        if(Simple_Factions.factionDatabase.factionExistsByName(factionName))
        {
            player.sendMessage(RED + "There is no faction with the name " + factionName);
            return;
        }
    
        Faction faction;
        
        if(factionName == null)
        {
            int factionId = Simple_Factions.playerDatabase.getFactionId(player);
            faction= Simple_Factions.factionDatabase.getFactionDataById(factionId);
        }
        else 
        {
            faction = Simple_Factions.factionDatabase.getFactionDataByName(factionName);
        }

        String ownerUUID = faction.getOwner().toString();
        String ownerName = Simple_Factions.playerDatabase.getPlayerName(ownerUUID);
        player.sendMessage(ChatColor.AQUA + "The owner of " + faction.getName() + " is " + ownerName);
    }
}
