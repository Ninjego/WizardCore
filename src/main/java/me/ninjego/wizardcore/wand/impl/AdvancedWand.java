package me.ninjego.wizardcore.wand.impl;

import me.ninjego.wizardcore.wand.Wand;
import org.bukkit.Material;

public class AdvancedWand extends Wand {

    @Override
    public String getName() {
        return "Advanced";
    }

    @Override
    public Material getIcon() {
        return Material.BLAZE_ROD;
    }
}
