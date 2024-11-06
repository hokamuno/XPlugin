package ru.azenizzka.xplugin.oreExcavation;

import java.util.*;
import org.bukkit.Material;
import org.bukkit.block.Block;

public class OreProcessor {
  public static boolean isOre(Block block) {
    Material material = block.getBlockData().getMaterial();

    switch (material) {
      case COAL_ORE:
      case DEEPSLATE_COAL_ORE:

      case IRON_ORE:
      case DEEPSLATE_IRON_ORE:

      case GOLD_ORE:
      case DEEPSLATE_GOLD_ORE:
      case GILDED_BLACKSTONE:

      case LAPIS_ORE:
      case DEEPSLATE_LAPIS_ORE:

      case REDSTONE_ORE:
      case DEEPSLATE_REDSTONE_ORE:

      case EMERALD_ORE:
      case DEEPSLATE_EMERALD_ORE:

      case DIAMOND_ORE:
      case DEEPSLATE_DIAMOND_ORE:

      case COPPER_ORE:
      case DEEPSLATE_COPPER_ORE:

      case NETHER_QUARTZ_ORE:
      case NETHER_GOLD_ORE:
      case ANCIENT_DEBRIS:
        return true;
    }

    return false;
  }

  public static List<Block> getNearbyOres(Block block) {
    List<Block> blocks = new ArrayList<>();
    Set<Block> processedBlocks = new HashSet<>();

    getNearbyOresRecursive(block, blocks, processedBlocks);

    return blocks;
  }

  private static void getNearbyOresRecursive(
      Block block, List<Block> oreBlocks, Set<Block> processedBlocks) {
    if (!isOre(block) || !processedBlocks.add(block)) return;

    oreBlocks.add(block);

    Block[] neighbors = {
      block.getRelative(0, 1, 0),
      block.getRelative(0, -1, 0),
      block.getRelative(1, 0, 0),
      block.getRelative(-1, 0, 0),
      block.getRelative(0, 0, 1),
      block.getRelative(0, 0, -1)
    };

    for (Block neighbor : neighbors) {
      getNearbyOresRecursive(neighbor, oreBlocks, processedBlocks);
    }
  }
}
