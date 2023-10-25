package io.github.sh0inx.heart.managers;

import io.github.sh0inx.heart.configs.Commands;
import io.github.sh0inx.heart.configs.Messages;

import io.github.sh0inx.heart.configs.PluginConfiguration;

import java.nio.file.Path;
import java.nio.file.Paths;

public abstract class ConfigManager {

    PluginConfiguration configuration = new PluginConfiguration();
    Messages messages = new Messages();
    Commands commands = new Commands();

    Path configFile = Paths.get("/tmp/config.yml");
    Path messagesFile = Paths.get("/tmp/messages.yml");
    Path commandsFile = Paths.get("/tmp/commands.yml");


    public void initializeConfigs() {
        loadConfigs();
        saveConfigs();
    }

    public void loadConfigs() {

    }

    public void saveConfigs() {
    }
}
