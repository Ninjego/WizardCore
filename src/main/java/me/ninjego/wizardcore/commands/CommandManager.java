package me.ninjego.wizardcore.commands;

import me.ninjego.wizardcore.WizardCore;
import me.ninjego.wizardcore.commands.impl.GetCommand;
import me.ninjego.wizardcore.commands.impl.ManaCommand;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class CommandManager implements CommandExecutor {

    public List<SubCommand> commandList = new ArrayList<>();

    public CommandManager() {
        WizardCore.getInstance().getCommand("wand").setExecutor(this);
        commandList.add(new GetCommand());
        commandList.add(new ManaCommand());
    }

    @Override
    public boolean onCommand(CommandSender sender, Command commandD, String label, String[] args) {
        if(!(sender instanceof Player)) {
            return true;
        }

        Player plr = (Player) sender;
        for(SubCommand command : commandList) {
            if(args.length > 0) {
                if(args[0].equalsIgnoreCase(command.getName())) {
                    if(plr.hasPermission("wizard." + command.getName().toLowerCase())) {
                        command.perform(plr, args);
                    } else {
                        plr.sendMessage(ChatColor.RED + "You do not have permissions to execute this command!");
                        return true;
                    }
                }
            }
        }

        return true;
    }
}
