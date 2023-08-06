package me.invis.holo.manager;

import me.invis.holo.Holo;

import java.util.HashMap;
import java.util.Map;

public class HoloManager {

    private final Map<String, Hologram> holos;

    private static boolean HOLOGRAMS_UPDATED;

    public HoloManager() {
        this.holos = new HashMap<>();
        HOLOGRAMS_UPDATED = false;

        initData();
    }

    public void updated() {
        HOLOGRAMS_UPDATED = true;
    }

    public Hologram get(String name) {
        return holos.get(name);
    }

    public void saveHologram(String name, Hologram hologram) {
        holos.put(name, hologram);
    }

    public void removeHologram(String name) {
        holos.get(name).deleteEntities();
        holos.remove(name);
    }

    public Map<String, Hologram> getHolograms() {
        return new HashMap<>(holos);
    }

    public void initData() {
        Holo.getStorageManager().getStorage().getKeys(false).forEach(name -> holos.put(name, Holo.getStorageManager().getStorage().getSerializable(name, Hologram.class)));
    }

    public void saveData() {
        if(HOLOGRAMS_UPDATED) {
            getHolograms().forEach((name, hologram) -> Holo.getStorageManager().getStorage().set(name, hologram));
            Holo.getStorageManager().saveStorageFile();
        }
    }

}
