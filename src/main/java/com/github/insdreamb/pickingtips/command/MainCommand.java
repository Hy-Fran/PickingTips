package com.github.insdreamb.pickingtips.command;

import com.github.insdreamb.pickingtips.PickingTips;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public class MainCommand implements CommandExecutor {
    public Map<String,CommandSub> commandSubMap = new HashMap<>();
    private PickingTips pickingTips;

    public MainCommand(PickingTips pickingTips){
        this.pickingTips = pickingTips;
    }

    public void registers(CommandSub... commandSubs){
        for (CommandSub commandSub:commandSubs){
            commandSubMap.put(commandSub.getSub(),commandSub);
        }
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        if (args.length == 0){
            return help(commandSender,command);
        }
        CommandSub commandSub = commandSubMap.get(args[0].toLowerCase());
        if (commandSub != null){
            if (commandSender.hasPermission(commandSub.getPermission())) {
                if (commandSub.allowConsole()){
                    return commandSub.execute(commandSender, args);
                }
                else {
                    if (commandSender instanceof Player){
                        return commandSub.execute(commandSender,args);
                    }
                    else {
                        commandSender.sendMessage(pickingTips.config.getString("message.console"));
                        return false;
                    }
                }
            }
            else {
                commandSender.sendMessage(pickingTips.config.getString("message.cant_do_it").replace("&","§"));
                return false;
            }
        }
        else{
            return help(commandSender,command);
        }
    }

    public boolean help(CommandSender commandSender,Command command){
        for (CommandSub value:commandSubMap.values()){
            if (commandSender.hasPermission(value.getPermission())) {
                commandSender.sendMessage(
                        pickingTips.config.getString("message.command")
                                .replace("<command>",command.getName())
                                .replace("<command_sub>",value.getSub())
                                .replace("<command_args>",value.getArgs())
                                .replace("<command_description>",value.getDescription())
                                .replace("&","§")
                );
            }
        }
        return false;
    }

}
