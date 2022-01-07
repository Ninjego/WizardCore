package me.ninjego.wizardcore.listeners;

import me.ninjego.wizardcore.WizardCore;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;

public class PlayerRespawnListener implements Listener {

    @EventHandler
    public void onRespawn(PlayerRespawnEvent e) {
        Player player = e.getPlayer();
        player.getInventory().addItem(WizardCore.getInstance().getWand(player).getWandItem());
    }

}
