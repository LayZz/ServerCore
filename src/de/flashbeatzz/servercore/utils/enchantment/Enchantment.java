package de.flashbeatzz.servercore.utils.enchantment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public enum Enchantment {
    PROTECTION(0, "Protection", org.bukkit.enchantments.Enchantment.PROTECTION_ENVIRONMENTAL, Type.ARMOR),
    FIRE_PROTECTION(1, "Fire Protection", org.bukkit.enchantments.Enchantment.PROTECTION_FIRE, Type.ARMOR),
    FEATHER_FALLING(2, "Feather Falling", org.bukkit.enchantments.Enchantment.PROTECTION_FALL, Type.BOOTS),
    BLAST_PROTECTION(3, "Blast Protection", org.bukkit.enchantments.Enchantment.PROTECTION_EXPLOSIONS, Type.ARMOR),
    PROJECTILE_PROTECTION(4, "Projectile Protection", org.bukkit.enchantments.Enchantment.PROTECTION_PROJECTILE, Type.ARMOR),
    RESPIRATION(5, "Respiration", org.bukkit.enchantments.Enchantment.OXYGEN, Type.HELMET),
    AQUA_AFFINITY(6, "Aqua Affinity", org.bukkit.enchantments.Enchantment.WATER_WORKER, Type.HELMET),
    THORNS(7, "Thorns", org.bukkit.enchantments.Enchantment.THORNS, Type.ARMOR),
    DEPTH_STRIDER(8, "Depth Strider", org.bukkit.enchantments.Enchantment.DEPTH_STRIDER, Type.BOOTS),
    SHARPNESS(16, "Sharpness", org.bukkit.enchantments.Enchantment.DAMAGE_ALL, Type.SWORD, Type.AXE),
    SMITE(17, "Smite", org.bukkit.enchantments.Enchantment.DAMAGE_UNDEAD, Type.SWORD, Type.AXE),
    BANE_OF_ARTHROPODS(18, "Bane of Arthropods", org.bukkit.enchantments.Enchantment.DAMAGE_ARTHROPODS, Type.SWORD, Type.AXE),
    KNOCKBACK(19, "Knockback", org.bukkit.enchantments.Enchantment.KNOCKBACK, Type.SWORD),
    FIRE_ASPECT(20, "Fire Aspect", org.bukkit.enchantments.Enchantment.FIRE_ASPECT, Type.SWORD),
    LOOTING(21, "Looting", org.bukkit.enchantments.Enchantment.LOOT_BONUS_MOBS, Type.SWORD),
    EFFICIENCY(32, "Efficiency", org.bukkit.enchantments.Enchantment.DIG_SPEED, Type.TOOLS, Type.SHEAR),
    SILK_TOUCH(33, "Silk Touch", org.bukkit.enchantments.Enchantment.SILK_TOUCH, Type.TOOLS, Type.SHEAR),
    UNBREAKING(34, "Unbreaking", org.bukkit.enchantments.Enchantment.DURABILITY, Type.ALL),
    FORTUNE(35, "Fortune", org.bukkit.enchantments.Enchantment.LOOT_BONUS_BLOCKS, Type.TOOLS),
    POWER(48, "Power", org.bukkit.enchantments.Enchantment.ARROW_DAMAGE, Type.BOW),
    PUNCH(49, "Punch", org.bukkit.enchantments.Enchantment.ARROW_KNOCKBACK, Type.BOW),
    FLAME(50, "Flame", org.bukkit.enchantments.Enchantment.ARROW_FIRE, Type.BOW),
    INFINITY(51, "Infinity", org.bukkit.enchantments.Enchantment.ARROW_INFINITE, Type.BOW),
    LUCK_OF_THE_SEA(61, "Luck of the Sea", org.bukkit.enchantments.Enchantment.LUCK, Type.FISHING_ROD),
    LURE(62, "Lure", org.bukkit.enchantments.Enchantment.LURE, Type.FISHING_ROD);

    private Integer id;
    private String name;
    private List<Type> types = new ArrayList<>();
    private org.bukkit.enchantments.Enchantment bukkitEnchantment;

    private Enchantment(Integer id, String name, org.bukkit.enchantments.Enchantment bukkitEnchantment,Type... type) {
        this.id = id;
        this.name = name;
        Collections.addAll(this.types, type);
        this.bukkitEnchantment = bukkitEnchantment;
    }

    private enum Type {
        ARMOR,
        HELMET,
        CHESTPLATE,
        LEGGINGS,
        BOOTS,
        SWORD,
        SHOVEL,
        PICKAXE,
        HOE,
        AXE,
        FISHING_ROD,
        SHEAR,
        BOW,
        CARROT_ON_STICK,
        FLINT_AND_STEEL,
        ALL,
        TOOLS,
    }

    public Integer getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public List<Type> getTypes() {
        return this.types;
    }

    public org.bukkit.enchantments.Enchantment getBukkitEnchantment() {
        return this.bukkitEnchantment;
    }
}
