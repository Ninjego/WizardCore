package me.ninjego.wizardcore.wand.impl;

import me.ninjego.wizardcore.wand.Wand;
import org.bukkit.Material;

public class BasicWand extends Wand {

    @Override
    public String getName() {
        return "Basic";
    }

    @Override
    public Material getIcon() {
        return Material.STICK;
    }



}
