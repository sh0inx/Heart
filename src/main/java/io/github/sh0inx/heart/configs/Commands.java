package io.github.sh0inx.heart.configs;

import io.github.sh0inx.heart.commands.AboutCommand;
import io.github.sh0inx.heart.commands.UpdateCheckCommand;

import java.util.Collections;

public class Commands {


    public AboutCommand aboutCommand = new AboutCommand(Collections.singletonList("about"), "Shows information about the plugin.", "/heart about", "heart.about", true);
    public UpdateCheckCommand updateCheckCommand = new UpdateCheckCommand(Collections.singletonList("updateCheck"), "Checks if there have been updates to the plugin.", "/heart updateCheck", "heart.updateCheck", true);
}
