package ru.azenizzka.xplugin.utils;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class ItemUtils {
  public static boolean isToolItem(ItemStack item) {
    if (item.getType() == Material.AIR) return false;

    if (isAxeItem(item) || isPickaxeItem(item)) return true;

    switch (item.getType()) {
      case WOODEN_SHOVEL:
      case WOODEN_HOE:

      case STONE_SHOVEL:
      case STONE_HOE:

      case IRON_SHOVEL:
      case IRON_HOE:

      case GOLDEN_SHOVEL:
      case GOLDEN_HOE:

      case DIAMOND_SHOVEL:
      case DIAMOND_HOE:

      case NETHERITE_SHOVEL:
      case NETHERITE_HOE:
        return true;
      default:
        return false;
    }
  }

  public static boolean isAxeItem(ItemStack item) {
    if (item.getType() == Material.AIR) return false;

    switch (item.getType()) {
      case WOODEN_AXE:
      case STONE_AXE:
      case IRON_AXE:
      case GOLDEN_AXE:
      case DIAMOND_AXE:
      case NETHERITE_AXE:
        return true;
      default:
        return false;
    }
  }

  public static boolean isPickaxeItem(ItemStack item) {
    if (item.getType() == Material.AIR) return false;

    switch (item.getType()) {
      case WOODEN_PICKAXE:
      case STONE_PICKAXE:
      case IRON_PICKAXE:
      case GOLDEN_PICKAXE:
      case DIAMOND_PICKAXE:
      case NETHERITE_PICKAXE:
        return true;
      default:
        return false;
    }
  }
}
