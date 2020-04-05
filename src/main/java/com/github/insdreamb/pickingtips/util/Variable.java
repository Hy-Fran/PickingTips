package com.github.insdreamb.pickingtips.util;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class Variable {

    public static String pick_success;
    public static String pick_fault;
    public static String systemPrefix;
    public static String entity;
    public static int bind_time;
    public static int block_time;
    public static double X;
    public static double Y;
    public static double Z;
    /** 判断物品正在拾取人的名字是否和pickup的值相同 **/
    public static Map<UUID,String> pickup = new ConcurrentHashMap<>();
    /**是否取消物品提示 **/
    public static Map<Player,Boolean> map = new HashMap<>();
    /** 是否禁止物品丢出去后别人可以立即拾取 **/
    public static Map<Player,Boolean> blockingPickup  = new HashMap<>();
    /** 玩家是否可以立即接受禁止拾取物品的消息 **/
    public static Map<Player,Boolean> blocktips = new ConcurrentHashMap<>();
    /** 击杀实体绑定玩家的物品 **/
    public static Map<String,List<String>> drops = new HashMap<>();

    /** 判断玩家是否开启拾取提示并且物品改过名 **/
    public static boolean sendTips(Player player, ItemStack item){
        return Variable.map.get(player) && item.hasItemMeta() && item.getItemMeta().hasDisplayName();
    }

    /** 判断玩家是否开启拾取提示 **/
    public static boolean sendTips(Player player){
        return Variable.map.get(player);
    }
}
