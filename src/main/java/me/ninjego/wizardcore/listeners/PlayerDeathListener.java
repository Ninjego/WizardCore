package me.ninjego.wizardcore.listeners;

import me.ninjego.wizardcore.WizardCore;
import me.ninjego.wizardcore.wand.Wand;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class PlayerDeathListener implements Listener {

    @EventHandler
    public void onDeath(PlayerDeathEvent e) {
        Player player = e.getEntity();
        try {
            for(ItemStack item : e.getDrops()) {
                for(Wand wand : WizardCore.getInstance().getWandManager().getWandList()) {
                    if(item.hasItemMeta()) {
                        if(item.getItemMeta().hasDisplayName()) {
                            if(item.getItemMeta().getDisplayName().equalsIgnoreCase(wand.getWandItem().getItemMeta().getDisplayName())) {
                                if(item.getType().equals(wand.getWandItem().getType())) {
                                    e.getDrops().remove(item);
                                }
                            }
                        }
                    }
                }
            }
        } catch (Exception e1) {
            
        }
    }
}
