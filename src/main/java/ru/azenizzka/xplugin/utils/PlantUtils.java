package ru.azenizzka.xplugin.utils;

import org.bukkit.Material;

public class PlantUtils {
  public static boolean isPlant(Material plant) {
    switch (plant) {
      case WHEAT:
      case CARROTS:
      case NETHER_WART:
      case POTATOES:
      case BEETROOTS:
        return true;
      default:
        return false;
    }
  }
}
