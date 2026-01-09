package com.tempgmswitch.commands;

import com.tempgmswitch.TemporaryGameModeSwitch;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class OpTempGmTabCompleter implements TabCompleter {

    private final TemporaryGameModeSwitch plugin;
    private static final List<String> SUBCOMMANDS = Arrays.asList("time", "gamemode");
    private static final List<String> ACTIONS = Arrays.asList("enable", "disable");
    private static final List<String> GAMEMODES = Arrays.asList(
            "survival", "creative", "adventure", "spectator", "0", "1"
    );

    public OpTempGmTabCompleter(TemporaryGameModeSwitch plugin) {
        this.plugin = plugin;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        List<String> completions = new ArrayList<>();

        if (args.length == 1) {
            // First argument: subcommand
            completions.addAll(SUBCOMMANDS);
        } else if (args.length == 2) {
            // Second argument: depends on subcommand
            String subCommand = args[0].toLowerCase();
            if (subCommand.equals("time")) {
                completions.add("30");
                completions.add("60");
                completions.add("300");
                completions.add("600");
                completions.add("1800");
                completions.add("3600");
            } else if (subCommand.equals("gamemode")) {
                completions.addAll(ACTIONS);
            }
        } else if (args.length == 3) {
            // Third argument: depends on subcommand
            String subCommand = args[0].toLowerCase();
            if (subCommand.equals("gamemode")) {
                completions.addAll(GAMEMODES);
            }
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