package ru.azenizzka.xplugin.utils;

import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import ru.azenizzka.xplugin.XPlugin;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class BlockUtils {
	private static final Set<Block> processingBlocks = new HashSet<>();

	public static void breakBlocks(Player player, List<Block> blocks) {
		synchronized (processingBlocks) {
			blocks.removeIf(block -> !processingBlocks.add(block));

			ItemStack tool = player.getInventory().getItemInMainHand();
			Damageable damageable = (Damageable) tool.getItemMeta();

			int unbreakingCoeff = tool.getEnchantmentLevel(Enchantment.DURABILITY) + 1;
			int maxDamage = damageable.getDamage() + blocks.size() / unbreakingCoeff;

			if (tool.getType().getMaxDurability() - maxDamage < 0)
				return;

			damageable.setDamage(maxDamage);
			tool.setItemMeta(damageable);

			for (int i = 0; i < blocks.size(); i++) {
				Block block = blocks.get(i);

				if (!block.isPreferredTool(tool))
					continue;

				Bukkit.getScheduler().runTaskLater(XPlugin.instance, () -> {
					block.breakNaturally(tool, true, true);
					synchronized (processingBlocks) {
						processingBlocks.remove(block);
					}
				}, 3L * i);
			}
		}
	}
}
