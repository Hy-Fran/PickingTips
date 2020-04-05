package main.java.com.github.insdreamb.pickingtips.command.sub;

import main.java.com.github.insdreamb.pickingtips.PickingTips;
import main.java.com.github.insdreamb.pickingtips.command.CommandSub;
import org.bukkit.command.CommandSender;

public class Help extends CommandSub {
    public Help(PickingTips pickingTips){
        super(pickingTips,"help","","§7获取所有指令","PickingTips.help",true);
    }
    @Override
    public boolean execute(CommandSender commandSender, String[] args) {
        if (args.length == 1) {
            for (CommandSub value : pickingTips.getMainCommand().commandSubMap.values()) {
                if (commandSender.hasPermission(value.getPermission())) {
                    commandSender.sendMessage(
                            pickingTips.config.getString("message.command")
                                    .replace("<command>", "tips")
                                    .replace("<command_sub>", value.getSub())
                                    .replace("<command_args>", value.getArgs())
                                    .replace("<command_description>", value.getDescription())
                                    .replace("&", "§")
                    );
                }
            }
            return true;
        }
        else {
            commandSender.sendMessage(pickingTips.config.getString("message.error_command").replace("&", "§"));
            return false;
        }
    }
}
