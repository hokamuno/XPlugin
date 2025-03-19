package ru.azenizzka.xplugin.treeCapitator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;

public class TreeCapitatorProcessor {
  private static final BlockFace[] faces =
      new BlockFace[] {
        BlockFace.UP,
        BlockFace.DOWN,
        BlockFace.NORTH,
        BlockFace.NORTH_EAST,
        BlockFace.EAST,
        BlockFace.SOUTH_EAST,
        BlockFace.SOUTH,
        BlockFace.SOUTH_WEST,
        BlockFace.WEST,
        BlockFace.NORTH_WEST
      };

  public static boolean isLog(Material material) {
    String name = material.name();
    return (name.endsWith("_LOG")
            || name.equals("CRIMSON_STEM")
            || name.equalsIgnoreCase("WARPED_STEM"))
        && !name.startsWith("STRIPPED");
  }

  public static boolean isLeave(Material material) {
    String name = material.name();
    return name.endsWith("_LEAVES");
  }

  public static List<Block> getTreeBlocks(Block block) throws Exception {
    List<Block> blocks = new ArrayList<>();
    List<Block> leaves = new ArrayList<>();

    getNearbyLogsRecursive(block, blocks, leaves);

    if (leaves.size() < 4 || !isTree(blocks)) throw new Exception();

    return blocks;
  }

  private static boolean isTree(List<Block> blocks) {
    Map<Integer, Integer> heightsMap = new HashMap<>();

    blocks.stream()
        .forEach(
            block -> {
              int height = block.getY();

              if (heightsMap.containsKey(height)) {
                int value = heightsMap.get(height);
                heightsMap.put(height, value + 1);
              } else {
                heightsMap.put(height, 1);
              }
            });

    return heightsMap.values().stream().allMatch(count -> count <= 6);
  }

  private static void getNearbyLogsRecursive(Block block, List<Block> blocks, List<Block> leaves) {
    if (isLeave(block.getType()) && leaves.size() < 4) {
      leaves.add(block);
      return;
    }

    if (!isLog(block.getType()) || blocks.contains(block)) return;
    blocks.add(block);

    for (BlockFace face : faces) {

      BlockFace[] relatives = new BlockFace[] {BlockFace.SELF, BlockFace.UP, BlockFace.DOWN};

      for (BlockFace relative : relatives) {
        getNearbyLogsRecursive(block.getRelative(face).getRelative(relative), blocks, leaves);
      }
    }
  }
}
