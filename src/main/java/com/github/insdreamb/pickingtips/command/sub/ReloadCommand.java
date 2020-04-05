package main.java.com.github.insdreamb.pickingtips.command.sub;

import main.java.com.github.insdreamb.pickingtips.PickingTips;
import main.java.com.github.insdreamb.pickingtips.command.CommandSub;
import org.bukkit.command.CommandSender;

public class ReloadCommand extends CommandSub {

    public ReloadCommand(PickingTips pickingTips){
        super(pickingTips,"reload","","§7重载配置文件","PickingTips.reload",true);
    }

    @Override
    public boolean execute(CommandSender commandSender, String[] args) {
        getPickingTips().reload();
        commandSender.sendMessage(getPickingTips().config.getString("message.reload").replace("&","§"));
        return true;
    }

}
