package ru.azenizzka.xplugin.oreExcavation;

import java.util.List;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import ru.azenizzka.xplugin.utils.BlockUtils;
import ru.azenizzka.xplugin.utils.ItemUtils;

public class OreEvents implements Listener {

  @EventHandler
  public void onOreExcavated(BlockBreakEvent event) {
    Player player = event.getPlayer();

    if (player.isSneaking()) return;

    Block block = event.getBlock();

    if (!ItemUtils.isPickaxeItem(player.getInventory().getItemInMainHand())) return;

    if (OreProcessor.isOre(block)) {
      List<Block> ores = OreProcessor.getNearbyOres(block);
      BlockUtils.breakBlocks(player, ores);
    }
  }
}
