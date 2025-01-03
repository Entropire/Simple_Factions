package me.entropire.simple_factions.commands;

import me.entropire.simple_factions.FactionEditor;
import me.entropire.simple_factions.FactionInfo;
import me.entropire.simple_factions.FactionInvitor;
import me.entropire.simple_factions.Gui.CreateFactionGui;
import me.entropire.simple_factions.Gui.FactionGui;
import me.entropire.simple_factions.Gui.SimpleFactionGui;
import me.entropire.simple_factions.Simple_Factions;
import me.entropire.simple_factions.objects.Faction;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class FactionCommand implements CommandExecutor, TabCompleter
{
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args)
    {
        if (!(sender instanceof Player player))
        {
            sender.sendMessage(ChatColor.RED + "Only players can use this command.");
            return false;
        }

        if(args.length == 0)
        {
            if(Simple_Factions.playerDatabase.hasFaction(player))
            {
                int factionId = Simple_Factions.playerDatabase.getFactionId(player);
                Faction faction = Simple_Factions.factionDatabase.getFactionDataById(factionId);
                new FactionGui(faction).open(player);
                return true;
            }

            if(Simple_Factions.createFactions.containsKey(player.getUniqueId()))
            {
               new CreateFactionGui().open(player);
            }
            else
            {
                new SimpleFactionGui().open(player);
            }

            return false;
        }

        switch (args[0].toLowerCase())
        {
            case "create":
                handleCreateCommand(args, player);
                break;
            case "delete":
                handleDeleteCommand(player);
                break;
            case "list":
                handleListCommand(player);
                break;
            case "members":
                handleMembersCommand(args, player);
                break;
            case "owner":
                handleOwnerCommand(args, player);
                break;
            case "leave":
                handleLeaveCommand(player);
                break;
            case "kick":
                handleKickCommand(args, player);
                break;
            case "invite":
                handleInviteCommand(args, player);
                break;
            case "accept":
                handleAcceptCommand(player);
                break;
            case "decline":
                handleDeclineCommand(player);
                break;
            case "join":
                handleJoinCommand(args, player);
                break;
            case "modify":
                handleModifyCommand(args, player);
                break;
            default:
                player.sendMessage("Available commands for factions are");
                player.sendMessage("/faction create");
                player.sendMessage("/faction delete");
                player.sendMessage("/faction list");
                player.sendMessage("/faction owner");
                player.sendMessage("/faction members");
                player.sendMessage("/faction leave");
                player.sendMessage("/faction kick");
                player.sendMessage("/faction invite");
                player.sendMessage("/faction join");
                player.sendMessage("/faction accept");
                player.sendMessage("/faction decline");
                player.sendMessage("/faction modify");
                break;
        }
        return false;
    }

    private void handleCreateCommand(String[] args, Player player)
    {
        if (args.length < 2)
        {
            player.sendMessage(ChatColor.RED + "/faction create [faction name].");
            return;
        }

        FactionEditor.create(player, args[1]);
    }

    private void handleDeleteCommand(Player player)
    {
        FactionEditor.delete(player);
    }

    private void handleListCommand(Player player)
    {
        FactionInfo.list(player);
    }

    private void handleMembersCommand(String[] args, Player player)
    {
        String factionName = null;
        if(args.length > 1) factionName = args[1];

        FactionInfo.members(player, factionName);
    }

    private void  handleOwnerCommand(String[] args, Player player)
    {
        String factionName = null;
        if(args.length > 1) factionName = args[1];

        FactionInfo.owner(player, factionName);
    }

    private void handleLeaveCommand(Player player)
    {
        FactionEditor.leave(player);
    }

    private void handleKickCommand(String[] args, Player player)
    {
        if (args.length < 2) {
            player.sendMessage(ChatColor.RED + "/faction kick [Player name]");
            return;
        }

        FactionEditor.kick(player, args[1]);
    }

    private void handleInviteCommand(String[] args, Player player)
    {
        if(args.length < 2)
        {
            player.sendMessage(ChatColor.RED + "/faction invite [Player name]");
            return;
        }

        FactionInvitor.invite(player, args[1]);
    }

    private void handleJoinCommand(String[] args, Player player)
    {
        if(args.length < 2){
            player.sendMessage(ChatColor.RED + "/faction join [Faction name]");
            return;
        }

        FactionInvitor.join(player, args[1]);
    }

    private void handleAcceptCommand(Player player)
    {
        FactionInvitor.accept(player);
    }

    private void handleDeclineCommand(Player player)
    {
        FactionInvitor.decline(player);
    }

    private void handleModifyCommand(String[] args, Player player){
        if(args.length < 2)
        {
            player.sendMessage("Available commands for factions modify are");
            player.sendMessage("/faction modify name [new faction name]");
            player.sendMessage("/faction modify color [color name]");
            player.sendMessage("/faction modify owner [member name]");
            return;
        }
        switch (args[1].toLowerCase())
        {
            case "name":
                handleModifyNameCommand(args, player);
                break;
            case "color":
                handleModifyColorCommand(args, player);
                break;
            case "owner":
                handleModifyOwnerCommand(args, player);
                break;
            default:
                player.sendMessage("Available commands for factions modify are");
                player.sendMessage("/faction modify name [new faction name]");
                player.sendMessage("/faction modify color [color name]");
                player.sendMessage("/faction modify owner [member name]");
                break;
        }
    }

    private void handleModifyNameCommand(String[] args, Player player)
    {
        if(args.length < 3)
        {
            player.sendMessage(ChatColor.RED + "/faction modify name [new Faction name]");
            return;
        }

        FactionEditor.modifyName(player, args[2]);
    }

    private void handleModifyColorCommand(String[] args, Player player)
    {
        if(args.length < 3)
        {
            player.sendMessage(ChatColor.RED + "/faction modify name [Color]");
            return;
        }

        FactionEditor.modifyColor(player, args[2]);
    }

    private void handleModifyOwnerCommand(String[] args, Player player)
    {
        if(args.length < 3)
        {
            player.sendMessage(ChatColor.RED + "/faction modify owner [member name]");
            return;
        }

        FactionEditor.modifyOwner(player, args[2]);
    }

    private void handleException(Exception ex, Player player, String message)
    {
        ex.printStackTrace();
        Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.RED + message + ex.getMessage());
        player.sendMessage(ChatColor.RED + message);
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String s, String[] args)
    {
        if(!(sender instanceof Player))
        {
            return List.of();
        }

        Player player = (Player)sender;

        List<String> suggestions = new ArrayList<>();

        switch (args.length)
        {
            case 1:
                suggestions.add("create");
                suggestions.add("delete");
                suggestions.add("list");
                suggestions.add("owner");
                suggestions.add("member");
                suggestions.add("leave");
                suggestions.add("kick");
                suggestions.add("invite");
                suggestions.add("join");
                suggestions.add("accept");
                suggestions.add("decline");
                suggestions.add("modify");
                break;
            case 2:
                switch(args[0].toLowerCase())
                {
                    case "kick":
                        if(Simple_Factions.playerDatabase.hasFaction(player))
                        {
                            int factionId = Simple_Factions.playerDatabase.getFactionId(player);
                            Faction faction = Simple_Factions.factionDatabase.getFactionDataById(factionId);
                            ArrayList<String> members = faction.getMembers();
                            members.remove(player.getName());

                            suggestions.addAll(members);
                        }
                        break;
                    case "invite":
                        player.sendMessage("you made it");
                        suggestions.addAll(Simple_Factions.playerDatabase.getPlayerWithNoFaction());
                        break;
                    case "join":
                        suggestions.addAll(Simple_Factions.factionDatabase.getFactions());
                        break;
                    case "modify":
                        player.sendMessage("you made it to modify good job");
                        suggestions.add("name");
                        suggestions.add("color");
                        suggestions.add("owner");
                        break;
                }
                break;
        }

        return suggestions;
    }
}