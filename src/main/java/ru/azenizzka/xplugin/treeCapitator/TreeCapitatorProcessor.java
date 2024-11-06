package ru.azenizzka.xplugin.treeCapitator;

import java.util.ArrayList;
import java.util.List;
import org.bukkit.Material;
import org.bukkit.block.Block;

public class TreeCapitatorProcessor {
  public static boolean isLog(Block block) {
    Material material = block.getBlockData().getMaterial();

    switch (material) {
      case ACACIA_LOG:
      case BIRCH_LOG:
      case CHERRY_LOG:
      case DARK_OAK_LOG:
      case JUNGLE_LOG:
      case MANGROVE_LOG:
      case OAK_LOG:
      case SPRUCE_LOG:
      case MANGROVE_WOOD:
      case CRIMSON_STEM:
      case WARPED_STEM:
        return true;
    }

    return false;
  }

  public static List<Block> getTreeBlocks(Block block) {
    List<Block> blocks = new ArrayList<>();

    getNearbyOresRecursive(block, blocks);

    return blocks;
  }

  private static void getNearbyOresRecursive(Block block, List<Block> blocks) {
    if (!isLog(block) || blocks.contains(block)) return;

    blocks.add(block);

    Block[] neighbors = {block.getRelative(0, 1, 0), block.getRelative(0, -1, 0)};

    for (Block neighbor : neighbors) {
      getNearbyOresRecursive(neighbor, blocks);
    }
  }
}
