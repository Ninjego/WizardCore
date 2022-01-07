package me.ninjego.wizardcore.menu;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class Menu implements InventoryHolder {

    protected PlayerMenu playerMenu;
    protected Inventory inventory;
    protected ItemStack FILLER_GLASS = makeItem(Material.STAINED_GLASS_PANE, 7, " ");

    public Menu(PlayerMenu playerMenu) {
        this.playerMenu = playerMenu;
    }

    public abstract String getMenuName();

    public abstract int getSlots();

    public abstract void onClick(InventoryClickEvent event);

    public abstract void setMenuItems();

    public PlayerMenu getPlayerMenu() {
        return playerMenu;
    }

    public void setPlayerMenu(PlayerMenu playerMenu) {
        this.playerMenu = playerMenu;
    }

    public void open() {
        inventory = Bukkit.createInventory(this, getSlots(), getMenuName());

        setMenuItems();

        playerMenu.getOwner().openInventory(inventory);
    }

    @Override
    public Inventory getInventory() {
        return this.inventory;
    }

    public ItemStack makeItem(Material material, String displayName, String... lore) {

        ItemStack item = new ItemStack(material);
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.setDisplayName(displayName);

        itemMeta.setLore(Arrays.asList(lore));
        item.setItemMeta(itemMeta);

        return item;
    }

    private List<Integer> sideSlots = new ArrayList<>();

    public void fillTop() {
        for(int i = 0; i < 9; i++) {
            this.inventory.setItem(i, FILLER_GLASS);
        }
    }

    public void fillBottom() {
        for(int i =0; i < 9; i++) {
            this.inventory.setItem(this.inventory.getSize() - i - 1, FILLER_GLASS);
        }
    }

    public void fillSides() {
        int size = inventory.getSize();
        int rows = size / 9;

        if(rows >= 3) {
            for (int i = 0; i <= 8; i++) {
                this.inventory.setItem(i, FILLER_GLASS);

                sideSlots.add(i);
            }

            for(int s = 8; s < (this.inventory.getSize() - 9); s += 9) {
                int lastSlot = s + 1;
                this.inventory.setItem(s, FILLER_GLASS);
                this.inventory.setItem(lastSlot, FILLER_GLASS);

                sideSlots.add(s);
                sideSlots.add(lastSlot);
            }

            for (int lr = (this.inventory.getSize() - 9); lr < this.inventory.getSize(); lr++) {
                this.inventory.setItem(lr, FILLER_GLASS);

                sideSlots.add(lr);
            }
        }
    }

    public ItemStack makeItem(Material material, int data, String displayName, String... lore) {

        ItemStack item = new ItemStack(material, 1, (short) data);
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.setDisplayName(displayName);

        itemMeta.setLore(Arrays.asList(lore));
        item.setItemMeta(itemMeta);

        return item;
    }

}
