package me.ninjego.wizardcore.commands.impl;

import me.ninjego.wizardcore.WizardCore;
import me.ninjego.wizardcore.commands.SubCommand;
import me.ninjego.wizardcore.wand.Wand;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class GetCommand extends SubCommand {
    @Override
    public String getName() {
        return "get";
    }

    @Override
    public void perform(Player player, String[] args) {
        if(args.length > 1) {
            for(Wand wand : WizardCore.getInstance().getWandManager().getWandList()) {
                if(wand.getName().equalsIgnoreCase(args[1])) {
                    player.getInventory().addItem(wand.getWandItem());
                    player.sendMessage(ChatColor.GREEN + "You have been given a " + ChatColor.DARK_GREEN + wand.getName() + ChatColor.GREEN + " wand");
                }
            }
        } else {
            player.sendMessage(ChatColor.RED + "Invalid Usage: /wand get (wand)");
            return;
        }
    }
}
