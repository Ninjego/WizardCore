package me.ninjego.wizardcore.listeners;

import me.ninjego.wizardcore.WizardCore;
import me.ninjego.wizardcore.spells.impl.LeapSpell;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

public class EntityDamageListener implements Listener {

    @EventHandler
    public void onDamage(EntityDamageEvent e) {
        if(!(e.getEntity() instanceof Player)) {
            return;
        }

        Player player = (Player) e.getEntity();
        if(LeapSpell.playerList.contains(player)) {
            if(e.getCause().equals(EntityDamageEvent.DamageCause.FALL)) {
                e.setCancelled(true);
                LeapSpell.playerList.remove(player);
            }
        }    
    }
}
