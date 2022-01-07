package me.ninjego.wizardcore.spells.impl;

import me.ninjego.wizardcore.spells.Spell;
import org.bukkit.Material;

public class NoneSpell extends Spell {

    @Override
    public String getName() {
        return "None";
    }

    @Override
    public String getDescription() {
        return null;
    }

    @Override
    public Material getIcon() {
        return Material.BARRIER;
    }

    @Override
    public Double getCooldown() {
        return 0D;
    }

    @Override
    public Double getManaUsage() {
        return 0D;
    }
}
