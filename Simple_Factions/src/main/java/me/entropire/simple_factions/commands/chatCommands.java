package me.entropire.simple_factions.commands;

import me.entropire.simple_factions.Simple_Factions;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.bukkit.ChatColor.*;

public class ChatCommands implements CommandExecutor, TabCompleter
{
    ArrayList<String> chats = new ArrayList<>(Arrays.asList("public", "faction"));

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args)
    {
        if (!(sender instanceof Player player))
        {
            sender.sendMessage(RED + "Only players can preform this command!");
            return false;
        }

        if(!Simple_Factions.playerDatabase.hasFaction(player))
        {
            player.sendMessage(RED + "You must be in a faction to preform this action!");
            return false;
        }

        if(command.getName().equalsIgnoreCase("cf"))
        {
            Simple_Factions.playerDatabase.setChat(player.getUniqueId(), "faction");
            player.sendMessage(AQUA + "Changed chat to faction");
            return true;
        }

        if(command.getName().equalsIgnoreCase("cp"))
        {
            Simple_Factions.playerDatabase.setChat(player.getUniqueId(), "public");
            player.sendMessage(AQUA + "Changed chat to public");
            return true;
        }

        if(args.length < 1)
        {
            player.sendMessage("Command usage: /chat [public or faction]");
            return false;
        }

        if(!chats.contains(args[0]))
        {
            player.sendMessage(RED + args[0] + " Is not an valid chat type!");
        }

        Simple_Factions.playerDatabase.setChat(player.getUniqueId(), args[0]);
        player.sendMessage(AQUA + "Changed chat to " + args[0]);
        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String s, String[] args)
    {
        List<String> suggestions = new ArrayList<>();

        if(sender instanceof Player && !command.getName().equalsIgnoreCase("cp") && !command.getName().equalsIgnoreCase("cf"))
        {
            if(args.length == 1)
            {
                suggestions.add("public");
                suggestions.add("faction");
            }

            return  suggestions;
        }

        return List.of();
    }
}
