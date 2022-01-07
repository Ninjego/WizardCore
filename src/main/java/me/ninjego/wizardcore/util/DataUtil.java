package me.ninjego.wizardcore.util;

import me.ninjego.wizardcore.WizardCore;
import me.ninjego.wizardcore.spells.Spell;
import org.bukkit.entity.Player;

public class DataUtil {

    public static void setupPlayer(Player player) {
        if(!WizardCore.getInstance().getDataManager().getDataConfig().contains("users." + player.getUniqueId())) {
            for(int i = 0; i < 7; i++) {
                WizardCore.getInstance().getDataManager().getDataConfig().set("users." + player.getUniqueId() + ".favorites." + i, "not-set");
            }
            WizardCore.getInstance().getDataManager().saveDataConfig();
        }
    }

    public static void setFavorite(Player player, String spell, int id) {
        setupPlayer(player);
        WizardCore.getInstance().getDataManager().getDataConfig().set("users." + player.getUniqueId() + ".favorites." + id, spell.toLowerCase());
        WizardCore.getInstance().getDataManager().saveDataConfig();
    }

    public static String getSpell(Player player, int id) {
        setupPlayer(player);
        return WizardCore.getInstance().getDataManager().getDataConfig().getString("users." + player.getUniqueId() + ".favorites." + id);
    }

    public static int getFirstEmptySlot(Player player) {
        setupPlayer(player);
        for(int i = 0; i < 7; i++) {
            if(WizardCore.getInstance().getDataManager().getDataConfig().getString("users." + player.getUniqueId() + ".favorites." + i).equals("not-set")) {
                return i;
            }
        }
        return -1;
    }

    public static boolean isFavoriteSlot(int slot) {
        for(int i = 0; i < 7; i++) {
            if(slot == (i + 10)) {
                return true;
            }
        }
        return false;
    }

    public static boolean alreadyExists(Player player, String name) {
        for(int i = 0; i < 7; i++) {
            if(WizardCore.getInstance().getDataManager().getDataConfig().getString("users." + player.getUniqueId() + ".favorites." + i).equals(name)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isSet(Player player, int slot) {
        setupPlayer(player);
        if(WizardCore.getInstance().getDataManager().getDataConfig().getString("users." + player.getUniqueId() + ".favorites." + slot).equals("none-set")) {
            return false;
        }
        return true;
    }

}
