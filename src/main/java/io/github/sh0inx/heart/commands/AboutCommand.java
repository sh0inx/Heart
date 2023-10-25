package io.github.sh0inx.heart.commands;

import org.bukkit.command.CommandSender;

import java.util.List;

public class AboutCommand extends Command {
    public AboutCommand(List<String> args, String description, String syntax, String permission, boolean enabled) {
        super(args, description, syntax, permission, enabled);
    }

    public boolean execute(CommandSender sender, String commandLabel, String[] args) {
        return true;
    }
}
