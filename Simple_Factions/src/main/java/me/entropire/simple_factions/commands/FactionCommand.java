package me.entropire.simple_factions.commands;

import me.entropire.simple_factions.FactionEditor;
import me.entropire.simple_factions.FactionInfo;
import me.entropire.simple_factions.FactionInvitor;
import me.entropire.simple_factions.Gui.FactionGui;
import me.entropire.simple_factions.Gui.SimpleFactionGui;
import me.entropire.simple_factions.Simple_Factions;
import me.entropire.simple_factions.objects.Colors;
import me.entropire.simple_factions.objects.Faction;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.bukkit.ChatColor.RED;


public class FactionCommand implements CommandExecutor, TabCompleter
{
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args)
    {
        if (!(sender instanceof Player player))
        {
            sender.sendMessage(ChatColor.RED + "Only players can preform this command!");
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
                handleListCommand(player, args);
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
                player.sendMessage(ChatColor.YELLOW + "Invalid command!");
                break;
        }
        return false;
    }

    private void handleCreateCommand(String[] args, Player player)
    {
        if (args.length < 2)
        {
            player.sendMessage(ChatColor.YELLOW + "Command usage /faction create [Faction name]");
            return;
        }

        FactionEditor.create(player, args[1]);
    }

    private void handleDeleteCommand(Player player)
    {
        FactionEditor.delete(player);
    }

    private void handleListCommand(Player player, String[] args)
    {
        int pageNumber = 1;
        if(args.length > 1)
        {
            pageNumber = Integer.parseInt(args[1]);
        }

        FactionInfo.list(player, pageNumber);
    }

    private void handleMembersCommand(String[] args, Player player)
    {
        if(args.length < 2)
        {
            player.sendMessage(RED + "Command usage /faction members [Faction name] [Page number]");
            return;
        }

        int pageNumber = 1;
        if(args.length > 2)
        {
            pageNumber = Integer.parseInt(args[2]);
        }

        FactionInfo.members(player, args[1], pageNumber);
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
            player.sendMessage(ChatColor.YELLOW + "Command usage /faction kick [Player name]");
            return;
        }

        FactionEditor.kick(player, args[1]);
    }

    private void handleInviteCommand(String[] args, Player player)
    {
        if(args.length < 2)
        {
            player.sendMessage(ChatColor.YELLOW + "Command usage /faction invite [Player name]");
            return;
        }

        FactionInvitor.invite(player, args[1]);
    }

    private void handleJoinCommand(String[] args, Player player)
    {
        if(args.length < 2){
            player.sendMessage(ChatColor.YELLOW + "Command usage /faction join [Faction name]");
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
            player.sendMessage( ChatColor.YELLOW + "Invalid command!");
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
                player.sendMessage(ChatColor.YELLOW + "Invalid command!");
                break;
        }
    }

    private void handleModifyNameCommand(String[] args, Player player)
    {
        if(args.length < 3)
        {
            player.sendMessage(ChatColor.YELLOW + "Command usage /faction modify name [New faction name]");
            return;
        }

        FactionEditor.modifyName(player, args[2]);
    }

    private void handleModifyColorCommand(String[] args, Player player)
    {
        if(args.length < 3)
        {
            player.sendMessage(ChatColor.YELLOW + "Command usage /faction modify name [Color name]");
            return;
        }

        FactionEditor.modifyColor(player, args[2]);
    }

    private void handleModifyOwnerCommand(String[] args, Player player)
    {
        if(args.length < 3)
        {
            player.sendMessage(ChatColor.YELLOW + "Command usage /faction modify owner [Member name]");
            return;
        }

        FactionEditor.modifyOwner(player, args[2]);
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
                suggestions.add("members");
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
                        ArrayList<String> playerNames = Simple_Factions.playerDatabase.getPlayerWithNoFaction();
                        Iterator<String> playerNamesIterator = playerNames.iterator();

                        while(playerNamesIterator.hasNext())
                        {
                            Player noFactionPlayer = Bukkit.getPlayer(playerNamesIterator.next());
                            if(noFactionPlayer == null || !noFactionPlayer.isOnline())
                            {
                                playerNamesIterator.remove();
                            }
                        }

                        suggestions.addAll(playerNames);
                        break;
                    case "join":
                        suggestions.addAll(Simple_Factions.factionDatabase.getFactions());
                        break;
                    case "modify":
                        suggestions.add("name");
                        suggestions.add("color");
                        suggestions.add("owner");
                        break;
                }
                break;
            case 3:
                switch(args[1].toLowerCase())
                {
                    case "color":
                        suggestions.addAll(Colors.getColorNames());
                        break;
                    case "owner":
                        int factionId = Simple_Factions.playerDatabase.getFactionId(player);
                        Faction faction = Simple_Factions.factionDatabase.getFactionDataById(factionId);

                        if(faction != null)
                        {
                            ArrayList<String> members = faction.getMembers();
                            members.remove(player.getName());
                            suggestions.addAll(members);
                        }
                        break;
                }
                break;
        }

        return suggestions;
    }
}