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
        ArrayList<String> members;
        if(factionName == null)
        {
            if(Simple_Factions.playerDatabase.hasFaction(player))
            {
                int factionId = Simple_Factions.playerDatabase.getFactionId(player);
                members = Simple_Factions.factionDatabase.getFactionDataById(factionId).getMembers();
            }
            else
            {
                player.sendMessage(RED + "You are not in a faction.");
                return;
            }
        }
        else if  (Simple_Factions.factionDatabase.factionExistsByName(factionName))
        {
            members = Simple_Factions.factionDatabase.getFactionDataByName(factionName).getMembers();
        }
        else
        {
            player.sendMessage(RED + "faction " + factionName + " does not  exists.");
            return;
        }

        player.sendMessage("Members: ");
        for (String member : members)
        {
            player.sendMessage(" -" + member);
        }
    }

    public static void owner(Player player, String factionName)
    {
        Faction faction;
        if(factionName == null)
        {
            if(Simple_Factions.playerDatabase.hasFaction(player))
            {
                int factionId = Simple_Factions.playerDatabase.getFactionId(player);
                faction= Simple_Factions.factionDatabase.getFactionDataById(factionId);
            }
            else
            {
                player.sendMessage(RED + "you are not in a faction");
                return;
            }
        }
        else if  (Simple_Factions.factionDatabase.factionExistsByName(factionName))
        {
            faction = Simple_Factions.factionDatabase.getFactionDataByName(factionName);
        }
        else
        {
            player.sendMessage(RED + "faction " + factionName + " does not  exists.");
            return;
        }

        String ownerUUID = faction.getOwner().toString();
        String ownerName = Simple_Factions.playerDatabase.getPlayerName(ownerUUID);
        player.sendMessage("Owner of " + faction.getName() + ": " + ownerName);
    }
}
