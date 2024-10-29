package ru.azenizzka.xplugin.oreExcavation;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import ru.azenizzka.xplugin.utils.BlockUtils;
import ru.azenizzka.xplugin.utils.ItemUtils;

import java.util.List;

public class OreEvents implements Listener {

	@EventHandler
	public void onOreExcavated(BlockBreakEvent event) {
		Player player = event.getPlayer();
		Block block = event.getBlock();

		if (!ItemUtils.isPickaxeItem(player.getInventory().getItemInMainHand()))
			return;

		if (OreProcessor.isOre(block)) {
			List<Block> ores = OreProcessor.getNearbyOres(block);
			BlockUtils.breakBlocks(player, ores);
		}
	}
}