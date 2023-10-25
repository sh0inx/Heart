package io.github.sh0inx.heart.managers.versioncheck;

import io.github.sh0inx.heart.Heart;
import io.github.sh0inx.heart.MessageType;
import io.github.sh0inx.heart.Platform;

import org.bukkit.Bukkit;

public class VersionCheck {

    private final Heart heart;

    public VersionCheck(Heart heart) {
        this.heart = heart;
        checkVersion();
        checkPlatform();
    }

    public void checkVersion() {
        String version = getVersion();
        boolean isVersionSupported = isVersionSupported(version);

        if (!isVersionSupported) {
            heart.logMessage(MessageType.TRACE, "Version: FAIL w/ " + version);
            Bukkit.getPluginManager().disablePlugin(heart);
        }

        heart.logMessage(MessageType.TRACE, "Version: PASS w/ " + version);
    }

    public void checkPlatform() {
        Platform platform = getPlatform();
        boolean isPlatformSupported = isPlatformSupported(platform);

        if (!isPlatformSupported) {
            heart.logMessage(MessageType.TRACE, "Platform: FAIL w/ " + platform.toString());
            Bukkit.getPluginManager().disablePlugin(heart);
        }

        heart.logMessage(MessageType.TRACE, "Version: PASS w/ " + platform.toString());
    }

    public static String getVersion() { return Bukkit.getVersion(); }

    public static boolean isVersionSupported(String version) {
        switch (version) {
            case "1.20": {}
            case "1.19": {}
            case "1.18": {}
            case "1.17": {}
            case "1.16": {}
            case "1.15": {}
            case "1.14": {}
            case "1.13": {}
            case "1.12": {}
            case "1.11": {}
            case "1.10": {}
            case "1.9": {}
            case "1.8": { return true; }
            default: return false;
        }
    }

    public static boolean classExists(String className) {
        try {
            Class.forName(className);
            return true;
        } catch (ClassNotFoundException e) {
            return false;
        }
    }

    public static boolean isPlatformSupported(Platform platform) {
        switch (platform) {
            case Spigot: {}
            case Paper: {}
            case Purpur: { return true; }
            default: { return false; }
        }
    }

    public static Platform getPlatform() {

        if (classExists("org.magmafoundation.magma.Magma"))
            return Platform.Magma;
        else if (classExists("com.mohistmc.MohistMC"))
            return Platform.Mohist;
        else if (classExists("io.izzel.arclight.boot.AbstractBootstrap"))
            return Platform.Arclight;
        else if (classExists("org.cardboardpowered.CardboardConfig"))
            return Platform.Cardboard;
        else if (classExists("org.spongepowered.api.Sponge"))
            return Platform.Sponge;
        else if (classExists("net.minecraftforge.fml.server.ServerMain"))
            return Platform.Forge;
        else if (classExists("org.quiltmc.loader.impl.QuiltLoaderConfig"))
            return Platform.Quilt;
        else if (classExists("net.fabricmc.fabric.mixin.event.entity.EntityMixin"))
            return Platform.Fabric;
        else if (classExists("org.purpurmc.purpur.event.player.PlayerBookTooLargeEvent"))
            return Platform.Purpur;
        else if (classExists("io.papermc.paper.event.player.AsyncChatEvent"))
            return Platform.Paper;
        else if (classExists("org.spigotmc.CustomTimingsHandler"))
            return Platform.Spigot;
        else if (classExists("org.bukkit.Bukkit"))
            return Platform.Bukkit;
        else
            return Platform.NULL;
    }
}

