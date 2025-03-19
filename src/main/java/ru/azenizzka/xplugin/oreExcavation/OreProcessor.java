package ru.azenizzka.xplugin.oreExcavation;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.bukkit.Material;
import org.bukkit.block.Block;

public class OreProcessor {

  public static boolean isOre(Block block) {
    Material material = block.getBlockData().getMaterial();

    return material.toString().endsWith("_ORE");
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
