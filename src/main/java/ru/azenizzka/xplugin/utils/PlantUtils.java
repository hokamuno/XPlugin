package ru.azenizzka.xplugin.utils;

import org.bukkit.Material;

public class PlantUtils {
  public static boolean isPlant(Material plant) {
    switch (plant) {
      case WHEAT_SEEDS:
      case CARROT:
      case NETHER_WART:
      case POTATO:
      case BEETROOT:
        return true;
    }

    return false;
  }

  public static Material getSeed(Material plant) {
    switch (plant) {
      case CARROTS:
        return Material.CARROTS;
      case NETHER_WART:
        return Material.NETHER_WART;
      case POTATO:
        return Material.POTATOES;
      case BEETROOT:
        return Material.BEETROOTS;
      case WHEAT:
        return Material.WHEAT;
    }

    return Material.AIR;
  }
}
