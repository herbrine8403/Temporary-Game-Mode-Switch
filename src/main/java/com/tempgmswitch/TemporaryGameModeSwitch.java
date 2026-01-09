package com.tempgmswitch;

import com.tempgmswitch.commands.OpTempGmCommand;
import com.tempgmswitch.commands.OpTempGmTabCompleter;
import com.tempgmswitch.commands.TempGmCommand;
import com.tempgmswitch.commands.TempGmTabCompleter;
import com.tempgmswitch.config.ConfigManager;
import com.tempgmswitch.listeners.PlayerQuitListener;
import com.tempgmswitch.managers.GameModeManager;
import org.bukkit.plugin.java.JavaPlugin;

public class TemporaryGameModeSwitch extends JavaPlugin {

    private ConfigManager configManager;
    private GameModeManager gameModeManager;

    @Override
    public void onEnable() {
        // Initialize managers
        this.configManager = new ConfigManager(this);
        this.gameModeManager = new GameModeManager(this);

        // Register commands
        getCommand("tempgm").setExecutor(new TempGmCommand(this));
        getCommand("tempgm").setTabCompleter(new TempGmTabCompleter(this));
        getCommand("optempgm").setExecutor(new OpTempGmCommand(this));
        getCommand("optempgm").setTabCompleter(new OpTempGmTabCompleter(this));

        // Register listeners
        getServer().getPluginManager().registerEvents(new PlayerQuitListener(this), this);

        getLogger().info("TemporaryGameModeSwitch has been enabled!");
    }

    @Override
    public void onDisable() {
        // Cancel all active tasks
        if (gameModeManager != null) {
            gameModeManager.cancelAllTasks();
        }

        getLogger().info("TemporaryGameModeSwitch has been disabled!");
    }

    public ConfigManager getConfigManager() {
        return configManager;
    }

    public GameModeManager getGameModeManager() {
        return gameModeManager;
    }
}