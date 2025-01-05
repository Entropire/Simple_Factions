package me.entropire.simple_factions.Gui;

import me.entropire.simple_factions.FactionEditor;
import me.entropire.simple_factions.objects.Faction;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class FactionMemberInfoGui extends BaseGui
{
    private final String memberName;
    private final Faction faction;

    public FactionMemberInfoGui(String memberName, Faction faction)
    {
        this.memberName = memberName;
        this.faction = faction;
    }

    @Override
    public void open(Player player)
    {
        Gui gui = new Gui("Info of " + memberName, GuiSize.Small);

        gui.addButton(13, memberName, Material.PLAYER_HEAD, "", null);

        gui.addButton(26, "Return", Material.RED_WOOL, "",
                (btn, event) -> new FactionMembersListGui(0).open(player));

        if(faction.getOwner().equals(player.getUniqueId()))
        {
            gui.addButton(11, "Make owner", Material.PAPER, "Make this member the owner of the faction",
                    (btn, event) -> {
                        FactionEditor.modifyOwner(player, memberName);
                        player.closeInventory();
                    });

            gui.addButton(15, "Kick member", Material.RED_WOOL, "Kick player from the faction",
                    (btn, event) -> {
                        FactionEditor.kick(player, memberName);
                        player.closeInventory();
                    });
        }

        player.openInventory(gui.create());
    }
}
