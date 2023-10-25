package io.github.sh0inx.heart.managers;

import io.github.sh0inx.heart.Heart;
import io.github.sh0inx.heart.commands.Command;

import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.*;
import java.util.stream.Collectors;

public abstract class CommandManager implements CommandExecutor, TabCompleter {

    private final List<Command> commands = new ArrayList<>();
    private final String command;
    private final String color;
    private final Heart heart;

    public CommandManager(Heart heart, String color, String command) {
        this.heart = heart;
        this.command = command;
        this.color = color;
        heart.getCommand(command).setExecutor(this);
        heart.getCommand(command).setTabCompleter(this);
        registerCommands();
    }

    public void registerCommands() {
        registerCommand(heart.getCommands().aboutCommand);
        registerCommand(heart.getCommands().updateCheckCommand);
    }

    public void registerCommand(Command command) {
        int index = Collections.binarySearch(commands, command, Comparator.comparing(cmd -> cmd.aliases.get(0)));
        commands.add(index < 0 ? -(index + 1) : index, command);
    }

    @Override
    public boolean onCommand(CommandSender commandSender, org.bukkit.command.Command cmd, String label, String[] args) {
        if (args.length == 0) {
            noArgsDefault(commandSender);
            return true;
        }

        for (Command command : commands) {
            // We don't want to execute other commands or ones that are disabled
            if (!command.aliases.contains(args[0])) continue;

            return executeCommand(commandSender, command, Arrays.copyOfRange(args, 1, args.length));
        }

        // Unknown command message
        return false;
    }

    public boolean executeCommand(CommandSender commandSender, Command command, String[] args) {
        if (!command.hasPermission(commandSender, heart)) {
            //no permission message
            return false;
        }

        return command.execute(commandSender, args, heart);
    }

    public abstract void noArgsDefault(CommandSender commandSender);

    private List<String> getTabComplete(CommandSender commandSender, String[] args) {
        if (args.length == 1) {
            ArrayList<String> result = new ArrayList<>();
            for (Command command : commands) {
                for (String alias : command.aliases) {
                    if (!alias.toLowerCase().startsWith(args[0].toLowerCase())) continue;
                    if (commandSender.hasPermission(command.permission) || command.permission.equalsIgnoreCase("")) {
                        result.add(alias);
                    }
                }
            }
            return result.stream().sorted().collect(Collectors.toList());
        }

        for (Command command : commands) {
            if (!command.aliases.contains(args[0].toLowerCase())) continue;
            if (commandSender.hasPermission(command.permission) || command.permission.equalsIgnoreCase("")) {
                return command.onTabComplete(commandSender, Arrays.copyOfRange(args, 1, args.length), heart);
            }
        }

        return Collections.emptyList();
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, org.bukkit.command.Command cmd, String label, String[] args) {
        List<String> tabComplete = getTabComplete(commandSender, args);
        if (tabComplete == null) return null;
        return tabComplete.stream()
                .filter(s -> s.toLowerCase().startsWith(args[args.length - 1].toLowerCase()))
                .sorted()
                .collect(Collectors.toList());
    }
}
