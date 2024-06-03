package ru.azenizzka.xplugin.treeCapitator;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import ru.azenizzka.xplugin.utils.BlockUtils;

import java.util.List;

public class TreeCapitatorEvents implements Listener {

	@EventHandler
	public void onTreeBroken(BlockBreakEvent event) {
		Player player = event.getPlayer();
		Block block = event.getBlock();

		if (TreeCapitatorProcessor.isLog(block) && player.isSneaking()) {
			List<Block> blocks = TreeCapitatorProcessor.getTreeBlocks(block);
			BlockUtils.breakBlocks(player, blocks);
		}
	}
}
