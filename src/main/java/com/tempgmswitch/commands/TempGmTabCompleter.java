package com.tempgmswitch.commands;

import com.tempgmswitch.TemporaryGameModeSwitch;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class TempGmTabCompleter implements TabCompleter {

    private final TemporaryGameModeSwitch plugin;
    private static final List<String> GAMEMODES = Arrays.asList(
            "survival", "creative", "adventure", "spectator", "0", "1"
    );

    public TempGmTabCompleter(TemporaryGameModeSwitch plugin) {
        this.plugin = plugin;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        List<String> completions = new ArrayList<>();

        if (args.length == 1) {
            // First argument: game mode
            completions.addAll(plugin.getConfigManager().getEnabledGamemodes());
            completions.add("0");
            completions.add("1");
        } else if (args.length == 2) {
            // Second argument: duration (number)
            completions.add("30");
            completions.add("60");
            completions.add("300");
            completions.add("600");
            completions.add("1800");
            completions.add("3600");
        }

        // Filter completions based on current input
        if (args.length > 0) {
            String currentArg = args[args.length - 1].toLowerCase();
            completions = completions.stream()
                    .filter(s -> s.toLowerCase().startsWith(currentArg))
                    .collect(Collectors.toList());
        }

        return completions;
    }
}