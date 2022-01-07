package me.ninjego.wizardcore.wand;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public abstract class Wand {

    public abstract String getName();

    public abstract Material getIcon();

    public ItemStack getWandItem() {
        ItemStack item = new ItemStack(getIcon());
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.GREEN + getName() + " Wand");
        item.setItemMeta(meta);
        return item;
    }

}
