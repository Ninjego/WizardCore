package me.ninjego.wizardcore.data;

import me.ninjego.wizardcore.WizardCore;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.logging.Level;

public class DataManager {

    private WizardCore plugin;
    private FileConfiguration dataConfig = null;
    private File configFile = null;

    public DataManager(WizardCore plugin) {
        this.plugin = plugin;
        saveDataDefaultConfig();
    }

    public void reloadDataConfig() {
        if(this.configFile == null)
            this.configFile = new File(this.plugin.getDataFolder(), "data.yml");

        this.dataConfig = YamlConfiguration.loadConfiguration(this.configFile);

        InputStream defaultStream = this.plugin.getResource("data.yml");
        if(defaultStream != null) {
            YamlConfiguration defaultConfig = YamlConfiguration.loadConfiguration(new InputStreamReader(defaultStream));
            this.dataConfig.setDefaults(defaultConfig);
        }
    }

    public FileConfiguration getDataConfig() {
        if(this.dataConfig == null)
            reloadDataConfig();

        return this.dataConfig;
    }

    public void saveDataConfig() {
        if(this.dataConfig == null || this.configFile == null)
            return;

        try {
            this.getDataConfig().save(this.configFile);
        } catch (IOException e) {
            plugin.getLogger().log(Level.SEVERE, "Could not save config to " + this.configFile, e);
        }
    }

    public void saveDataDefaultConfig() {
        if(configFile == null)
            this.configFile = new File(this.plugin.getDataFolder(), "data.yml");

        if(!this.configFile.exists()) {
            this.plugin.saveResource("data.yml", false);
        }
    }

}
