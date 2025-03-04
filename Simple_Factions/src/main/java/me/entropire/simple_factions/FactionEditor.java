package me.entropire.simple_factions;

import me.entropire.simple_factions.objects.Colors;
import me.entropire.simple_factions.objects.Faction;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.UUID;

import static org.bukkit.ChatColor.*;

public class FactionEditor
{
    public static void create(Player player, String factionName)
    {
        if(Simple_Factions.playerDatabase.hasFaction(player))
        {
            player.sendMessage(RED + "You are already in a faction.");
            return;
        }

        factionName = factionName.toLowerCase();

        if(factionName.matches(".*[^a-zA-Z].*"))
        {
            player.sendMessage(ChatColor.RED + "Special characters are not allowed in an faction names!");
            return;
        }

        if(Simple_Factions.plugin.getSettingsConfig().getBoolean("enable-bannedNames", true) && Simple_Factions.plugin.getBannedWordsConfig().getStringList("BannedNames").contains(factionName))
        {
            player.sendMessage(ChatColor.RED + "This name has been banned and cannot be used!");
            return;
        }

        if(Simple_Factions.factionDatabase.factionExistsByName(factionName))
        {
            player.sendMessage(RED + "The name " + factionName + " is already in use by another faction!");
            return;
        }

        ArrayList<String> members = new ArrayList<>();
        members.add(player.getName());
        Faction faction = new Faction(0, factionName, WHITE, player.getUniqueId(), members);

        Simple_Factions.factionDatabase.addFaction(faction);
        Simple_Factions.playerDatabase.updateFactionWithPlayerName(player.getName(), Simple_Factions.factionDatabase.getFactionDataByName(factionName).getId());

        changePlayerDisplayName(player, faction.getColor() + "[" + faction.getName() + "] " + player.getName());

        player.sendMessage(AQUA + "New faction " + factionName + " created");
    }

    public static void kick(Player player, String playerName)
    {
        if(!Simple_Factions.playerDatabase.hasFaction(player))
        {
            player.sendMessage(RED + "You must be in a faction to preform this action!");
            return;
        }

        int factionId = Simple_Factions.playerDatabase.getFactionId(player);
        Faction factionData = Simple_Factions.factionDatabase.getFactionDataById(factionId);
        if(!factionData.getOwner().equals(player.getUniqueId()))
        {
            player.sendMessage(RED + "You must be the owner of the faction to preform this action!");
            return;
        }
        if(!factionData.getMembers().contains(playerName))
        {
            player.sendMessage(RED + "This player is not a part of your faction!");
            return;
        }
        UUID playerToKickId = Simple_Factions.playerDatabase.getPlayerUUID(playerName);
        if(factionData.getOwner().equals(playerToKickId))
        {
            player.sendMessage(RED + "You can not kick the owner from the faction!");
            return;
        }
        Player playerToKick = player.getServer().getPlayer(playerName);

        Simple_Factions.factionDatabase.updateFactionMembers(factionId, playerName, false);
        Simple_Factions.playerDatabase.updateFactionWithPlayerName(playerName, 0);

        player.sendMessage(AQUA + "You have kicked " + playerName + " out of your faction");
        if(playerToKick != null){
            changePlayerDisplayName(playerToKick, playerToKick.getName());
            if(playerToKick.isOnline()) playerToKick.sendMessage(AQUA + "You have been kicked from your faction");
        }
    }

    public static void leave(Player player)
    {
        if(!Simple_Factions.playerDatabase.hasFaction(player))
        {
            player.sendMessage(RED + "You must be in a faction to preform this action!");
            return;
        }

        int factionId = Simple_Factions.playerDatabase.getFactionId(player);
        Faction factionData = Simple_Factions.factionDatabase.getFactionDataById(factionId);
        if(factionData.getOwner().equals(player.getUniqueId()))
        {
            player.sendMessage(RED + "You can not preform this action as owner of the faction!");
            return;
        }

        Simple_Factions.factionDatabase.updateFactionMembers(factionId, player.getName(), false);
        Simple_Factions.playerDatabase.updateFactionWithPlayerName(player.getName(), 0);

        changePlayerDisplayName(player, player.getName());
        player.sendMessage(AQUA + "You have left your faction");
    }

    public static void delete(Player player)
    {
        if(!Simple_Factions.playerDatabase.hasFaction(player))
        {
            player.sendMessage(RED + "You must be in a faction to preform this action!");
            return;
        }

        int factionId = Simple_Factions.playerDatabase.getFactionId(player);
        Faction faction = Simple_Factions.factionDatabase.getFactionDataById(factionId);

        if (faction == null)
        {
            player.sendMessage(RED + "Your faction data has not been found!");
            return;
        }

        if(!faction.getOwner().equals(player.getUniqueId()))
        {
            player.sendMessage(RED + "You must be the owner of the faction to preform this action!");
            return;
        }

        ArrayList<String> members = faction.getMembers();
        for (String memberName : members) {
            UUID memberUUID = Simple_Factions.playerDatabase.getPlayerUUID(memberName);
            Player member = Bukkit.getPlayer(memberUUID);

            Simple_Factions.playerDatabase.updateFactionWithPlayerUUID(memberUUID, 0);

            if(member != null)
            {
                changePlayerDisplayName(member, member.getName());
                if(member.isOnline() && !faction.getOwner().equals(memberUUID)) member.sendMessage(AQUA + "You have been kicked from your faction because the faction has been deleted");
            }
        }
        Simple_Factions.factionDatabase.deleteFaction(factionId);

        player.sendMessage(AQUA + "You have deleted your faction");
    }

    public static void modifyName(Player player, String newFactionName)
    {
        if(!Simple_Factions.playerDatabase.hasFaction(player))
        {
            player.sendMessage(RED + "You must be in a faction to preform this action!");
            return;
        }

        int factionId = Simple_Factions.playerDatabase.getFactionId(player);
        Faction factionData = Simple_Factions.factionDatabase.getFactionDataById(factionId);
        if(!factionData.getOwner().equals(player.getUniqueId()))
        {
            player.sendMessage(RED + "You must be the owner of the faction to preform this action!");
            return;
        }

        newFactionName = newFactionName.toLowerCase();

        if(newFactionName.matches(".*[^a-zA-Z].*"))
        {
            player.sendMessage(ChatColor.RED + "Special characters are not allowed in an faction names!");
            return;
        }

        if(Simple_Factions.plugin.getSettingsConfig().getBoolean("enable-bannedNames", true) && Simple_Factions.plugin.getBannedWordsConfig().getStringList("BannedNames").contains(newFactionName))
        {
            player.sendMessage(ChatColor.RED + "This name has been banned and cannot be used!");
            return;
        }

        if(Simple_Factions.factionDatabase.factionExistsByName(newFactionName)){
            player.sendMessage(RED + "the name ''" + newFactionName + "' is already in use by another faction!");
            return;
        }

        Simple_Factions.factionDatabase.updateFactionName(factionId, newFactionName);
        player.sendMessage(AQUA + "You have changed your faction name to " + newFactionName);
        factionData = Simple_Factions.factionDatabase.getFactionDataById(factionId);

        for(int i = 0; i < factionData.getMembers().size(); i++)
        {
            Player member = player.getServer().getPlayer(factionData.getMembers().get(i));

            if(member != null)
            {
                changePlayerDisplayName(member, factionData.getColor() + "[" + factionData.getName() + "] " + member.getName());
            }
        }
    }

    public static void modifyColor(Player player, String newColor)
    {
        if (!Simple_Factions.playerDatabase.hasFaction(player))
        {
            player.sendMessage(RED + "You must be in a faction to preform this action!");
            return;
        }
       
        if(!Colors.colorNameExists(newColor)){
            player.sendMessage(RED + newColor + " is not a valid color!");
            return;
        }

        int factionId = Simple_Factions.playerDatabase.getFactionId(player);
        Faction faction = Simple_Factions.factionDatabase.getFactionDataById(factionId);

        if(!faction.getOwner().equals(player.getUniqueId()))
        {
            player.sendMessage(RED + "You must be the owner of the faction to preform this action!");
            return;
        }

        Simple_Factions.factionDatabase.updateFactionColor(factionId, newColor);
        player.sendMessage(AQUA + "You have changed your faction color to " + Colors.getChatColorWithColorName(newColor) + newColor);
        faction = Simple_Factions.factionDatabase.getFactionDataById(factionId);

        for(int i = 0; i < faction.getMembers().size(); i++)
        {
            Player member = player.getServer().getPlayer(faction.getMembers().get(i));

            if(member != null)
            {
                changePlayerDisplayName(member, faction.getColor() + "[" + faction.getName() + "] " + player.getName());
            }
        }
    }

    public static void modifyOwner(Player player, String newOwnerName)
    {
        if (!Simple_Factions.playerDatabase.hasFaction(player))
        {
            player.sendMessage(RED + "You must be in a faction to preform this action!");
            return;
        }

        int factionId = Simple_Factions.playerDatabase.getFactionId(player);
        Faction faction = Simple_Factions.factionDatabase.getFactionDataById(factionId);

        if(!faction.getOwner().equals(player.getUniqueId()))
        {
            player.sendMessage(RED + "You must be the owner of the faction to preform this action!");
            return;
        }
        if(!faction.getMembers().contains(newOwnerName)){
            player.sendMessage(RED + "This player is not a part of your faction!");
            return;
        }

        String newOwnerUUID = Simple_Factions.playerDatabase.getPlayerUUID(newOwnerName).toString();
        Simple_Factions.factionDatabase.updateFactionOwner(factionId, newOwnerUUID);
        player.sendMessage(AQUA + "You have promoted " + newOwnerName + " to owner");
    }

    //I know there are 2 of these, but I am lazy leave me. this already is one giant mess
    private static void changePlayerDisplayName(Player player, String newDisplayName)
    {
        player.setDisplayName(newDisplayName);
        player.setPlayerListName(newDisplayName);
        player.setCustomName(newDisplayName);
    }
}
