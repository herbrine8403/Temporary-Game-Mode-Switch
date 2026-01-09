package com.tempgmswitch.config;

import com.tempgmswitch.TemporaryGameModeSwitch;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.List;

public class ConfigManager {
    private final TemporaryGameModeSwitch plugin;
    private FileConfiguration config;

    public ConfigManager(TemporaryGameModeSwitch plugin) {
        this.plugin = plugin;
        this.plugin.saveDefaultConfig();
        this.config = plugin.getConfig();
        plugin.getLogger().info("Config loaded. Language: " + config.getString("language", "zh"));
        plugin.getLogger().info("Messages keys: " + config.getConfigurationSection("messages").getKeys(false));
    }

    public void reloadConfig() {
        plugin.reloadConfig();
        config = plugin.getConfig();
    }

    public int getDefaultDuration() {
        return config.getInt("default-duration", 300);
    }

    public void setDefaultDuration(int duration) {
        config.set("default-duration", duration);
        plugin.saveConfig();
    }

    public List<String> getEnabledGamemodes() {
        return config.getStringList("enabled-gamemodes");
    }

    public void setEnabledGamemodes(List<String> gamemodes) {
        config.set("enabled-gamemodes", gamemodes);
        plugin.saveConfig();
    }

    public boolean isGamemodeEnabled(String gamemode) {
        return getEnabledGamemodes().contains(gamemode.toLowerCase());
    }

    public void setGamemodeEnabled(String gamemode, boolean enabled) {
        List<String> modes = getEnabledGamemodes();
        String mode = gamemode.toLowerCase();

        if (enabled) {
            if (!modes.contains(mode)) {
                modes.add(mode);
            }
        } else {
            modes.remove(mode);
        }

        setEnabledGamemodes(modes);
    }

    public String getMessage(String key) {
        String language = config.getString("language", "zh");
        String fullPath = "messages." + language + "." + key;
        String message = config.getString(fullPath, "");

        if (message == null || message.isEmpty()) {
            plugin.getLogger().warning("Message not found: " + fullPath);
            plugin.getLogger().warning("Language setting: " + language);

            // Try to get the messages section
            if (config.contains("messages")) {
                plugin.getLogger().warning("Messages section exists");
                if (config.contains("messages." + language)) {
                    plugin.getLogger().warning("Language section exists: " + language);
                    plugin.getLogger().warning("Available keys: " + config.getConfigurationSection("messages." + language).getKeys(false));
                } else {
                    plugin.getLogger().warning("Language section does NOT exist: " + language);
                    plugin.getLogger().warning("Available languages: " + config.getConfigurationSection("messages").getKeys(false));
                }
            } else {
                plugin.getLogger().warning("Messages section does NOT exist!");
            }
        }
        return message != null ? message : "";
    }

    public String getPrefix() {
        return getMessage("prefix");
    }

    public String getLanguage() {
        return config.getString("language", "zh");
    }

    public void setLanguage(String language) {
        config.set("language", language);
        plugin.saveConfig();
    }

    public String getFormattedMessage(String key) {
        return getPrefix() + getMessage(key);
    }

    public String getFormattedMessage(String key, String... placeholders) {
        String message = getFormattedMessage(key);
        for (int i = 0; i < placeholders.length; i += 2) {
            if (i + 1 < placeholders.length) {
                message = message.replace("{" + placeholders[i] + "}", placeholders[i + 1]);
            }
        }
        return message;
    }

    public String getColoredMessage(String key) {
        return org.bukkit.ChatColor.translateAlternateColorCodes('&', getMessage(key));
    }

    public String getColoredPrefix() {
        return org.bukkit.ChatColor.translateAlternateColorCodes('&', getPrefix());
    }

    public String getColoredFormattedMessage(String key) {
        return getColoredPrefix() + getColoredMessage(key);
    }

    public String getColoredFormattedMessage(String key, String... placeholders) {
        String message = getColoredFormattedMessage(key);
        for (int i = 0; i < placeholders.length; i += 2) {
            if (i + 1 < placeholders.length) {
                message = message.replace("{" + placeholders[i] + "}", placeholders[i + 1]);
            }
        }
        return message;
    }
}