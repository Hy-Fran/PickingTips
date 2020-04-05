package com.github.insdreamb.pickingtips.util;

import com.github.insdreamb.pickingtips.PickingTips;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.UUID;

public class Limit {

    /** 绑定时间 **/
    public Limit (PickingTips pickingTips,UUID item){
        new BukkitRunnable(){
            @Override
            public void run() {
                Variable.pickup.remove(item);
                this.cancel();
            }
        }.runTaskLaterAsynchronously(pickingTips,Variable.bind_time*20);
    }

    /** 封锁拾取失败提示 **/
    public Limit (PickingTips pickingTips, Player player){
        new BukkitRunnable(){
            @Override
            public void run() {
                Variable.blocktips.put(player,false);
                this.cancel();
            }
        }.runTaskLaterAsynchronously(pickingTips,Variable.block_time*20);
    }
}
