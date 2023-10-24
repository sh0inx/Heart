package io.github.sh0inx.heart.configs.beans;

import java.util.List;

public class CommandItemBean {
    private List<String> aliases;
    private String commandInfo;
    private String permissionBase;
    private String permission;
    private boolean enabled;

    public CommandItemBean(List<String> aliases, String commandInfo, String permissionBase, String permission, boolean enabled) {
        this.aliases = aliases;
        this.commandInfo = commandInfo;
        this.permissionBase = permissionBase;
        this.permission = permission;
        this.enabled = enabled;
    }

    public List<String> getAliases() {
        return aliases;
    }

    public String getCommandInfo() {
        return commandInfo;
    }

    public String getPermissionBase() {
        return permissionBase;
    }

    public String getPermission() {
        return permission;
    }

    public boolean getEnabled() {
        return enabled;
    }
}