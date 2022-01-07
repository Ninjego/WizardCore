package me.ninjego.wizardcore.spells.impl;

import me.ninjego.wizardcore.spells.Spell;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class NourishSpell extends Spell {

    @Override
    public String getName() {
        return "Nourish";
    }

    @Override
    public String getDescription() {
        return "A spell to feed yourself";
    }

    @Override
    public Material getIcon() {
        return Material.CARROT_ITEM;
    }

    @Override
    public Double getCooldown() {
        return 1D;
    }

    @Override
    public Double getManaUsage() {
        return 25D;
    }

    @Override
    public void perform(Player player) {
        if(player.getFoodLevel() < 20D) {
            player.setFoodLevel(Math.min(player.getFoodLevel() + 6, 20));
            player.setSaturation(Math.min(player.getSaturation() + 6, 20));
            consumeMana(player);
        }
        else {
            player.sendMessage(ChatColor.RED + "You are not hungry!");
        }
    }

}

