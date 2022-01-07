package me.ninjego.wizardcore.wand;

import me.ninjego.wizardcore.spells.Spell;
import me.ninjego.wizardcore.wand.impl.AdvancedWand;
import me.ninjego.wizardcore.wand.impl.BasicWand;

import java.util.ArrayList;
import java.util.List;

public class WandManager {

    private List<Wand> wandList = new ArrayList<>();

    public void load() {
        wandList.clear();
        wandList.add(new BasicWand());
        wandList.add(new AdvancedWand());
    }

    public List<Wand> getWandList() {
        return wandList;
    }

    public Wand getWand(String name) {
        for(Wand wand : wandList) {
            if(wand.getName().equalsIgnoreCase(name)) {
                return wand;
            }
        }
        return null;
    }
}
