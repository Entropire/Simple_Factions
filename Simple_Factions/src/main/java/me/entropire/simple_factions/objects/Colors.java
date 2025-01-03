package me.entropire.simple_factions.objects;

import org.bukkit.ChatColor;
import org.bukkit.Material;

import java.util.ArrayList;
import java.util.Arrays;

public class Colors
{
    private static final ArrayList<String> colorName = new ArrayList<>(Arrays.asList("black", "red", "aqua", "blue", "dark_aqua", "dark_blue", "dark_gray", "dark_green", "dark_purple", "dark_red", "gold", "gray", "green", "light_purple", "white", "yellow"));
    private static final ArrayList<ChatColor> chatColor = new ArrayList<>(Arrays.asList(ChatColor.BLACK, ChatColor.RED, ChatColor.AQUA, ChatColor.BLUE, ChatColor.DARK_AQUA, ChatColor.DARK_BLUE, ChatColor.DARK_GRAY, ChatColor.DARK_GREEN, ChatColor.DARK_PURPLE, ChatColor.DARK_RED, ChatColor.GOLD, ChatColor.GRAY, ChatColor.GREEN, ChatColor.LIGHT_PURPLE, ChatColor.WHITE, ChatColor.YELLOW));
    private static final ArrayList<Material> material = new ArrayList<>(Arrays.asList(Material.BLACK_WOOL, Material.RED_WOOL, Material.LIGHT_BLUE_WOOL, Material.BLUE_WOOL, Material.CYAN_WOOL, Material.BLUE_WOOL, Material.GRAY_WOOL, Material.GREEN_WOOL, Material.PURPLE_WOOL, Material.RED_WOOL, Material.ORANGE_WOOL, Material.LIGHT_GRAY_WOOL, Material.LIME_WOOL, Material.MAGENTA_WOOL, Material.WHITE_WOOL, Material.YELLOW_WOOL));

    public static String getColorNameWithChatColor(ChatColor chatColor)
    {
        int i = Colors.chatColor.indexOf(chatColor);
        return i != -1 ? Colors.colorName.get(i) : null;
    }

    public static String getColorNameWithMaterial(Material material)
    {
        int i = Colors.material.indexOf(material);
        return i != -1 ? Colors.colorName.get(i) : null;
    }

    public static ChatColor getChatColorWithColorName(String colorName)
    {
        int i = Colors.colorName.indexOf(colorName.toLowerCase());
        return i != -1 ? Colors.chatColor.get(i) : null;
    }

    public static ChatColor getChatColorWithMaterial(Material material)
    {
        int i = Colors.material.indexOf(material);
        return i != -1 ? Colors.chatColor.get(i) : null;
    }

    public static Material getMaterialWithColorName(String colorName)
    {
        int i = Colors.colorName.indexOf(colorName);
        return i != -1 ? Colors.material.get(i) : null;
    }

    public static Material getMaterialWithChatColor(ChatColor chatColor)
    {
        int i = Colors.chatColor.indexOf(chatColor);
        return i != -1 ? Colors.material.get(i) : null;
    }

    public static boolean colorNameExists(String colorName)
    {
        return colorName.contains(colorName.toLowerCase());
    }

    public static ArrayList<String> getColorNames()
    {
        return colorName;
    }
}