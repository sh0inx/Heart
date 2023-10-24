package io.github.sh0inx.heart;

import io.github.sh0inx.heart.configs.files.Commands;
import io.github.sh0inx.heart.managers.CommandManager;
import io.github.sh0inx.heart.managers.ConfigManager;
import io.github.sh0inx.heart.managers.versioncheck.ProfileCheck;
import io.github.sh0inx.heart.managers.versioncheck.UpdateCheck;
import io.github.sh0inx.heart.managers.versioncheck.VersionCheck;

import org.bukkit.NamespacedKey;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.java.JavaPluginLoader;

import java.io.File;

public abstract class Heart extends JavaPlugin {

    //variables
    public final int getBstatsPluginId = 00000;
    public final String getModrinthPluginId = "";
    public final String getModrinthLink = "https://modrinth.com/plugin/" + getModrinthPluginId;
    String githubIssues;
    String consoleDecorator;
    public Profile profile;
    public abstract UpdateCheck getUpdateCheck();
    public abstract VersionCheck getVersionCheck();
    public abstract ProfileCheck getProfileCheck();
    public abstract ConfigManager configManager();
    public abstract CommandManager commandManager();
    public abstract Commands getCommands();

    private static Heart instance;
    protected Heart() {
    }

    public Heart(JavaPluginLoader loader, PluginDescriptionFile description, File dataFolder, File file) {
        super(loader, description, dataFolder, file);
    }


    private final NamespacedKey key = new NamespacedKey(this, "heart");

    public NamespacedKey getKey() {
        return key;
    }

    //override methods
    @Override
    public void onLoad() {
        getDataFolder().mkdir();
    }

    @Override
    public void onEnable() {
        instance = this;
        initializeManagers();
        loadConfigs();
        initializeManagers();
        registerListeners();
        consoleMessage(MessageType.ENABLE);
        startUpdateCheck();
    }

    @Override
    public void onDisable() {
        consoleMessage(MessageType.DISABLE);
    }

    //plugin methods
    public void initializeManagers() {
        loadConfigs();
        commandManager().registerCommands();
    }

    public void registerListeners() {

    }

    public void loadConfigs() {
        configManager().reloadConfigs();
    }

    public void logMessage(MessageType messageType, String message) {
        switch(messageType) {
            case LOG -> {
                getLogger().info(message);
            }
            case WARNING -> {
                getLogger().warning(message);
            }
            case ERROR -> {
                pluginError(message);
            }
            case TRACE -> {
                getLogger().info("*trace*: " + message);
            }
        }
    }

    public void pluginError(String error) {
        getLogger().warning(getDescription().getName() + " " + getDescription().getVersion() + " encountered the following error.");
        getLogger().warning(consoleDecorator);
        getLogger().warning("");
        getLogger().warning(getDescription().getName() + " has encountered a problem and may not continue functioning.");
        getLogger().warning("Please read the stacktrace in this log and make the necessary fixes.");
        getLogger().warning("");
        getLogger().warning("ERROR: " + error);
        getLogger().warning("");
        getLogger().warning("You can also check here to see if this issue has been reported:");
        getLogger().warning(githubIssues);
        getLogger().warning("");
        getLogger().warning( consoleDecorator);
    }

    public void consoleMessage(MessageType messageType) {
        getLogger().info(consoleDecorator);
        getLogger().info("");
        switch (messageType) {
            case ENABLE -> {
                getLogger().info(getDescription().getName() + " enabled successfully.");
            }
            case DISABLE -> {
                getLogger().info(getDescription().getName() + " disabled successfully.");
            }
            case PROFILE -> {
                getLogger().info(getDescription().getName() + " profile set to " + profile);
            }
            case UPDATE -> {
                getLogger().info("There's a new version of " + getDescription().getName() + " available!");
                getLogger().info("Download it here: " + getModrinthLink);
            }
        }
        getLogger().info("");
        getLogger().info( consoleDecorator);
    }

    public void startUpdateCheck() {

        getVersionCheck().checkVersion();
        getVersionCheck().checkPlatform();

        profile = getProfileCheck().determineProfile();

        if(getUpdateCheck().getCurrentVersion().equalsIgnoreCase("404")) { return; }

        if(!getDescription().getVersion().equalsIgnoreCase(getUpdateCheck().getCurrentVersion())) {
            consoleMessage(MessageType.UPDATE);
            return;
        }

        logMessage(MessageType.LOG, "You're running the latest version of " + getDescription().getName() + " (" + getDescription().getVersion() + ").");
    }

    public static Heart getInstance() {
        return instance;
    }
}
