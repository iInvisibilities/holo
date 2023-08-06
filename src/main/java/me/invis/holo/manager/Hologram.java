package me.invis.holo.manager;

import me.invis.holo.setting.Constants;
import org.bukkit.Location;
import org.bukkit.Nameable;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;

import java.util.*;
import java.util.stream.Collectors;

public class Hologram implements ConfigurationSerializable {

    private final Location location;
    private final Map<Integer, ArmorStand> content;

    public Hologram(Location location) {
        this.location = location;
        this.content = new HashMap<>();
    }

    public Hologram(Map<String, Object> data) {
        this((Location) data.get("location"));
        addLines((List<String>) data.get("content"));
    }

    public Location getLocation() {
        return location;
    }

    public List<String> getContent() {
        return this.content.values().stream().map(Nameable::getCustomName).collect(Collectors.toList());
    }

    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> serialized = new HashMap<>();
        serialized.put("location", this.location);
        serialized.put("content", getContent());

        return serialized;
    }

    private void reorganizeLines(int removedLineIndex) {
        int max = Collections.max(this.content.keySet());

        List<String> lines = new ArrayList<>();
        for (int i = removedLineIndex; i <= max; i++) {
            lines.add(this.content.get(i).getCustomName());
            removeLineWithoutUpdate(i);
        }

        addLines(lines);
    }

    private void addLines(List<String> lines) {
        addLines(lines.toArray(new String[0]));
    }

    public void addLines(String... lines) {
        for (String line : lines) {
            ArmorStand newLineEntity =
                    (ArmorStand) location.getWorld()
                            .spawnEntity(location.clone().subtract(0, Constants.SPACING * this.content.size(), 0), EntityType.ARMOR_STAND, false);
            newLineEntity.setVisible(false);
            newLineEntity.setSmall(true);
            newLineEntity.setMarker(true);
            newLineEntity.setCustomName(line);
            newLineEntity.setCustomNameVisible(true);
            this.content.put(this.content.size()+1, newLineEntity);
        }
    }

    public void setLine(int index, String newText) {
        ArmorStand line = this.content.get(index);
        line.setCustomName(newText);
        this.content.put(index, line);
    }

    public void removeLineWithoutUpdate(int index) {
        ArmorStand line = this.content.get(index);
        line.remove();
        this.content.remove(index);
    }

    public void removeEntities() {
        this.content.forEach((index, armorStand) -> armorStand.remove());
    }

    public void removeLine(int index) {
        removeLineWithoutUpdate(index);
        reorganizeLines(index+1);
    }

    public void deleteEntities() {
        content.forEach((index, entity) -> entity.remove());
    }
}
