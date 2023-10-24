package io.github.sh0inx.heart.managers;

import io.github.sh0inx.heart.Heart;
import io.github.sh0inx.heart.configs.Configs;
import io.github.sh0inx.heart.configs.files.Commands;
import io.github.sh0inx.heart.configs.files.Config;
import io.github.sh0inx.heart.configs.files.Messages;

import ch.jalu.configme.SettingsHolder;
import ch.jalu.configme.SettingsManager;
import ch.jalu.configme.SettingsManagerBuilder;
import ch.jalu.configme.properties.Property;

import java.io.File;
import java.nio.file.Path;
import java.util.HashMap;

public abstract class ConfigManager {
    private final HashMap<Configs, SettingsManager> configs = new HashMap<>();
    private final Heart heart;

    public ConfigManager(Heart heart) {
        this.heart = heart;
    }

    public void reloadConfigs() {
        configs.clear();
        loadConfig(Configs.MESSAGES);
        loadConfig(Configs.CONFIG);
    }

    public SettingsManager getConfig(Configs name) {
        if (!configs.containsKey(name)) {
            loadConfig(name);
        }

        return configs.get(name);
    }

    public void loadConfig(Configs name) {
        String fileName = name.name().toLowerCase() + ".yml";
        File file = new File(Heart.getInstance().getDataFolder(), fileName);

        if (!file.exists()) {
            file.getParentFile().mkdirs();
        }

        SettingsManager settingsManager = initSettings(name, file);
        configs.put(name, settingsManager);
    }

    public SettingsManager initSettings(Configs name, File config) {
        Class<? extends SettingsHolder> clazz = switch (name) {
            case MESSAGES -> Messages.class;
            case CONFIG -> Config.class;
            case COMMANDS -> Commands.class;
        };

        Path configFile = Path.of(config.getPath());
        return SettingsManagerBuilder
                .withYamlFile(configFile)
                .configurationData(clazz)
                .useDefaultMigrationService()
                .create();
    }

    public Object getConfigValue(Configs config, Property<?> value) {
        SettingsManager settingsManager = getConfig(config);
        return settingsManager.getProperty(value);
    }
}