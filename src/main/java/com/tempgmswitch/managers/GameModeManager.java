package com.tempgmswitch.managers;

import com.tempgmswitch.TemporaryGameModeSwitch;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class GameModeManager {
    private final TemporaryGameModeSwitch plugin;
    private final Map<UUID, BukkitTask> activeTasks;

    public GameModeManager(TemporaryGameModeSwitch plugin) {
        this.plugin = plugin;
        this.activeTasks = new HashMap<>();
    }

    public void switchGameMode(Player player, GameMode gameMode, int duration) {
        UUID uuid = player.getUniqueId();

        // Cancel existing task if any
        cancelTask(uuid);

        // Store original game mode if not already stored
        GameMode originalMode = player.getGameMode();
        if (originalMode != GameMode.SURVIVAL && !hasActiveTask(uuid)) {
            // If player was already in a different mode, we'll switch to survival after duration
        }

        // Switch game mode
        player.setGameMode(gameMode);

        // Send message
        String modeName = gameMode.name().toLowerCase();
        String message = plugin.getConfigManager().getColoredFormattedMessage("gamemode-switched", "gamemode", modeName);
        player.sendMessage(message);

        // Schedule task to switch back
        BukkitTask task = plugin.getServer().getScheduler().runTaskLater(plugin, () -> {
            Player targetPlayer = plugin.getServer().getPlayer(uuid);
            if (targetPlayer != null && targetPlayer.isOnline()) {
                targetPlayer.setGameMode(GameMode.SURVIVAL);
                String expiredMessage = plugin.getConfigManager().getColoredFormattedMessage("gamemode-expired");
                targetPlayer.sendMessage(expiredMessage);
            }
            activeTasks.remove(uuid);
        }, duration * 20L); // Convert seconds to ticks

        activeTasks.put(uuid, task);
    }

    public void cancelTask(UUID uuid) {
        BukkitTask task = activeTasks.get(uuid);
        if (task != null && !task.isCancelled()) {
            task.cancel();
            activeTasks.remove(uuid);
        }
    }

    public boolean hasActiveTask(UUID uuid) {
        BukkitTask task = activeTasks.get(uuid);
        return task != null && !task.isCancelled();
    }

    public void cancelAllTasks() {
        activeTasks.values().forEach(task -> {
            if (!task.isCancelled()) {
                task.cancel();
            }
        });
        activeTasks.clear();
    }

    public void onPlayerQuit(Player player) {
        cancelTask(player.getUniqueId());
    }
}