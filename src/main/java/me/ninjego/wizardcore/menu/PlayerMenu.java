package me.ninjego.wizardcore.menu;

import org.bukkit.entity.Player;

public class PlayerMenu {

    private Player owner;

    public PlayerMenu(Player p) {
        this.owner = p;
    }

    public Player getOwner() {
        return owner;
    }

    public void setOwner(Player owner) {
        this.owner = owner;
    }

}
