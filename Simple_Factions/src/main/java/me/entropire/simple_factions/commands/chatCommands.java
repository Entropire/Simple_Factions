package me.entropire.simple_factions.commands;

import me.entropire.simple_factions.Simple_Factions;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.bukkit.ChatColor.*;

public class chatCommands implements CommandExecutor, TabCompleter
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

        if(args.length < 1 || !chats.contains(args[0].toLowerCase()))
        {
            player.sendMessage("Command usage: /chat [public or faction]");
            return false;
        }

        if(!Simple_Factions.playerDatabase.hasFaction(player))
        {
            player.sendMessage(YELLOW + "You must be in a faction to change the chat.");
            return false;
        }
        Simple_Factions.playerDatabase.setChat(player.getUniqueId(), args[0].toLowerCase());
        player.sendMessage(AQUA + "Changed chat to " + args[0]);

        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String s, String[] args)
    {
        List<String> suggestions = new ArrayList<>();

        if(sender instanceof Player)
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
