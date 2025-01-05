package me.entropire.simple_factions;

import me.entropire.simple_factions.objects.Faction;
import org.bukkit.entity.Player;

import java.util.ArrayList;

import static org.bukkit.ChatColor.*;

public class FactionInfo
{
    public static void list(Player player, int pageNumber)
    {
        ArrayList<String> factions = Simple_Factions.factionDatabase.getFactions();

        int maxPageNumber = (int)Math.ceil(factions.size() / 9) + 1;

        if(pageNumber > maxPageNumber || pageNumber < 0)
        {
            player.sendMessage(YELLOW + "Invalid page number!");
            return;
        }

        player.sendMessage(AQUA + "Factions - " + pageNumber + "/" + maxPageNumber);

        if(!factions.isEmpty())
        {
            factions.remove(player.getDisplayName());

            int endIndex = Math.min(9 * pageNumber, factions.size());
            int startIndex = 9 * (pageNumber - 1);
            for(int i = startIndex; i < endIndex; i++)
            {
                player.sendMessage(AQUA + "- " + factions.get(i));
            }
        }
    }

    public static void members(Player player, String factionName, int pageNumber)
    {
        Faction faction = Simple_Factions.factionDatabase.getFactionDataByName(factionName);;

        if(faction == null)
        {
            player.sendMessage(RED + "There is no faction with the name " + factionName);
            return;
        }

        ArrayList<String> members = faction.getMembers();

        int maxPageNumber = (int)Math.ceil(members.size() / 9) + 1;

        if(pageNumber > maxPageNumber || pageNumber < 1)
        {
            player.sendMessage(YELLOW + "Invalid page number!");
            return;
        }

        player.sendMessage(AQUA + "Members of " + faction.getName() + " - " + pageNumber + "/" + maxPageNumber);

        if(!members.isEmpty())
        {
            members.remove(player.getDisplayName());

            int endIndex = Math.min(9 * pageNumber, members.size());
            int startIndex = 9 * (pageNumber - 1);
            for(int i = startIndex; i < endIndex; i++)
            {
                player.sendMessage(AQUA + "- " + members.get(i));
            }
        }
    }

    public static void owner(Player player, String factionName)
    {
        if(!Simple_Factions.playerDatabase.hasFaction(player) && factionName == null)
        {
            player.sendMessage(RED + "Command usage /faction owner [Faction name]");
            return;    
        }

        Faction faction;

        if(factionName == null || factionName.isEmpty())
        {
            if(Simple_Factions.playerDatabase.hasFaction(player))
            {
                int factionId = Simple_Factions.playerDatabase.getFactionId(player);
                faction = Simple_Factions.factionDatabase.getFactionDataById(factionId);
            }
            else
            {
                player.sendMessage(RED + "There is no faction with the name " + factionName);
                return;
            }
        }
        else
        {
            faction = Simple_Factions.factionDatabase.getFactionDataByName(factionName);
        }

        String ownerUUID = faction.getOwner().toString();
        String ownerName = Simple_Factions.playerDatabase.getPlayerName(ownerUUID);
        player.sendMessage(AQUA + "The owner of " + faction.getName() + " is " + ownerName);
    }
}
