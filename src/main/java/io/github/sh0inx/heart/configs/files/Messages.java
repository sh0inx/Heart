package io.github.sh0inx.heart.configs.files;

import ch.jalu.configme.configurationdata.CommentsConfiguration;
import ch.jalu.configme.properties.MapProperty;
import ch.jalu.configme.properties.PropertyInitializer;
import ch.jalu.configme.properties.types.BeanPropertyType;
import io.github.sh0inx.heart.Heart;
import io.github.sh0inx.heart.configs.HeartConfig;
import io.github.sh0inx.heart.configs.beans.CommandItemBean;

import java.nio.file.Path;
import java.util.Collections;

public class Messages extends HeartConfig {
    private Path configFile;

    @Override
    public void registerComments(CommentsConfiguration config) {
        config.setComment("", "\n",
                "\n",
                " Welcome to Heart's Config!",
                "\n",
                "This is the Messages config fle, where you can edit the contents of any message that the plugin sends.",
                "\n");
    }


    public Messages(Heart heart, Path configFile) {
        super(heart, configFile);
    }

    public static final MapProperty<CommandItemBean> MESSAGES = PropertyInitializer.mapProperty(BeanPropertyType.of(CommandItemBean.class))
            .path("messages")
            .build();
}
