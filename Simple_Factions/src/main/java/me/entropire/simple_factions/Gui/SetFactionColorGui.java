package me.entropire.simple_factions.Gui;

import me.entropire.simple_factions.Simple_Factions;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.List;

public class SetFactionColorGui extends BaseGui
{
    public SetFactionColorGui(Simple_Factions plugin)
    {
        super(plugin);
    }

    @Override
    public void open(Player player)
    {
        Gui gui = new Gui("Set faction color", 27);

        int i = 0;
        List<Integer> slots = Arrays.asList(1,2,3,4,5,6,7,10,11,12,13,14,15,16,21,23);
        for (String colorName : colors.colorName)
        {
            gui.addButton(slots.get(i), colors.getChatColorWithColorName(colorName) + colorName, colors.getMaterialWithColorName(colorName), "" , (btn, event) -> {
                String eventColorName = btn.getItemMeta().getDisplayName();
                ChatColor color = colors.getChatColorWithColorName(ChatColor.stripColor(eventColorName));
                plugin.createFactions.get(player.getUniqueId()).setColor(color);
                new CreateFactionGui(plugin).open(player);
            });
            i++;
        }

        player.openInventory(gui.Create());
    }
}
