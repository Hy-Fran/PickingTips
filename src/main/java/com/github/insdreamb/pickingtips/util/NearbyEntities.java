package com.github.insdreamb.pickingtips.util;

import com.github.insdreamb.pickingtips.PickingTips;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;

public class NearbyEntities {

    public NearbyEntities(PickingTips pickingTips, EntityDeathEvent event, String entityName){
        Entity entity = event.getEntity();
        new BukkitRunnable(){
            @Override
            public void run() {
                List<Entity> entities = entity.getNearbyEntities(Variable.X,Variable.Y,Variable.Z);
                for(int i = 0;i<entities.size();i++){
                    if (entities.get(i) instanceof Item){
                       String itemName = ((Item) entities.get(i)).getItemStack().getItemMeta().getDisplayName();
                       if (itemName != null){
                           List<String> list = Variable.drops.get(entityName);
                           for (int i1 = 0;i1 <list.size();i1++){
                               if (itemName.contains(list.get(i1))){
                                   Variable.pickup.put(entities.get(i).getUniqueId(),event.getEntity().getKiller().getName());
                                   new Limit(pickingTips,entities.get(i).getUniqueId());
                               }
                           }
                       }
                    }
                }
                this.cancel();
            }
        }.runTaskLater(pickingTips,1);
    }
}
