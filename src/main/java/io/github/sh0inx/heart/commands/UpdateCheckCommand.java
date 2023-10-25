package io.github.sh0inx.heart.commands;

import io.github.sh0inx.heart.Heart;
import io.github.sh0inx.heart.MessageType;
import org.bukkit.command.CommandSender;

import java.util.List;
import java.util.Objects;

public class UpdateCheckCommand extends Command {

    public UpdateCheckCommand(List<String> args, String description, String syntax, String permission, boolean enabled) {
        super(args, description, syntax, permission, enabled);
    }

    public boolean execute(CommandSender sender, String commandLabel, String[] args, Heart heart) {
        heart.startUpdateCheck();
        if(!Objects.equals(heart.getUpdateCheck().getCurrentVersion(), heart.getDescription().getVersion())) {
            sender.sendMessage(heart.getMessages().update);
        }
        return true;
    }
}
