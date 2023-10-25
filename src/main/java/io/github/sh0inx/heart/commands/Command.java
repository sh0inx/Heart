package io.github.sh0inx.heart.commands;

import io.github.sh0inx.heart.Heart;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Collections;
import java.util.List;

public class Command {

    public final List<String> aliases;
    public final String description;
    public final String syntax;
    public final String permission;
    public final boolean enabled;

    public Command() {
        this.aliases = Collections.emptyList();
        this.description = "";
        this.syntax = "";
        this.permission = "";
        this.enabled = true;
    }

    public Command(List<String> aliases, String description, String syntax, String permission, boolean enabled) {
        this.aliases = aliases;
        this.description = description;
        this.syntax = syntax;
        this.permission = permission;
        this.enabled = enabled;
    }

    public boolean execute(CommandSender sender, String[] arguments, Heart heart) {
        if (!(sender instanceof Player)) {
            //send message stating they must be a player
            return false;
        }
        return true;
    }

    public boolean hasPermission(CommandSender commandSender, Heart heart) {
        return commandSender.hasPermission(permission) || permission.equalsIgnoreCase("");
    }

    public List<String> onTabComplete(CommandSender commandSender, String[] args, Heart heart) {
        if (commandSender instanceof Player) {
            //tab complete if player
        }
        return Collections.emptyList();
    }

    public List<String> onTabComplete(String[] args, Heart heart) {
        return Collections.emptyList();
    }

}
