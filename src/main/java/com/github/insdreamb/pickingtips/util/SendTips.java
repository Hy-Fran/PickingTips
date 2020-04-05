package com.github.insdreamb.pickingtips.util;

import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class SendTips {

    public static void sendTips(ItemStack item,Player player,String name){
            String str = Variable.systemPrefix + Variable.pick_success.replace("<amount>", Integer.toString(item.getAmount())).replace("<item_name>", name);
            player.sendMessage(str);
    }

    public static void isNotyours(Item item, Player player,String name){
        String str = Variable.systemPrefix + Variable.pick_fault.replace("<item_name>",name).replace("<player>",Variable.pickup.get(item.getUniqueId()));
        player.sendMessage(str);
    }

}
