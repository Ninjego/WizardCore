package me.ninjego.wizardcore.spells;

import me.ninjego.wizardcore.WizardCore;
import me.ninjego.wizardcore.spells.impl.*;

import java.util.ArrayList;
import java.util.List;

public class SpellManager {

    private List<Spell> spellList = new ArrayList<>();

    private NoneSpell noneSpell;

    public void load() {
        spellList.add(new NourishSpell());
        spellList.add(new KickSpell());
        spellList.add(new LeapSpell());
        spellList.add(new ViperSpell());
        spellList.add(new BoomSpell());
        spellList.add(new LevicorpusSpell());

        noneSpell = new NoneSpell();
        spellList.add(noneSpell);

        for(Spell spell : spellList) {
            WizardCore.getInstance().getLogger().info(" - Loaded " + spell.getName());
        }
    }

    public NoneSpell getNoneSpell() {
        return noneSpell;
    }

    public List<Spell> getSpellList() {
        return spellList;
    }

    public Spell getSpell(String name) {
        for(Spell spell : spellList) {
            if(spell.getName().equalsIgnoreCase(name)) {
                return spell;
            }
        }
        return null;
    }
}
