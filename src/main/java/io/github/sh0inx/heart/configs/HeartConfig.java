package io.github.sh0inx.heart.configs;

import io.github.sh0inx.heart.Heart;

import ch.jalu.configme.SettingsHolder;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

public class HeartConfig implements SettingsHolder {

    private Heart heart;
    public Path configFile;

    public HeartConfig(Heart heart, Path configFile) {
        this.configFile = copyFileFromJar(String.valueOf(configFile));
        this.heart = heart;
    }

    private Path copyFileFromJar(String path) {
        try {
            Path tempFile = Files.createTempFile("configme-", "-commands.yml");
            Files.copy(Objects.requireNonNull(heart.getResource(String.valueOf(configFile))), tempFile, REPLACE_EXISTING);
            return tempFile;
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    public Path getConfigFile() {
        return configFile;
    }
}
