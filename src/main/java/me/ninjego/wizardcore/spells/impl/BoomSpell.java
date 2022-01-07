package me.ninjego.wizardcore.spells.impl;

import me.ninjego.wizardcore.spells.Spell;
import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

public class BoomSpell extends Spell {
    @Override
    public String getName() {
        return "Boom";
    }

    @Override
    public String getDescription() {
        return "A high damage explosion spell";
    }

    @Override
    public Material getIcon() {
        return Material.TNT;
    }

    @Override
    public Double getCooldown() {
        return 3D;
    }

    @Override
    public boolean toShoot() {
        return true;
    }

    @Override
    public boolean shouldDamage() {
        return true;
    }

    @Override
    public Double getManaUsage() {
        return 35D;
    }

    @Override
    public int removeDelay() { 
        return 10;
    }

    @Override
    public void perform(Player caster) {
        consumeMana(caster);
    }
    
    @Override
    public double damage() {
        return 10D;
    }

    @Override
    public void hit(Player caster, Entity target) {
        if(target instanceof LivingEntity) {
            LivingEntity entity = (LivingEntity) target;
            entity.getWorld().playEffect(entity.getLocation(), Effect.EXPLOSION_HUGE, 0);
        }
    }
}
