package me.invis.holo;

import me.invis.holo.command.HoloCommand;
import me.invis.holo.manager.HoloManager;
import me.invis.holo.manager.Hologram;
import me.invis.holo.manager.StorageManager;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.plugin.java.JavaPlugin;

public final class Holo extends JavaPlugin {

    private static HoloManager holoManager;
    private static StorageManager storageManager;

    @Override
    public void onLoad() {
        ConfigurationSerialization.registerClass(Hologram.class);
    }

    @Override
    public void onEnable() {
        saveDefaultConfig();
        storageManager = new StorageManager(this);
        getCommand("holo").setExecutor(new HoloCommand());
        holoManager = new HoloManager();
    }

    public static HoloManager getHoloManager() {
        return holoManager;
    }

    public static StorageManager getStorageManager() {
        return storageManager;
    }

    @Override
    public void onDisable() {
        getHoloManager().getHolograms().forEach((name, holo) -> holo.removeEntities());
        getHoloManager().saveData();
    }
}
