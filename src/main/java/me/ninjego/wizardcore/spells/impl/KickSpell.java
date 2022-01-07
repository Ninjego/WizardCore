package me.ninjego.wizardcore.spells.impl;

import me.ninjego.wizardcore.spells.Spell;
import me.ninjego.wizardcore.spells.SpellType;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public class KickSpell extends Spell {

    @Override
    public String getName() {
        return "Kick";
    }

    @Override
    public String getDescription() {
        return ChatColor.RED + "KICK ADMIN TOOL";
    }

    @Override
    public Material getIcon() {
        return Material.BARRIER;
    }

    @Override
    public Double getCooldown() {
        return 0.1D;
    }

    @Override
    public SpellType getSpellType() {
        return SpellType.ADMIN;
    }

    @Override
    public int removeDelay() { return 100; }

    @Override
    public boolean toShoot() {
        return true;
    }

    @Override
    public Double getManaUsage() {
        return 0D;
    }

    @Override
    public void hit(Player caster, Entity target) {
        if(target instanceof Player) {
            Player plr = (Player) target;
            plr.kickPlayer(ChatColor.RED + "You have been kick spelled by a Staff member!");
        }
    }
}
