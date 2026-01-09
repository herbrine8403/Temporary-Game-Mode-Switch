package com.tempgmswitch.commands;

import com.tempgmswitch.TemporaryGameModeSwitch;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class OpTempGmCommand implements CommandExecutor {

    private final TemporaryGameModeSwitch plugin;

    public OpTempGmCommand(TemporaryGameModeSwitch plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        // Check permission
        if (!sender.hasPermission("tempgm.admin")) {
            sender.sendMessage(plugin.getConfigManager().getColoredFormattedMessage("no-permission"));
            return true;
        }

        // Check arguments
        if (args.length == 0) {
            sender.sendMessage(plugin.getConfigManager().getColoredFormattedMessage("invalid-command",
                    "usage", "/optempgm <time|gamemode> <args>"));
            return true;
        }

        String subCommand = args[0].toLowerCase();

        switch (subCommand) {
            case "time":
                handleTimeCommand(sender, args);
                break;
            case "gamemode":
                handleGamemodeCommand(sender, args);
                break;
            default:
                sender.sendMessage(plugin.getConfigManager().getColoredFormattedMessage("invalid-command",
                        "usage", "/optempgm <time|gamemode> <args>"));
                break;
        }

        return true;
    }

    private void handleTimeCommand(CommandSender sender, String[] args) {
        if (args.length < 2) {
            sender.sendMessage(plugin.getConfigManager().getColoredFormattedMessage("invalid-command",
                    "usage", "/optempgm time <duration>"));
            return;
        }

        try {
            int duration = Integer.parseInt(args[1]);
            if (duration < 1) {
                sender.sendMessage(plugin.getConfigManager().getColoredFormattedMessage("duration-too-short"));
                return;
            }
            if (duration > 86400) {
                sender.sendMessage(plugin.getConfigManager().getColoredFormattedMessage("duration-too-long"));
                return;
            }

            plugin.getConfigManager().setDefaultDuration(duration);
            sender.sendMessage(plugin.getConfigManager().getColoredFormattedMessage("duration-set", "duration", String.valueOf(duration)));
        } catch (NumberFormatException e) {
            sender.sendMessage(plugin.getConfigManager().getColoredFormattedMessage("invalid-duration"));
        }
    }

    private void handleGamemodeCommand(CommandSender sender, String[] args) {
        if (args.length < 3) {
            sender.sendMessage(plugin.getConfigManager().getColoredFormattedMessage("invalid-command",
                    "usage", "/optempgm gamemode <enable|disable> <gamemode>"));
            return;
        }

        String action = args[1].toLowerCase();
        String modeArg = args[2].toLowerCase();
        GameMode gameMode = parseGameMode(modeArg);

        if (gameMode == null) {
            sender.sendMessage(plugin.getConfigManager().getColoredFormattedMessage("invalid-gamemode",
                    "modes", "survival, creative, adventure, spectator"));
            return;
        }

        String modeName = gameMode.name().toLowerCase();

        switch (action) {
            case "enable":
                plugin.getConfigManager().setGamemodeEnabled(modeName, true);
                sender.sendMessage(plugin.getConfigManager().getColoredFormattedMessage("gamemode-enabled", "gamemode", modeName));
                break;
            case "disable":
                plugin.getConfigManager().setGamemodeEnabled(modeName, false);
                sender.sendMessage(plugin.getConfigManager().getColoredFormattedMessage("gamemode-disabled-msg", "gamemode", modeName));
                break;
            default:
                sender.sendMessage(plugin.getConfigManager().getColoredFormattedMessage("invalid-command",
                        "usage", "/optempgm gamemode <enable|disable> <gamemode>"));
                break;
        }
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