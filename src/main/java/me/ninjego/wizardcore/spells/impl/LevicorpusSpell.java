package me.ninjego.wizardcore.spells.impl;

import me.ninjego.wizardcore.spells.Spell;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

public class LevicorpusSpell extends Spell {

    @Override
    public String getName() {
        return "Levicorpus";
    }

    @Override
    public String getDescription() {
        return "Make enemies levitate";
    }

    @Override
    public Material getIcon() {
        return Material.BONE;
    }

    @Override
    public Double getCooldown() {
        return 0.1D;
    }

    public Double getManaUsage() {
        return 2D;
    }

    @Override
    public void perform(Player caster) {
        consumeMana(caster);
    }

    @Override
    public void hit(Player caster, Entity target) {
        if(target instanceof LivingEntity) {
            LivingEntity entity = (LivingEntity) target;
            target.setVelocity(caster.getLocation().getDirection().multiply(0.3D).setY(0.2D));
        }
    }

    public boolean shouldDamage() {
        return true;
    }

    public boolean toShoot() {
        return true;
    }

}
