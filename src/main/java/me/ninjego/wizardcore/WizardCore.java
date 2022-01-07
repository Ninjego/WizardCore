package me.ninjego.wizardcore;

import me.ninjego.wizardcore.commands.CommandManager;
import me.ninjego.wizardcore.data.DataManager;
import me.ninjego.wizardcore.listeners.*;
import me.ninjego.wizardcore.menu.PlayerMenu;
import me.ninjego.wizardcore.spells.Spell;
import me.ninjego.wizardcore.spells.SpellManager;
import me.ninjego.wizardcore.spells.impl.LeapSpell;
import me.ninjego.wizardcore.wand.Wand;
import me.ninjego.wizardcore.wand.WandManager;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import net.minecraft.server.v1_8_R3.ChatComponentText;
import net.minecraft.server.v1_8_R3.PacketPlayOutChat;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.text.DecimalFormat;
import java.util.HashMap;

public final class WizardCore extends JavaPlugin {

    public HashMap<Player, Spell> selectedSpell = new HashMap<>();
    public HashMap<Player, Wand> selectedWand = new HashMap<>();

    private static final HashMap<Player, PlayerMenu> playerMenuMap = new HashMap<>();
    private static final HashMap<Player, Double> playerManaMap = new HashMap<>();
    private static final HashMap<Player, Long> playerCooldownMap = new HashMap<>();

    private static WizardCore instance;

    private WandManager wandManager;

    private SpellManager spellManager;

    private DataManager dataManager;

    public static WizardCore getInstance() {
        return instance;
    }

    public SpellManager getSpellManager() {
        return spellManager;
    }

    public WandManager getWandManager() {
        return wandManager;
    }

    public DataManager getDataManager() {
        return dataManager;
    }

    @Override
    public void onEnable() {
        instance = this;
        wandManager = new WandManager();
        wandManager.load();

        spellManager = new SpellManager();
        spellManager.load();

        dataManager = new DataManager(this);

        new CommandManager();

        getLogger().info("#########################");
        getLogger().info("#      Wizard Core      #");
        getLogger().info("#  By Ninjego, SnoBrow  #");
        getLogger().info("#########################");

        loadListeners();

        getServer().getScheduler().runTaskTimer(WizardCore.getInstance(), new Runnable() {
            @Override
            public void run() {
                for(Player player : Bukkit.getOnlinePlayers()) {
                    Double mana = getMana(player);

                    int amount = 5;

                    if(mana < 100D) {
                        if(player.isSprinting()) {
                            amount = 0;
                        } else if(player.isSneaking() && player.isOnGround()) {
                            amount = amount * 2;
                        }
                        setMana(player, Math.min(mana + amount, 100));
                    }

                    sendActionText(player, ChatColor.BLUE + "[" + new DecimalFormat("0.#").format(getMana(player)) + "/100]");
                }
            }
        }, 20, 20);
    }

    public void sendActionText(Player player, String message){
        PacketPlayOutChat packet = new PacketPlayOutChat(new ChatComponentText(message), (byte)2);
        ((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);
    }

    private void loadListeners() {
        PluginManager pm = getServer().getPluginManager();
        pm.registerEvents(new WandDropEvent(), this);
        pm.registerEvents(new WandUsageEvent(), this);
        pm.registerEvents(new MenuClickListener(), this);
        pm.registerEvents(new EntityDamageListener(), this);
        pm.registerEvents(new MoveEvent(), this);
        pm.registerEvents(new PlayerDeathListener(), this);
        pm.registerEvents(new PlayerRespawnListener(), this);
    }


    @Override
    public void onDisable() {
        // Plugin shutdown logic
        getLogger().info("#########################");
        getLogger().info("#      Wizard Core      #");
        getLogger().info("#      is Disabled      #");
        getLogger().info("#########################");
    }

    public static PlayerMenu getPlayerMenu(Player plr) {
        PlayerMenu menu;
        if(!(playerMenuMap.containsKey(plr))) {
            menu = new PlayerMenu(plr);
            playerMenuMap.put(plr, menu);

            return menu;
        } else {
            return playerMenuMap.get(plr);
        }
    }

    public Spell getSpell(Player player) {
        if(selectedSpell.containsKey(player)) {
            return selectedSpell.get(player);
        } else {
            selectedSpell.put(player, spellManager.getNoneSpell());
            return selectedSpell.get(player);
        }
    }

    public Wand getWand(Player player) {
        if(selectedWand.containsKey(player)) {
            return selectedWand.get(player);
        } else {
            selectedWand.put(player, wandManager.getWand("Basic"));
            return selectedWand.get(player);
        }
    }

    public Double getMana(Player player) {
        if(playerManaMap.containsKey(player)) {
            return playerManaMap.get(player);
        }
        else {
            playerManaMap.put(player, 100D);
            return playerManaMap.get(player);
        }
    }

    public void setMana(Player player, Double mana) {
        getMana(player);
        playerManaMap.put(player, mana);
    }

    public Long getCooldown(Player player) {
        if(playerCooldownMap.containsKey(player)) {
            return playerCooldownMap.get(player);
        }
        else {
            playerCooldownMap.put(player, System.currentTimeMillis());
            return playerCooldownMap.get(player);
        }
    }

    public void setCooldown(Player player, Long cooldown) {
        getCooldown(player);
        playerCooldownMap.put(player, cooldown);
    }
}
