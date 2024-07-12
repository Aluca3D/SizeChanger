package me.rpw.sizechanger;

import org.bukkit.plugin.java.JavaPlugin;

public final class SizeChanger extends JavaPlugin {

    @Override
    public void onEnable() {
        getCommand("size").setExecutor(new SizeChangerCommand());
        SizeChangerConfig.getInstance().load();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static SizeChanger getInstance() {
        return getPlugin(SizeChanger.class);
    }
}
