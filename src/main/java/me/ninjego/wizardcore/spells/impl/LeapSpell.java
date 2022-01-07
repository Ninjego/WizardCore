package me.ninjego.wizardcore.spells.impl;

import me.ninjego.wizardcore.spells.Spell;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class LeapSpell extends Spell {

    public static List<Player> playerList = new ArrayList<>();
    
    @Override
    public String getName() {
        return "Leap";
    }

    @Override
    public String getDescription() {
        return "Thrusts you forward";
    }

    @Override
    public Material getIcon() {
        return Material.FEATHER;
    }

    @Override
    public Double getCooldown() {
        return 5D;
    }

    @Override
    public Double getManaUsage() {
        return 50D;
    }

    @Override
    public void perform(Player caster) {
        caster.setVelocity(caster.getLocation().getDirection().multiply(3).setY(1));
        playerList.add(caster);
        consumeMana(caster);
    }

}
