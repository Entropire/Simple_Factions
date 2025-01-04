package me.entropire.simple_factions;

import me.entropire.simple_factions.objects.Faction;
import me.entropire.simple_factions.objects.Invite;
import me.entropire.simple_factions.objects.Join;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import static org.bukkit.ChatColor.GREEN;
import static org.bukkit.ChatColor.RED;

public class FactionInvitor
{
    public static void invite(Player player, String invitedPlayerName)
    {
        if(!Simple_Factions.playerDatabase.hasFaction(player))
        {
            player.sendMessage(RED + "You must be in a faction to preform this action!");
            return;
        }

        int factionId = Simple_Factions.playerDatabase.getFactionId(player);
        Faction faction = Simple_Factions.factionDatabase.getFactionDataById(factionId);

        if(!faction.getOwner().equals(player.getUniqueId()))
        {
            player.sendMessage(RED  + "Only the owner can invite player to the faction." );
            return;
        }

        Player invitedPlayer = Bukkit.getServer().getPlayer(invitedPlayerName);
        if (invitedPlayer == null || !invitedPlayer.isOnline()) 
        {
            player.sendMessage(ChatColor.RED + (invitedPlayer == null ? invitedPlayerName + " is not a player." : invitedPlayerName + " is not online."));
            return;
        }
        
        if (Simple_Factions.playerDatabase.hasFaction(invitedPlayer))
        {
            player.sendMessage(RED  + invitedPlayerName + " is already in a faction." );
            return;
        }
        
        if(Simple_Factions.invites.containsKey(invitedPlayer.getUniqueId()))
        {
            player.sendMessage(RED  + invitedPlayerName + " already is invited to a faction.");
            return;
        }
        
        Invite invite = new Invite(invitedPlayer.getUniqueId(), factionId, System.currentTimeMillis() + 30000);
        Simple_Factions.invites.put(invitedPlayer.getUniqueId(), invite);

        invitedPlayer.sendMessage(GREEN + "You have been invited for the faction " + faction.getName());
        invitedPlayer.sendMessage("to accept type: /faction accept");
        invitedPlayer.sendMessage("to decline type: /faction decline");

        player.sendMessage(GREEN + "Invited " + invitedPlayerName + " to your faction.");
    }

    public static void join(Player player, String factionName)
    {
        if(Simple_Factions.playerDatabase.hasFaction(player))
        {
            player.sendMessage(RED + "You are already in a faction.");
            return;
        }
        if(!Simple_Factions.factionDatabase.factionExistsByName(factionName))
        {
            player.sendMessage(RED + "faction " + factionName + " does not exist.");
            return;
        }

        Faction faction = Simple_Factions.factionDatabase.getFactionDataByName(factionName);
        Player receiver = Bukkit.getPlayer(faction.getOwner());
        if (receiver == null)
        {
            player.sendMessage(RED + "Something went wrong while making a join request.");
            return;
        }
        Join join = new Join(faction.getOwner(), player.getUniqueId(), faction.getId(), System.currentTimeMillis() + 30000);
        Simple_Factions.joins.put(faction.getOwner(), join);

        player.sendMessage(GREEN + "You have send a join request to " + faction.getName());

        receiver.sendMessage(GREEN + player.getName() + " wants to join your faction.");
        receiver.sendMessage("to accept type: /faction accept");
        receiver.sendMessage("to decline type: /faction decline");
    }

    public static void accept(Player player)
    {
        if (Simple_Factions.invites.containsKey(player.getUniqueId()))
        {
            Invite invite = Simple_Factions.invites.get(player.getUniqueId());
            Faction faction = Simple_Factions.factionDatabase.getFactionDataById(invite.factionId());

            Simple_Factions.factionDatabase.updateFactionMembers(invite.factionId(), player.getName(), true);
            Simple_Factions.playerDatabase.updateFactionWithPlayerName(player.getName(), invite.factionId());

            changePlayerDisplayName(player, faction.getColor() + "[" + faction.getName() + "] " + player.getName());
            player.sendMessage(GREEN + "you have joined the faction " + faction.getName());

            Simple_Factions.invites.remove(player.getUniqueId());
            return;
        }

        if(Simple_Factions.joins.containsKey(player.getUniqueId()))
        {
            Join join = Simple_Factions.joins.get(player.getUniqueId());
            String senderName = Simple_Factions.playerDatabase.getPlayerName(join.sender().toString());
            Faction faction = Simple_Factions.factionDatabase.getFactionDataById(join.factionId());

            Simple_Factions.factionDatabase.updateFactionMembers(join.factionId(), senderName, true);
            Simple_Factions.playerDatabase.updateFactionWithPlayerUUID(join.sender(), join.factionId());

            Player sender = Bukkit.getPlayer(join.sender());

            if(sender != null)
            {
                changePlayerDisplayName(sender, faction.getColor() + "[" + faction.getName() + "] " + sender.getName());
                sender.sendMessage(GREEN + "You have joined the faction " + faction.getName());
            }
            player.sendMessage(GREEN + senderName + " is now part of your faction.");

            return;
        }

        player.sendMessage(RED  + "You don't have any pending invites or requests." );
    }

    public static void decline(Player player)
    {
        if (Simple_Factions.invites.containsKey(player.getUniqueId()))
        {
            Invite invite = Simple_Factions.invites.get(player.getUniqueId());
            Faction faction = Simple_Factions.factionDatabase.getFactionDataById(invite.factionId());

            player.sendMessage(GREEN + "you have declined the faction invitation from " + faction.getName());

            Simple_Factions.invites.remove(player.getUniqueId());
            return;
        }

        if(Simple_Factions.joins.containsKey(player.getUniqueId()))
        {
            Join join = Simple_Factions.joins.get(player.getUniqueId());
            String senderName = Simple_Factions.playerDatabase.getPlayerName(join.sender().toString());
            player.sendMessage(RED + "You have declined the join request from " + senderName);

            Player sender = Bukkit.getPlayer(join.sender());

            if(sender != null)
            {
                sender.sendMessage(GREEN + player.getName() + " declined your join request.");
            }

            Simple_Factions.joins.remove(player.getUniqueId());
            return;
        }

        player.sendMessage(RED  + "You don't have any pending invites or requests." );
    }

    //I know there are 2 of these, but I am lazy leave me. this already is one giant mess
    private static void changePlayerDisplayName(Player player, String newDisplayName)
    {
        player.setDisplayName(newDisplayName);
        player.setPlayerListName(newDisplayName);
        player.setCustomName(newDisplayName);
    }
}
