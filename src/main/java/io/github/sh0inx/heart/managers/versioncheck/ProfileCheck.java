package io.github.sh0inx.heart.managers.versioncheck;

import io.github.sh0inx.heart.Heart;
import io.github.sh0inx.heart.MessageType;
import io.github.sh0inx.heart.Profile;

public class ProfileCheck {

    private final Heart heart;
    private boolean paper;
    private boolean purpur;

    private boolean getPaperMode() {
        return paper;
    }
    private void setPaperMode(boolean paper) {
        this.paper = paper;
    }

    private boolean getPurpurMode() {
        return purpur;
    }
    private void setPurpurMode(boolean purpur) {
        this.purpur = purpur;
    }

    public ProfileCheck(Heart heart) {
        this.heart = heart;
    }

    public static boolean classExists(String className) {
        try {
            Class.forName(className);
            return true;
        } catch (ClassNotFoundException e) {
            return false;
        }
    }

    public Profile determineProfile() {
        setPaperMode(classExists("com.destroystokyo.paper.PaperConfig"));
        setPurpurMode(classExists("org.purpurmc.purpur.PurpurConfig"));

        if(getPaperMode() && !getPurpurMode()) {
            heart.consoleMessage(MessageType.PROFILE);
            return Profile.PAPER;
        }

        if(getPaperMode() && getPurpurMode()) {
            heart.consoleMessage(MessageType.PROFILE);
            return Profile.PURPUR;
        }

        heart.logMessage(MessageType.LOG, "If you want to take full advantage of " + heart.getDescription().getName());
        heart.logMessage(MessageType.LOG, "and receive a significant performance boost, consider switching over to a fork of Spigot.");
        heart.logMessage(MessageType.LOG, "PaperMC » https://papermc.io/");
        heart.logMessage(MessageType.LOG, "PurpurMC » https://purpurmc.org/");

        return Profile.SPIGOT;
    }
}
