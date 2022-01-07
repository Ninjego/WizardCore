package me.ninjego.wizardcore.listeners;

import me.ninjego.wizardcore.WizardCore;
import me.ninjego.wizardcore.menu.impl.SpellMenu;
import me.ninjego.wizardcore.util.DataUtil;
import me.ninjego.wizardcore.wand.Wand;
import org.bukkit.Sound;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;

public class WandDropEvent implements Listener {

    @EventHandler
    public void onDrop(PlayerDropItemEvent e) {
        if(e.getItemDrop().getItemStack().getItemMeta() == null) return;
        if(e.getItemDrop().getItemStack().getItemMeta().getDisplayName() == null) return;

        for(Wand wand : WizardCore.getInstance().getWandManager().getWandList()) {
            if(e.getItemDrop().getItemStack().getItemMeta().getDisplayName().equals(wand.getWandItem().getItemMeta().getDisplayName())) {
                if(e.getItemDrop().getItemStack().getType().equals(wand.getWandItem().getType())) {
                    e.setCancelled(true);
                    DataUtil.setupPlayer(e.getPlayer());
                    new SpellMenu(WizardCore.getPlayerMenu(e.getPlayer())).open();
                    e.getPlayer().playSound(e.getPlayer().getLocation(), Sound.ORB_PICKUP, 1, 1);
                }
            }
        }
    }

}
