package me.ninjego.wizardcore.spells.impl;

import me.ninjego.wizardcore.spells.Spell;
import net.minecraft.server.v1_8_R3.EnumParticle;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class ViperSpell extends Spell {
    
    @Override
    public String getName() {
        return "Viper";
    }

    @Override
    public String getDescription() {
        return "Poison you enemies";
    }

    @Override
    public Material getIcon() {
        return Material.SPIDER_EYE;
    }

    @Override
    public Double getCooldown() {
        return 3D;
    }

    @Override
    public Double getManaUsage() {
        return 25D;
    }

    @Override
    public EnumParticle getParticle() {
        return EnumParticle.SLIME;
    }

    @Override
    public boolean shouldDamage() {
        return true;
    }

    @Override
    public void perform(Player caster) {
        consumeMana(caster);
    }

    @Override
    public boolean toShoot() {
        return true;
    }

    @Override
    public void hit(Player caster, Entity target) {
        if(target instanceof LivingEntity) {
            LivingEntity entity = (LivingEntity) target;
            entity.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 100, 1, true, true));
        }
    }
}
