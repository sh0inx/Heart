package io.github.sh0inx.heart.configs.files;

import ch.jalu.configme.configurationdata.CommentsConfiguration;
import ch.jalu.configme.properties.MapProperty;
import ch.jalu.configme.properties.PropertyInitializer;
import ch.jalu.configme.properties.types.BeanPropertyType;
import io.github.sh0inx.heart.Heart;
import io.github.sh0inx.heart.commands.AboutCommand;
import io.github.sh0inx.heart.configs.HeartConfig;
import io.github.sh0inx.heart.configs.beans.CommandItemBean;

import java.nio.file.Path;
import java.util.Collections;


public class Commands extends HeartConfig {
    public AboutCommand aboutCommand;
    private Path configFile;
    public Commands(Heart heart) {
        this("heart", "heart", heart, Path.of("resources/commands.yml"));
    }

    public Commands(String permissionBase, String commandBase, Heart heart, Path configFile) {
        super(heart, configFile);
        aboutCommand = new AboutCommand(Collections.singletonList("about"), "show information about dragonCancel", "%prefix% &7/" + commandBase + " about", "");
    }


    @Override
    public void registerComments(CommentsConfiguration config) {
        config.setComment("", "\n",
                "C O M M A N D S",
                "=-=-=-=-=-=-=-=",
                "\n");
    }

    public static final MapProperty<CommandItemBean> COMMANDS = PropertyInitializer.mapProperty(BeanPropertyType.of(CommandItemBean.class))
            .path("commands")
            .defaultEntry("about", new CommandItemBean(Collections.singletonList("info"), "Shows information about the plugin.", "heart", "", true))
            .defaultEntry("checkForUpdates", new CommandItemBean(Collections.singletonList("update"), "Checks for a new version of the plugin on Modrinth", "heart", "checkForUpdates", true))
            .build();
}