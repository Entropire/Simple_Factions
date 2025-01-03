package me.entropire.simple_factions.Gui;

import me.entropire.simple_factions.Simple_Factions;
import me.entropire.simple_factions.objects.Colors;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.List;

public class SetFactionColorGui extends BaseGui
{
    @Override
    public void open(Player player)
    {
        Gui gui = new Gui("Set faction color", GuiSize.Small);

        int i = 0;
        List<Integer> slots = Arrays.asList(1,2,3,4,5,6,7,10,11,12,13,14,15,16,21,23);
        for (String colorName : Colors.getColorNames())
        {
            gui.addButton(slots.get(i), Colors.getChatColorWithColorName(colorName) + colorName, Colors.getMaterialWithColorName(colorName), "" , (btn, event) -> {
                String eventColorName = btn.getItemMeta().getDisplayName();
                ChatColor color = Colors.getChatColorWithColorName(ChatColor.stripColor(eventColorName));
                Simple_Factions.createFactions.get(player.getUniqueId()).setColor(color);
                new CreateFactionGui().open(player);
            });
            i++;
        }

        player.openInventory(gui.create());
    }
}
