package com.tempgmswitch.listeners;

import com.tempgmswitch.TemporaryGameModeSwitch;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerQuitListener implements Listener {

    private final TemporaryGameModeSwitch plugin;

    public PlayerQuitListener(TemporaryGameModeSwitch plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        plugin.getGameModeManager().onPlayerQuit(event.getPlayer());
    }
}