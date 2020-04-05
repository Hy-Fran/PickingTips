package main.java.com.github.insdreamb.pickingtips.command;

import main.java.com.github.insdreamb.pickingtips.PickingTips;
import org.bukkit.command.CommandSender;

public abstract class CommandSub{
    private String sub;
    private String args;
    private String description;
    private String permission;
    private boolean console;
    protected PickingTips pickingTips;

    public CommandSub(PickingTips pickingTips, String sub, String args, String description, String permission,boolean console){
        this.pickingTips = pickingTips;
        this.sub = sub;
        this.args = args;
        this.description = description;
        this.permission = permission;
        this.console = console;
    }

    public String getSub() {
        return sub;
    }

    protected PickingTips getPickingTips() {
        return pickingTips;
    }

    public String getArgs() {
        return args;
    }

    public String getDescription() {
        return description;
    }

    public String getPermission() {
        return permission;
    }

    public boolean allowConsole() {
        return console;
    }

    public abstract boolean execute(CommandSender commandSender, String[] args);

}
