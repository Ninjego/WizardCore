package me.ninjego.wizardcore.menu.impl;

import me.ninjego.wizardcore.WizardCore;
import me.ninjego.wizardcore.menu.Menu;
import me.ninjego.wizardcore.menu.PlayerMenu;
import me.ninjego.wizardcore.spells.Spell;
import me.ninjego.wizardcore.spells.SpellType;
import me.ninjego.wizardcore.util.DataUtil;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class AdminMenu extends Menu {

    public AdminMenu(PlayerMenu playerMenu) {
        super(playerMenu);
    }

    @Override
    public String getMenuName() {
        return "Admin Menu";
    }

    @Override
    public int getSlots() {
        return 45;
    }

    @Override
    public void onClick(InventoryClickEvent event) {
        ItemStack item = event.getCurrentItem();

        Player plr = (Player) event.getWhoClicked();
        if(item.getItemMeta() == null) return;
        if(item.getItemMeta().getDisplayName() == null) return;
        if(event.isLeftClick() && !event.isShiftClick()) {
            for(Spell spell : WizardCore.getInstance().getSpellManager().getSpellList()) {
                if(spell.getSpellItem().getItemMeta().getDisplayName().equals(item.getItemMeta().getDisplayName())) {
                    if(spell.getSpellItem().getType().equals(item.getType())) {
                        WizardCore.getInstance().selectedSpell.put(plr, spell);
                        event.getWhoClicked().closeInventory();
                        event.getWhoClicked().sendMessage(ChatColor.GREEN + "You have selected " + spell.getName() + " spell");
                    }
                }
            }
        } else if(event.isShiftClick()) {
            for (Spell spell : WizardCore.getInstance().getSpellManager().getSpellList()) {
                if (spell.getSpellItem().getItemMeta().getDisplayName().equals(item.getItemMeta().getDisplayName())) {
                    if (spell.getSpellItem().getType().equals(item.getType())) {
                        int slot = DataUtil.getFirstEmptySlot(plr);
                        if (slot == -1) return;
                        if(DataUtil.alreadyExists(plr, spell.getName().toLowerCase())) {
                            plr.playSound(plr.getLocation(), Sound.ENDERMAN_TELEPORT, 1, 1);
                            return;
                        }
                        DataUtil.setFavorite(plr, spell.getName(), slot);
                        new SpellMenu(WizardCore.getPlayerMenu(plr)).open();
                        plr.playSound(plr.getLocation(), Sound.ORB_PICKUP, 1, 1);
                    }
                }
            }
        }
    }

    @Override
    public void setMenuItems() {
        fillSides();
        for(Spell spell : WizardCore.getInstance().getSpellManager().getSpellList()) {
            if(spell.getName().equals("None")) continue;
            if(spell.getSpellType().equals(SpellType.NONE)) continue;
            getInventory().addItem(spell.getSpellItem());
        }
    }
}
