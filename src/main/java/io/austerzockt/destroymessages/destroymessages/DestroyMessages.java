package io.austerzockt.destroymessages.destroymessages;

import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.plugin.java.JavaPlugin;

public final class DestroyMessages extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        Bukkit.getPluginManager().registerEvents(this, this);
        if (getConfig().getConfigurationSection("blocks") == null) {
            getConfig().createSection("blocks");
            getConfig().set("blocks.DRAGON_EGG", "&5This Message has Color and Placeholders {PLAYER} %Math_3+4%");
        }
        saveConfig();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    @EventHandler
    public void onBreak(BlockBreakEvent event) {
        var cs = getConfig().getConfigurationSection("blocks");
        if (cs == null) return;
        var keys = cs.getKeys(true);
        for (String key :
                keys) {
            if (key.toLowerCase().equalsIgnoreCase(event.getBlock().getType().name().toLowerCase())) {
                var message = cs.getString(key);
                if (message == null) return;
                Bukkit.broadcastMessage(PlaceholderAPI.setPlaceholders(event.getPlayer(), message.replaceAll("\\{PLAYER}", event.getPlayer().getDisplayName())));
            }

        }
    }
}
