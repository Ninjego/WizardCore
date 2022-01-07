package me.ninjego.wizardcore.listeners;

import me.ninjego.wizardcore.WizardCore;
import me.ninjego.wizardcore.spells.impl.LeapSpell;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class MoveEvent implements Listener {

    @EventHandler
    public void onMove(PlayerMoveEvent e) {
        Player plr = e.getPlayer();

        Bukkit.getScheduler().runTaskLater(WizardCore.getInstance(), new Runnable() {
            @Override
            public void run() {
                if(LeapSpell.playerList.contains(plr)) {
                    if(plr.isOnGround()) {
                        LeapSpell.playerList.remove(plr);
                    }
                }
            }
        }, 10);
    }

}
