package me.rpw.sizechanger;

import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

public class SizeChangerConfig {
    private final static SizeChangerConfig instance = new SizeChangerConfig();

    private File file;
    private YamlConfiguration config;

    private double maxSize;
    private double minSize;
    private double addRemoveValue;

    private SizeChangerConfig() {
    }

    public void load() {
        file = new File(SizeChanger.getInstance().getDataFolder(), "config.yml");

        if (!file.exists()) {
            SizeChanger.getInstance().saveResource("config.yml", false);
        }

        config = new YamlConfiguration();
        config.options().parseComments(true);

        try {
            config.load(file);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        maxSize = config.getDouble("size.max-size");
        minSize = config.getDouble("size.min-size");
        addRemoveValue = config.getDouble("size.add-remove-value");
    }

    public void save() {
        try {
            config.save(file);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public double getMaxSize() {
        return maxSize;
    }

    public double getMinSize() {
        return minSize;
    }

    public double getAddRemoveValue() {
        return addRemoveValue;
    }

    public static SizeChangerConfig getInstance() {
        return instance;
    }
}
