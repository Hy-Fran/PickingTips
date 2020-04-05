package main.java.com.github.insdreamb.pickingtips;

import main.java.com.github.insdreamb.pickingtips.command.sub.DisableBlock;
import main.java.com.github.insdreamb.pickingtips.command.sub.DisableTips;
import main.java.com.github.insdreamb.pickingtips.command.MainCommand;
import main.java.com.github.insdreamb.pickingtips.command.sub.Help;
import main.java.com.github.insdreamb.pickingtips.command.sub.ReloadCommand;
import main.java.com.github.insdreamb.pickingtips.listener.Listener;
import main.java.com.github.insdreamb.pickingtips.listener.pickitem.Anyway;
import main.java.com.github.insdreamb.pickingtips.listener.pickitem.hasDisplayName;
import main.java.com.github.insdreamb.pickingtips.util.Variable;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public class PickingTips extends JavaPlugin {
    private MainCommand mainCommand;
    private PickingTips pickingTips;
    public FileConfiguration config;
    public FileConfiguration items;
    private final String DATAFOLDER = "plugins"+File.separator+"PickingTips";

    @Override
    public void onEnable() {
        load();
        this.mainCommand = new MainCommand(this);
        mainCommand.registers(
                new ReloadCommand(this),
                new DisableTips(this),
                new DisableBlock(this),
                new Help(this)
        );
        getCommand("tips").setExecutor(mainCommand);
        this.pickingTips = this;
        this.getServer().getLogger().info("[拾取提示] PickingTips 启动中... ");
        this.getServer().getLogger().info("[拾取提示] PickingTips 作者:InsDreamb 禁止私自二次创作发布或者倒卖 需授权");
    }

    @Override
    public void onDisable() {
        this.save();
        this.unload();
    }

    public FileConfiguration loadFile(File file){
        try{
            if (!file.exists()){this.saveResource(file.getName(),true);}
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return YamlConfiguration.loadConfiguration(file);
    }

    public void load(){
        this.config = loadFile(new File(DATAFOLDER,"config.yml"));
        Variable.pick_success = replaceChar(config.getString("message.pick_success"));
        Variable.pick_fault = replaceChar(config.getString("message.pick_fault"));
        Variable.systemPrefix = replaceChar(config.getString("message.prefix"));
        Variable.block_time = config.getInt("Functions.block_time");
        Variable.bind_time = config.getInt("Functions.bind_time");
        Variable.entity = config.getString("Functions.entity");
        Variable.X = config.getInt("Functions.X");
        Variable.Y = config.getInt("Functions.Y");
        Variable.Z = config.getInt("Functions.Z");
        this.getServer().getLogger().info("[拾取提示] 注册监听器中..");
        loadEvents();
        for (String key:config.getConfigurationSection("Drops").getKeys(false)) {
            Variable.drops.put(key,config.getStringList("Drops."+key));
        }
        Plugin rpgitem  = this.getServer().getPluginManager().getPlugin("RPG_Items");
        if (rpgitem != null && config.getBoolean("Functions.Unregister")) PlayerPickupItemEvent.getHandlerList().unregister(rpgitem);
        if (config.getString("Functions.entity").equalsIgnoreCase("null")) EntityDeathEvent.getHandlerList().unregister(this);
    }

    public void unload(){
        Variable.drops.clear();
        Variable.pickup.clear();
        Variable.blocktips.clear();
        Bukkit.getScheduler().cancelTasks(this);
        HandlerList.unregisterAll(this);
        this.getServer().getLogger().info("[拾取提示] 卸载监听器中..");
    }

    public void reload(){
        this.unload();
        this.load();
        for (Player player:this.getServer().getOnlinePlayers()){
            Variable.map.put(player,true);
            Variable.blocktips.put(player,false);
            Variable.blockingPickup.put(player,true);
        }
    }

    private String replaceChar(String str){
        return str.replaceAll("&","§");
    }

    private void save(){
        try {
            config.save(new File(DATAFOLDER, "config.yml"));
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    private void loadEvents(){
        this.getServer().getPluginManager().registerEvents(new Listener(this),this);
        boolean displayername = config.getBoolean("Functions.displayname");
        if (displayername) this.getServer().getPluginManager().registerEvents(new hasDisplayName(this),this);
        else {
            Plugin miaochat = this.getServer().getPluginManager().getPlugin("MiaoChat");
            if (miaochat != null) {
                this.items = loadFile(new File("plugins" + File.separator + "YumCore" + File.separator + "cache", "Item_zh_CN.yml"));
                this.getServer().getPluginManager().registerEvents(new Anyway(this), this);
            }
            else this.getServer().getLogger().info("[拾取提示] 你的服务器没有安装MiaoChat所以不能使用带原版的物品拾取提示");
        }
        if (!config.getBoolean("Functions.bind")){
            PlayerDeathEvent.getHandlerList().unregister(this);
            PlayerDropItemEvent.getHandlerList().unregister(this);
        }
    }

    public String getItemname(ItemStack item){
        String name;
        if (item.hasItemMeta() && item.getItemMeta().hasDisplayName()) name = item.getItemMeta().getDisplayName();
        else if (pickingTips.items.contains(item.getType().toString())) name = pickingTips.items.getString(item.getType().toString());
        else name = item.getType().toString();
        return name;
    }

    public MainCommand getMainCommand() {
        return mainCommand;
    }
}
