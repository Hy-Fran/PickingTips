package main.java.com.github.insdreamb.pickingtips.listener;

import main.java.com.github.insdreamb.pickingtips.PickingTips;
import main.java.com.github.insdreamb.pickingtips.util.Limit;
import main.java.com.github.insdreamb.pickingtips.util.NearbyEntities;
import main.java.com.github.insdreamb.pickingtips.util.Variable;
import me.dpohvar.powernbt.api.NBTCompound;
import me.dpohvar.powernbt.api.NBTManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDeathEvent;

import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerJoinEvent;

public class Listener implements org.bukkit.event.Listener {
    private PickingTips pickingTips;

    public Listener(PickingTips pickingTips){
        this.pickingTips = pickingTips;
    }

    @EventHandler
    private void onJoin(PlayerJoinEvent event){
        Variable.map.put(event.getPlayer(),true);
        Variable.blockingPickup.put(event.getPlayer(),false);
        Variable.blocktips.put(event.getPlayer(),true);
    }

    @EventHandler
    private void onDeath(EntityDeathEvent event){
            if (Variable.entity.equalsIgnoreCase("all") || (event.getEntity().getKiller() instanceof Player && event.getEntity().getType().name().equalsIgnoreCase(Variable.entity))) {
                NBTManager manager = NBTManager.nbtManager;
                NBTCompound compound = manager.read(event.getEntity());
                if (compound != null) {
                    String npcName = compound.getString("Name");
                    if (Variable.drops.containsKey(npcName)) {
                        new NearbyEntities(pickingTips, event, npcName);
                    }
                }
            }
    }

    @EventHandler
    private void onDrop(PlayerDropItemEvent event){
        if (Variable.blockingPickup.get(event.getPlayer())) {
            Variable.pickup.put(event.getItemDrop().getUniqueId(), event.getPlayer().getName());
            new Limit(pickingTips, event.getItemDrop().getUniqueId());
        }
    }
}
