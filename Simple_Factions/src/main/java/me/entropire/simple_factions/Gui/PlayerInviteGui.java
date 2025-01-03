package me.entropire.simple_factions.Gui;

import me.entropire.simple_factions.FactionInvitor;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class PlayerInviteGui extends BaseGui
{
    String playerName;

    public PlayerInviteGui(String playerName)
    {
        this.playerName = playerName;
    }

    @Override
    public void open(Player player)
    {
        Gui gui = new Gui(playerName, GuiSize.Small);

        gui.addButton(12, "Invite", Material.PAPER, "Invite this player to your faction",
                (btn, event) -> {
                    FactionInvitor.invite(player, playerName);
                    player.closeInventory();
                });

        gui.addButton(15, "Return", Material.RED_WOOL, "",
                (btn, event) -> {
                    new PlayerListGui(0).open(player);
                });

        player.openInventory(gui.create());
    }
}
