package ru.azenizzka.xplugin.treeCapitator;

import java.util.List;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import ru.azenizzka.xplugin.utils.BlockUtils;
import ru.azenizzka.xplugin.utils.ItemUtils;

public class TreeCapitatorEvents implements Listener {

  @EventHandler
  public void onTreeBroken(BlockBreakEvent event) {
    Player player = event.getPlayer();
    Block block = event.getBlock();

    if (!ItemUtils.isAxeItem(player.getInventory().getItemInMainHand())) return;

    if (!TreeCapitatorProcessor.isLog(block.getType())) return;

    List<Block> blocks;
    try {
      blocks = TreeCapitatorProcessor.getTreeBlocks(block);
    } catch (Exception e) {
      return;
    }

    BlockUtils.breakBlocks(player, blocks);
  }
}
