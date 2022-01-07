package me.ninjego.wizardcore.commands.impl;

import me.ninjego.wizardcore.WizardCore;
import me.ninjego.wizardcore.commands.SubCommand;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.text.DecimalFormat;

public class ManaCommand extends SubCommand {

    @Override
    public String getName() {
        return "mana";
    }

    @Override
    public void perform(Player player, String[] args) {
        player.sendMessage(ChatColor.BLUE + "You have " + ChatColor.DARK_BLUE + new DecimalFormat("0.#").format(WizardCore.getInstance().getMana(player)) + ChatColor.BLUE + " mana!");
    }
}
