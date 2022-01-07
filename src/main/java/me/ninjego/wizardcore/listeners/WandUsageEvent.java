package me.ninjego.wizardcore.listeners;

import me.ninjego.wizardcore.WizardCore;
import me.ninjego.wizardcore.spells.Spell;
import me.ninjego.wizardcore.util.ArrowStraightener;
import me.ninjego.wizardcore.wand.Wand;
import net.minecraft.server.v1_8_R3.PacketPlayOutEntityDestroy;
import net.minecraft.server.v1_8_R3.PacketPlayOutWorldParticles;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Iterator;

public class WandUsageEvent implements Listener {

    public static HashMap<String, String> arrowList = new HashMap();

    @EventHandler
    public void onUse(PlayerInteractEvent e) {
        if(e.getItem() == null) return;
        if(e.getItem().getItemMeta() == null) return;
        if(e.getItem().getItemMeta().getDisplayName() == null) return;

        boolean exists = false;

        for(Wand wand : WizardCore.getInstance().getWandManager().getWandList()) {
            if(wand.getWandItem().getItemMeta().getDisplayName().equals(e.getItem().getItemMeta().getDisplayName())) {
                if(wand.getWandItem().getType().equals(e.getItem().getType())) {
                    exists = true;
                }
            }
        }

        if(!exists) {
            return;
        }

        if(e.getAction().equals(Action.RIGHT_CLICK_AIR) || e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
            Player player = e.getPlayer();
            Spell spell = WizardCore.getInstance().getSpell(player);
            WizardCore.getInstance().sendActionText(player, ChatColor.BLUE + "[" + new DecimalFormat("0.#").format(WizardCore.getInstance().getMana(player)) + "/100]");

            if(WizardCore.getInstance().getMana(player) < spell.getManaUsage()) {
                player.sendMessage(ChatColor.BLUE + "Not enough mana!");
                return;
            }

            if(WizardCore.getInstance().getCooldown(player) > System.currentTimeMillis()) {
                player.sendMessage(ChatColor.RED + "You are on cooldown!");
                return;
            } else {
                WizardCore.getInstance().setCooldown(player, (long) (System.currentTimeMillis() + (spell.getCooldown() * 1000)));
            }

            spell.perform(player);
            if(spell.toShoot()) {
                Cast(player, spell);
            }
        }
    }

    public void Cast(Player plr, Spell spell) {
        Location location = new Location(plr.getWorld(), plr.getLocation().getX(), plr.getLocation().getY() + 2.0D, plr.getLocation().getZ());
        Location loc = location.add(plr.getLocation().getDirection());
        final Arrow arrow = (Arrow) location.getWorld().spawn(loc, Arrow.class);

        arrowList.put(arrow.getUniqueId().toString(), plr.getName());
        arrow.setVelocity(plr.getLocation().getDirection().multiply(4.1D));
        new ArrowStraightener(arrow, arrow.getVelocity(), plr).start();
        this.removeAfterDelay(arrow, spell.removeDelay());
        arrow.setShooter(plr);

        Iterator var12 = plr.getWorld().getPlayers().iterator();

        while(var12.hasNext()) {
            Player p = (Player)var12.next();
            PacketPlayOutEntityDestroy packet = new PacketPlayOutEntityDestroy(new int[]{arrow.getEntityId()});
            ((CraftPlayer)p).getHandle().playerConnection.sendPacket(packet);
        }

        (new BukkitRunnable() {
            public void run() {
                if (!arrow.isDead() && !arrow.isOnGround() && arrow.isValid()) {
                    Iterator var2 = plr.getWorld().getPlayers().iterator();

                    while(var2.hasNext()) {
                        Player p = (Player)var2.next();
                        PacketPlayOutWorldParticles packet = new PacketPlayOutWorldParticles(spell.getParticle(), true, (float)arrow.getLocation().getX(), (float)arrow.getLocation().getY(), (float)arrow.getLocation().getZ(), 0.0F, 0.0F, 0.0F, 0.0F, 0, new int[0]);
                        ((CraftPlayer)p).getHandle().playerConnection.sendPacket(packet);
                    }
                } else {
                    this.cancel();
                    arrow.remove();
                }

            }
        }).runTaskTimer(WizardCore.getInstance(), 0L, 1L);
    }

    private void removeAfterDelay(final Entity e, int delay) {
        Bukkit.getScheduler().scheduleSyncDelayedTask(WizardCore.getInstance(), new Runnable() {
            public void run() {
                if (e.isValid()) {
                    e.remove();
                    if(arrowList.containsKey(e.getUniqueId().toString())) {
                        arrowList.remove(e.getUniqueId().toString());
                    }
                }

            }
        }, (long)delay);
    }


    @EventHandler(ignoreCancelled = false)
    public void onHitEvent(EntityDamageByEntityEvent e) {
        if(e.getDamager().getType().equals(EntityType.ARROW)) {
            String uuid = e.getDamager().getUniqueId().toString();
            if(arrowList.containsKey(uuid)) {
                e.setCancelled(true);
                e.setDamage(0);
                Player plr = Bukkit.getPlayer(arrowList.get(uuid));
                boolean exists = false;

                if(plr.getItemInHand() == null) return;
                if(plr.getItemInHand().getItemMeta() == null) return;
                if(plr.getItemInHand().getItemMeta().getDisplayName() == null) return;

                Wand wand = null;

                for(Wand wand1 : WizardCore.getInstance().getWandManager().getWandList()) {
                    if(wand1.getWandItem().getItemMeta().getDisplayName().equals(plr.getItemInHand().getItemMeta().getDisplayName())) {
                        if(wand1.getWandItem().getType().equals(plr.getItemInHand().getType())) {
                            exists = true;
                            wand = wand1;
                        }
                    }
                }

                if(!exists) {
                    return;
                }
                if(wand == null) return;
                Spell spell = WizardCore.getInstance().getSpell(plr);
                spell.hit(plr, e.getEntity());
                if(spell.shouldDamage()) {
                    ((LivingEntity) e.getEntity()).damage(spell.damage(), (Entity)((Arrow) e.getDamager()).getShooter());
                   // e.setDamage(spell.damage());
                }

                if(spell.playHitSound()) {
                    plr.playSound(plr.getLocation(), Sound.ORB_PICKUP, 1, 1);
                }
            }
        }
    }

}
