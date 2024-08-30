package ru.azenizzka.xplugin.misc;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import ru.azenizzka.xplugin.utils.BlockUtils;

public class BlockEvents implements Listener {
	@EventHandler
	public void onBreakBlock(BlockBreakEvent event) {
		ItemStack tool = event.getPlayer().getInventory().getItemInMainHand();

		if (tool.getType().isItem())
			BlockUtils.increaseBrokenBlocks(tool, 1L);
	}
}
