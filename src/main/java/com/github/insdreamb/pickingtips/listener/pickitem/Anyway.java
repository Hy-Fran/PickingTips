package com.github.insdreamb.pickingtips.listener.pickitem;

import com.github.insdreamb.pickingtips.PickingTips;
import com.github.insdreamb.pickingtips.util.Limit;
import com.github.insdreamb.pickingtips.util.SendTips;
import com.github.insdreamb.pickingtips.util.Variable;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.inventory.ItemStack;

public class Anyway implements Listener {
    private PickingTips pickingTips;

    public Anyway(PickingTips pickingTips) {
        this.pickingTips = pickingTips;
    }

    @EventHandler
    public void pickItem(PlayerPickupItemEvent event) {
        ItemStack item = event.getItem().getItemStack();
            if (Variable.pickup.containsKey(event.getItem().getUniqueId())) {
                String name = pickingTips.getItemname(item);
                if (Variable.pickup.get(event.getItem().getUniqueId()).equals(event.getPlayer().getName())) {
                    if (Variable.sendTips(event.getPlayer())) SendTips.sendTips(item, event.getPlayer(),name);
                }
                else {
                    if (!Variable.blocktips.get(event.getPlayer())) {
                        SendTips.isNotyours(event.getItem(), event.getPlayer(),name);
                        Variable.blocktips.put(event.getPlayer(), true);
                        new Limit(pickingTips, event.getPlayer());
                    }
                    event.setCancelled(true);
                }
            }
            else {
                String name = pickingTips.getItemname(item);
                if (Variable.sendTips(event.getPlayer())) SendTips.sendTips(item, event.getPlayer(),name);
            }
    }
}
