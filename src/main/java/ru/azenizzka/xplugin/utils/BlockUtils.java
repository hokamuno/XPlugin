package ru.azenizzka.xplugin.utils;

import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import ru.azenizzka.xplugin.XPlugin;

import java.util.List;

public class BlockUtils {
	public static void breakBlocks(Player player, List<Block> blocks) {
		ItemStack tool = player.getInventory().getItemInMainHand();
		Damageable damageable = (Damageable) tool.getItemMeta();

		int maxDurability = tool.getType().getMaxDurability();
		int unbreakingCoeff = tool.getEnchantmentLevel(Enchantment.DURABILITY) + 1;


		int maxDamage = damageable.getDamage() + blocks.size() / unbreakingCoeff;

		if (maxDurability - maxDamage < 0)
			return;

		damageable.setDamage(maxDamage);
		tool.setItemMeta(damageable);


		for (int i = 0; i < blocks.size(); i++) {
			Block block = blocks.get(i);

			if (!block.isPreferredTool(tool))
				continue;

			BukkitTask bukkitTask = new BukkitRunnable() {
				@Override
				public void run() {
					block.breakNaturally(tool, true, true);
				}
			}.runTaskLater(XPlugin.instance, 3L * i);
		}

	}
}

