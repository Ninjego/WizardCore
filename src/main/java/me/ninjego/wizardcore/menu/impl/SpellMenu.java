package me.ninjego.wizardcore.menu.impl;

import me.ninjego.wizardcore.WizardCore;
import me.ninjego.wizardcore.menu.Menu;
import me.ninjego.wizardcore.menu.PlayerMenu;
import me.ninjego.wizardcore.spells.Spell;
import me.ninjego.wizardcore.spells.SpellType;
import me.ninjego.wizardcore.util.DataUtil;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Locale;

public class SpellMenu extends Menu {
    public SpellMenu(PlayerMenu playerMenu) {
        super(playerMenu);
    }

    @Override
    public String getMenuName() {
        return "Select a spell";
    }

    @Override
    public int getSlots() {
        return 54;
    }

    @Override
    public void onClick(InventoryClickEvent event) {
        ItemStack item = event.getCurrentItem();

        Player plr = (Player) event.getWhoClicked();

        if(event.getRawSlot() == 52) {
            if(plr.hasPermission("wizard.adminmenu.open")) {
                new AdminMenu(WizardCore.getPlayerMenu(plr)).open();
                return;
            }
        }

        if(event.getRawSlot() == 49) return;

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
                        if(DataUtil.isFavoriteSlot(event.getRawSlot())) {
                            if(DataUtil.isSet(plr, (event.getRawSlot() - 10))) {
                                DataUtil.setFavorite(plr, "not-set", (event.getRawSlot() - 10));
                                new SpellMenu(WizardCore.getPlayerMenu(plr)).open();
                                plr.playSound(plr.getLocation(), Sound.ORB_PICKUP, 1, 1);
                            }
                        } else {
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
    }

    @Override
    public void setMenuItems() {
        fillSides();
        for(int i = 0; i < 7; i++) {
            getInventory().setItem(i + 10, makeItem(Material.STAINED_GLASS_PANE, i, ChatColor.RED + "Empty Favorite Slot"));
        }
        for(Spell spell : WizardCore.getInstance().getSpellManager().getSpellList()) {
            if(spell.getName().equals("None")) continue;
            if(spell.getSpellType().equals(SpellType.ADMIN)) continue;
            getInventory().addItem(spell.getSpellItem());
        }

        Player player = getPlayerMenu().getOwner();

        for(int i = 0; i < 7; i++) {
            DataUtil.setupPlayer(player);
            if(!WizardCore.getInstance().getDataManager().getDataConfig().getString("users." + player.getUniqueId() + ".favorites." + i).equals("not-set")) {
                getInventory().setItem(i + 10, WizardCore.getInstance().getSpellManager().getSpell(DataUtil.getSpell(player, i)).getSpellItem());
            }
        }
        if(player.hasPermission("wizard.adminmenu.open")) {
            getInventory().setItem(52, makeItem(Material.STAINED_GLASS_PANE, 14, ChatColor.RED + "Admin Menu", ChatColor.GRAY + "Contains Admin spells", "", ChatColor.YELLOW + "Click to open admin menu!"));
        }

        if(!WizardCore.getInstance().getSpell(player).getName().equals("None")) {
            getInventory().setItem(49, WizardCore.getInstance().getSpell(player).getSpellItem());
        } else {
            getInventory().setItem(49, makeItem(Material.STAINED_GLASS_PANE, 8, ChatColor.GRAY + "No spell selected"));
        }


    }
}
