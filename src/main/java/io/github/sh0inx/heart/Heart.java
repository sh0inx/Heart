package io.github.sh0inx.heart;

import io.github.sh0inx.heart.configs.*;
import io.github.sh0inx.heart.managers.*;
import io.github.sh0inx.heart.managers.versioncheck.*;

import org.bstats.bukkit.Metrics;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.java.JavaPluginLoader;

import java.io.File;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
public abstract class Heart extends JavaPlugin {

    private static Heart instance;

    public Heart(JavaPluginLoader loader, PluginDescriptionFile description, File dataFolder, File file) {
        super(loader, description, dataFolder, file);
    }

    @Override
    public void onLoad() {
        getDataFolder().mkdir();
        this.persist = new Persist(Persist.PersistType.YAML, this);
    }

    @Override
    public void onEnable() {
        startServerCheck();
        initializeManagers();
        registerListeners();
        consoleMessage(MessageType.ENABLE);
        startUpdateCheck();
    }

    @Override
    public void onDisable() {
        consoleMessage(MessageType.DISABLE);
    }

    //variables
    public int bstatsPluginID = 00000;
    public String modrinthPluginId = "";
    public String modrinthLink = "https://modrinth.com/plugin/" + modrinthPluginId;
    String githubIssues;
    String consoleDecorator;
    @Getter
    private Persist persist;
    public Profile profile;

    public abstract UpdateCheck getUpdateCheck();
    public abstract VersionCheck getVersionCheck();
    public abstract ProfileCheck getProfileCheck();
    public abstract CommandManager getCommandManager();
    public abstract ConfigManager getConfigManager();

    public abstract PluginConfiguration getPluginConfiguration();
    public abstract Commands getCommands();
    public abstract Messages getMessages();

    //plugin methods
    public void initializeManagers() {
        getConfigManager().initializeConfigs();
        getCommandManager().registerCommands();
    }

    public void registerListeners() {

    }

    public void logMessage(MessageType messageType, String message) {
        switch(messageType) {
            case LOG: {
                getLogger().info(message);
                break;
            }
            case WARNING: {
                getLogger().warning(message);
                break;
            }
            case ERROR: {
                pluginError(message);
                break;
            }
            case TRACE: {
                getLogger().info("*trace*: " + message);
                break;
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
            case ENABLE: {
                getLogger().info(getDescription().getName() + " enabled successfully.");
                break;
            }
            case DISABLE: {
                getLogger().info(getDescription().getName() + " disabled successfully.");
                break;
            }
            case PROFILE: {
                getLogger().info(getDescription().getName() + " profile set to " + profile);
                break;
            }
            case UPDATE: {
                getLogger().info("There's a new version of " + getDescription().getName() + " available!");
                getLogger().info("Download it here: " + modrinthLink);
                break;
            }
        }
        getLogger().info("");
        getLogger().info( consoleDecorator);
    }

    public void startUpdateCheck() {
        if(!getPluginConfiguration().checkForUpdates) {
            return;
        }

        if(getUpdateCheck().getCurrentVersion().equalsIgnoreCase("404")) { return; }

        if(!getDescription().getVersion().equalsIgnoreCase(getUpdateCheck().getCurrentVersion())) {
            consoleMessage(MessageType.UPDATE);
            return;
        }

        logMessage(MessageType.LOG, "You're running the latest version of " + getDescription().getName() + " (" + getDescription().getVersion() + ").");
    }

    public void startServerCheck() {
        getVersionCheck().checkVersion();
        getVersionCheck().checkPlatform();

        profile = getProfileCheck().determineProfile();
    }

    public void addBstats(int bstatsPluginID) {
        new Metrics(this, bstatsPluginID);
    }

}
