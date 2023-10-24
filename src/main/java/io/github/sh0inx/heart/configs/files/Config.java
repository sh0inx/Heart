package io.github.sh0inx.heart.configs.files;

import ch.jalu.configme.configurationdata.CommentsConfiguration;
import io.github.sh0inx.heart.Heart;
import io.github.sh0inx.heart.configs.HeartConfig;


import java.nio.file.Path;


public class Config extends HeartConfig {
    private Path configFile;
    public Config(Heart heart) {
        this(heart, Path.of("resources/config.yml"));
    }

    public Config(Heart heart, Path configFile) {
        super(heart, configFile);
    }


    @Override
    public void registerComments(CommentsConfiguration config) {
        config.setComment("", "\n",
                "\n" ,
                " Welcome to Heart's Config!" ,
                "\n");
        }
    }
