package me.ninjego.wizardcore.spells;

import me.ninjego.wizardcore.WizardCore;
import net.minecraft.server.v1_8_R3.EnumParticle;
import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public abstract class Spell {

    public abstract String getName();

    public abstract String getDescription();

    public abstract Material getIcon();

    public abstract Double getCooldown();

    public Double getManaUsage() {
        return 0D;
    }

    public void hit(Player caster, Entity target) {}

    public void perform(Player caster) {}

    public void consumeMana(Player player) {
        Double mana = WizardCore.getInstance().getMana(player);

        if(mana >= getManaUsage()) {
            WizardCore.getInstance().setMana(player, mana - getManaUsage());

        }
    }

    public SpellType getSpellType() {
        return SpellType.NONE;
    }

    public EnumParticle getParticle() {
        return EnumParticle.CLOUD;
    }

    public double damage() {
        return 0D;
    }

    public boolean playHitSound() { return true; }

    public boolean shouldDamage() {
        return false;
    }

    public int removeDelay() { return 5; }

    public boolean toShoot() {
        return false;
    }

    public ItemStack getSpellItem() {
        ItemStack item = new ItemStack(getIcon());
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.GREEN + getName() + " Spell");
        List<String> lores = new ArrayList<String>();
        lores.add(ChatColor.GRAY + getDescription());
        meta.setLore(lores);
        item.setItemMeta(meta);
        return item;
    }

}
