package me.entropire.simple_factions;

import me.entropire.simple_factions.objects.Colors;
import me.entropire.simple_factions.objects.Faction;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.UUID;

import static org.bukkit.ChatColor.*;

public class FactionEditor
{
    public static void create(Player player, String factionName)
    {
        if(Simple_Factions.plugin.playerDatabase.hasFaction(player))
        {
            player.sendMessage(RED + "You are already in a faction.");
            return;
        }
        if(Simple_Factions.plugin.factionDatabase.factionExistsByName(factionName))
        {
            player.sendMessage(RED + "There already is a faction with the name '" + factionName + "'.");
            return;
        }

        ArrayList<String> members = new ArrayList<>();
        members.add(player.getName());
        Faction faction = new Faction(0, factionName, WHITE, player.getUniqueId(), members);

        Simple_Factions.plugin.factionDatabase.addFaction(faction);
        Simple_Factions.plugin.playerDatabase.updateFactionWithPlayerName(player.getName(), Simple_Factions.plugin.factionDatabase.getFactionDataByName(factionName).getId());

        changePlayerDisplayName(player, faction.getColor() + "[" + faction.getName() + "] " + player.getName());

        player.sendMessage(GREEN + "New faction " + factionName + " created.");
    }

    public static void create(Player player)
    {
        Faction faction = Simple_Factions.plugin.createFactions.get(player.getUniqueId());

        Simple_Factions.factionDatabase.addFaction(faction);
        Simple_Factions.playerDatabase.updateFactionWithPlayerName(player.getName(), Simple_Factions.plugin.factionDatabase.getFactionDataByName(faction.getName()).getId());

        Simple_Factions.createFactions.remove(player.getUniqueId());

        changePlayerDisplayName(player, faction.getColor() + "[" + faction.getName() + "] " + player.getName());

        player.sendMessage(GREEN + "New faction " + faction.getName() + " created.");
    }

    public static void kick(Player player, String playerName)
    {
        if(!Simple_Factions.playerDatabase.hasFaction(player))
        {
            player.sendMessage(RED + "You must be in a faction to kick somebody.");
            return;
        }

        int factionId = Simple_Factions.playerDatabase.getFactionId(player);
        Faction factionData = Simple_Factions.factionDatabase.getFactionDataById(factionId);

        if(!factionData.getOwner().equals(player.getUniqueId()))
        {
            player.sendMessage(RED + "Only the owner of the faction can kick some wane.");
            return;
        }
        if(!factionData.getMembers().contains(playerName))
        {
            player.sendMessage(RED + "This player is not in your faction.");
            return;
        }
        UUID playerToKickId = Simple_Factions.playerDatabase.getPlayerUUID(playerName);
        if(factionData.getOwner().equals(playerToKickId))
        {
            player.sendMessage(RED + "The owner can not be kicked from the faction.");
            return;
        }
        Player playerToKick = player.getServer().getPlayer(playerName);

        Simple_Factions.factionDatabase.updateFactionMembers(factionId, playerName, false);
        Simple_Factions.playerDatabase.updateFactionWithPlayerName(playerName, 0);

        player.sendMessage(YELLOW + "You have kicked " + playerName + " from your faction.");
        if(playerToKick != null){
            changePlayerDisplayName(playerToKick, playerToKick.getName());
            if(playerToKick.isOnline()) playerToKick.sendMessage(YELLOW + "You have been kicked from your faction.");
        }
    }

    public static void leave(Player player)
    {
        if(!Simple_Factions.playerDatabase.hasFaction(player))
        {
            player.sendMessage(RED + "You are not in a faction.");
            return;
        }

        int factionId = Simple_Factions.playerDatabase.getFactionId(player);
        Faction factionData = Simple_Factions.factionDatabase.getFactionDataById(factionId);
        if(factionData.getOwner().equals(player.getUniqueId()))
        {
            player.sendMessage(RED + "As owner you van not leave the faction.");
            return;
        }

        Simple_Factions.factionDatabase.updateFactionMembers(factionId, player.getName(), false);
        Simple_Factions.playerDatabase.updateFactionWithPlayerName(player.getName(), 0);

        changePlayerDisplayName(player, player.getName());
        player.sendMessage(GREEN + "You left the faction: " + factionData.getName());
    }

    public static void delete(Player player)
    {
        if(!Simple_Factions.playerDatabase.hasFaction(player))
        {
            player.sendMessage(RED + "You are not in a faction.");
            return;
        }

        int factionId = Simple_Factions.playerDatabase.getFactionId(player);
        Faction faction = Simple_Factions.factionDatabase.getFactionDataById(factionId);

        if (faction == null)
        {
            player.sendMessage(RED + "Faction data not found.");
            return;
        }

        if(!faction.getOwner().equals(player.getUniqueId()))
        {
            player.sendMessage(RED + "Only the owner of a faction can delete it.");
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
                if(member.isOnline()) member.sendMessage(YELLOW + "Your faction has been deleted.");
            }
        }
        Simple_Factions.factionDatabase.deleteFaction(factionId);

        player.sendMessage(RED + "You have delete your faction " + faction.getName());
    }

    public static void modifyName(Player player, String newFactionName)
    {
        if(!Simple_Factions.playerDatabase.hasFaction(player))
        {
            player.sendMessage(RED + "You are not in a faction.");
            return;
        }

        int factionId = Simple_Factions.playerDatabase.getFactionId(player);
        Faction factionData = Simple_Factions.factionDatabase.getFactionDataById(factionId);
        if(!factionData.getOwner().equals(player.getUniqueId()))
        {
            player.sendMessage(RED + "Only the owner can modify a faction.");
            return;
        }

        if(Simple_Factions.factionDatabase.factionExistsByName(newFactionName)){
            player.sendMessage(RED + "There already is a faction with the name " + newFactionName + ".");
            return;
        }

        Simple_Factions.factionDatabase.updateFactionName(factionId, newFactionName);
        player.sendMessage(GREEN + "Changed faction name from " + factionData.getName() + " to " + newFactionName);
        factionData = Simple_Factions.factionDatabase.getFactionDataById(factionId);

        for(int i = 0; i < factionData.getMembers().size(); i++)
        {
            Player member = player.getServer().getPlayer(factionData.getMembers().get(i));

            if(member != null)
            {
                changePlayerDisplayName(member, factionData.getColor() + "[" + factionData.getName() + "] " + player.getName());
            }
        }
    }

    public static void modifyColor(Player player, String newColor)
    {
        if (!Simple_Factions.playerDatabase.hasFaction(player))
        {
            player.sendMessage(RED + "You must be in a faction to modify it.");
            return;
        }
        if(!Colors.colorNameExists(newColor)){
            player.sendMessage(RED + "Not a valid color possible options are:");
            player.sendMessage("black, red, aqua, blue, dark_aqua");
            player.sendMessage("dark_blue, dark_gray, dark_green");
            player.sendMessage("dark_purple, dark_red, gold, gray");
            player.sendMessage("green, light_purple, white, yellow");
            return;
        }

        int factionId = Simple_Factions.playerDatabase.getFactionId(player);
        Faction faction = Simple_Factions.factionDatabase.getFactionDataById(factionId);

        if(!faction.getOwner().equals(player.getUniqueId()))
        {
            player.sendMessage(RED + "Only the owner can modify a faction.");
            return;
        }

        Simple_Factions.factionDatabase.updateFactionColor(factionId, newColor);
        player.sendMessage(GREEN + "Changed faction color from " + faction.getColor() + Colors.getColorNameWithChatColor(faction.getColor())
                + GREEN + " to " + Colors.getChatColorWithColorName(newColor) + newColor);
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
            player.sendMessage(RED + "You must be in a faction to modify it.");
            return;
        }

        int factionId = Simple_Factions.playerDatabase.getFactionId(player);
        Faction faction = Simple_Factions.factionDatabase.getFactionDataById(factionId);

        if(!faction.getOwner().equals(player.getUniqueId()))
        {
            player.sendMessage(RED + "Only the owner can modify a faction.");
            return;
        }
        if(!faction.getMembers().contains(newOwnerName)){
            player.sendMessage(RED + "Player must be in your faction to make him the owner.");
            return;
        }

        String newOwnerUUID = Simple_Factions.playerDatabase.getPlayerUUID(newOwnerName).toString();
        Simple_Factions.factionDatabase.updateFactionOwner(factionId, newOwnerUUID);
        player.sendMessage(GREEN + "Change the owner of " + faction.getName() + " to " + newOwnerName);
    }

    public static void DeleteFactionCreation(Player player)
    {
        if(Simple_Factions.createFactions.containsKey(player.getUniqueId()))
        {
            Simple_Factions.createFactions.remove(player.getUniqueId());
        }
    }

    //I know there are 2 of these, but I am lazy leave me. this already is one giant mess
    private static void changePlayerDisplayName(Player player, String newDisplayName)
    {
        player.setDisplayName(newDisplayName);
        player.setPlayerListName(newDisplayName);
        player.setCustomName(newDisplayName);
    }
}
