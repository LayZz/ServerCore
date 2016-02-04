package de.flashbeatzz.servercore.utils.enchantment;

import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EnchantmentHelper {

    public static ItemStack addEnchantments(ItemStack itemstack, HashMap<Enchantment, Integer> map, Boolean showInLore) {
        HashMap<org.bukkit.enchantments.Enchantment, Integer> map1 = new HashMap<>();
        for(Map.Entry<Enchantment, Integer> entry : map.entrySet()) {
            map1.put(entry.getKey().getBukkitEnchantment(), entry.getValue());
        }

        if(showInLore) {
            itemstack.getItemMeta().getLore().add("\n");
            itemstack.addUnsafeEnchantments(map1);
        }
        List<String> list = itemstack.getItemMeta().getLore();
        itemstack.addUnsafeEnchantments(map1);
        itemstack.getItemMeta().getLore().clear();
        itemstack.getItemMeta().setLore(list);
        return itemstack;
    }

    public static ItemStack addEnchantment(ItemStack itemstack, Enchantment ench, Integer lvl, Boolean showInLore) {
        if(showInLore) {
            itemstack.getItemMeta().getLore().add("\n");
            itemstack.addUnsafeEnchantment(ench.getBukkitEnchantment(), lvl);
        }
        List<String> list = itemstack.getItemMeta().getLore();
        itemstack.addUnsafeEnchantment(ench.getBukkitEnchantment(), lvl);
        itemstack.getItemMeta().getLore().clear();
        itemstack.getItemMeta().setLore(list);
        return itemstack;
    }

    public static ItemStack addEnchantments(ItemStack itemstack, HashMap<Enchantment, Integer> map) {
        HashMap<org.bukkit.enchantments.Enchantment, Integer> map1 = new HashMap<>();
        for(Map.Entry<Enchantment, Integer> entry : map.entrySet()) {
            map1.put(entry.getKey().getBukkitEnchantment(), entry.getValue());
        }
        itemstack.addUnsafeEnchantments(map1);
        return itemstack;
    }

    public static ItemStack addEnchantment(ItemStack itemstack, Enchantment ench, Integer lvl) {
        itemstack.addUnsafeEnchantment(ench.getBukkitEnchantment(), lvl);
        return itemstack;
    }

    public static ItemStack enchantAll(ItemStack itemstack, Integer lvl) {
        HashMap<org.bukkit.enchantments.Enchantment, Integer> map = new HashMap<>();
        for(Enchantment ench : Enchantment.values()) {
            map.put(ench.getBukkitEnchantment(), lvl);
        }
        itemstack.addUnsafeEnchantments(map);
        return itemstack;
    }

}
