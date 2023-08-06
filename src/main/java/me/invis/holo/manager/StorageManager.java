package me.invis.holo.manager;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;

public class StorageManager {

    private FileConfiguration customConfig;
    private final File storageFile;

    private final Plugin plugin;

    public StorageManager(Plugin plugin) {
        storageFile = new File(plugin.getDataFolder(), "holos.yml");
        this.plugin = plugin;

        createHoloStorageFile();
    }

    private void createHoloStorageFile() {
        if (!storageFile.exists()) {
            storageFile.getParentFile().mkdirs();
            plugin.saveResource("holos.yml", false);
        }

        customConfig = YamlConfiguration.loadConfiguration(storageFile);
    }

    public void saveStorageFile() {
        try {
            getStorage().save(storageFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public FileConfiguration getStorage() {
        return customConfig;
    }

}
