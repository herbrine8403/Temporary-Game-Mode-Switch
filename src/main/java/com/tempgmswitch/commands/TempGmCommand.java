package com.tempgmswitch.commands;

import com.tempgmswitch.TemporaryGameModeSwitch;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TempGmCommand implements CommandExecutor {

    private final TemporaryGameModeSwitch plugin;

    public TempGmCommand(TemporaryGameModeSwitch plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        // Check if sender is a player
        if (!(sender instanceof Player)) {
            sender.sendMessage(plugin.getConfigManager().getColoredFormattedMessage("player-only"));
            return true;
        }

        Player player = (Player) sender;

        // Check permission
        if (!player.hasPermission("tempgm.use")) {
            player.sendMessage(plugin.getConfigManager().getColoredFormattedMessage("no-permission"));
            return true;
        }

        // Check arguments
        if (args.length == 0) {
            String availableModes = String.join(", ", plugin.getConfigManager().getEnabledGamemodes());
            player.sendMessage(plugin.getConfigManager().getColoredFormattedMessage("invalid-gamemode", "modes", availableModes));
            return true;
        }

        // Parse game mode
        String modeArg = args[0].toLowerCase();
        GameMode gameMode = parseGameMode(modeArg);

        if (gameMode == null) {
            String availableModes = String.join(", ", plugin.getConfigManager().getEnabledGamemodes());
            player.sendMessage(plugin.getConfigManager().getColoredFormattedMessage("invalid-gamemode", "modes", availableModes));
            return true;
        }

        // Check if game mode is enabled
        if (!plugin.getConfigManager().isGamemodeEnabled(gameMode.name().toLowerCase())) {
            player.sendMessage(plugin.getConfigManager().getColoredFormattedMessage("gamemode-disabled", "gamemode", gameMode.name().toLowerCase()));
            return true;
        }

        // Parse duration (optional)
        int duration = plugin.getConfigManager().getDefaultDuration();
        if (args.length >= 2) {
            try {
                duration = Integer.parseInt(args[1]);
                if (duration < 1) {
                    player.sendMessage(plugin.getConfigManager().getColoredFormattedMessage("duration-too-short"));
                    return true;
                }
                if (duration > 86400) {
                    player.sendMessage(plugin.getConfigManager().getColoredFormattedMessage("duration-too-long"));
                    return true;
                }
            } catch (NumberFormatException e) {
                player.sendMessage(plugin.getConfigManager().getColoredFormattedMessage("invalid-duration"));
                return true;
            }
        }

        // Switch game mode
        plugin.getGameModeManager().switchGameMode(player, gameMode, duration);

        return true;
    }

    private GameMode parseGameMode(String modeArg) {
        switch (modeArg) {
            case "0":
            case "survival":
                return GameMode.SURVIVAL;
            case "1":
            case "creative":
                return GameMode.CREATIVE;
            case "adventure":
                return GameMode.ADVENTURE;
            case "spectator":
                return GameMode.SPECTATOR;
            default:
                return null;
        }
    }
}