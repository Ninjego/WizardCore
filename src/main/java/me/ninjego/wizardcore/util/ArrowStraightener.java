package me.ninjego.wizardcore.util;

import me.ninjego.wizardcore.WizardCore;
import org.bukkit.Bukkit;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

public class ArrowStraightener {
    private Entity e;
    private Vector v;
    private Player player;
    private int id;

    public ArrowStraightener(Entity e, Vector first, Player player) {
        this.e = e;
        this.player = player;
        this.v = first;
    }

    public void stop() {
        Bukkit.getScheduler().cancelTask(this.id);
    }

    public void start() {
        this.id = Bukkit.getScheduler().scheduleSyncRepeatingTask(WizardCore.getInstance(), new Runnable() {
            public void run() {
                if (ArrowStraightener.this.e.isValid() && !ArrowStraightener.this.e.isOnGround()) {
                    try {
                        if (ArrowStraightener.this.e instanceof Arrow) {
                            Arrow arrow = (Arrow)ArrowStraightener.this.e;
                            arrow.setShooter(ArrowStraightener.this.player);
                        }

                        ArrowStraightener.this.e.setVelocity(ArrowStraightener.this.v);
                    } catch (Exception var2) {
                    }
                } else {
                    Bukkit.getScheduler().cancelTask(ArrowStraightener.this.id);
                }

            }
        }, 0L, 2L);
    }

}