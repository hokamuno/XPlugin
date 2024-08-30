package ru.azenizzka.xplugin.utils;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.CreativeCategory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import ru.azenizzka.xplugin.XPlugin;

import java.util.*;

public class BlockUtils {
	private static final Set<Block> processingBlocks = new HashSet<>();
	private static final String NUMBER_OF_BROKEN_BLOCKS_KEY = "BROKEN_BLOCKS_COUNT";

	public static void breakBlocks(Player player, List<Block> blocks) {
		ItemStack tool = player.getInventory().getItemInMainHand();
		ItemMeta meta = tool.getItemMeta();

		int efficiency = tool.getEnchantmentLevel(Enchantment.DIG_SPEED);
		int unbreakingCoeff = tool.getEnchantmentLevel(Enchantment.DURABILITY) + 1;
		int maxDamage = ((Damageable) meta).getDamage() + blocks.size() / unbreakingCoeff;

		if (tool.getType().getMaxDurability() - maxDamage < 0)
			return;

		synchronized (processingBlocks) {
			blocks.removeIf(block -> !processingBlocks.add(block));
		}

		maxDamage = ((Damageable) meta).getDamage() + blocks.size() / unbreakingCoeff;

		((Damageable) meta).setDamage(maxDamage);
		tool.setItemMeta(meta);

		for (int i = 0; i < blocks.size(); i++) {
			Block block = blocks.get(i);

			if (!block.isPreferredTool(tool))
				continue;

			Bukkit.getScheduler().runTaskLater(XPlugin.instance, () -> {
				if (block.isEmpty())
					return;

				block.breakNaturally(tool, true, true);
				increaseBrokenBlocks(tool, 1L);

				synchronized (processingBlocks) {
					processingBlocks.remove(block);
				}
			}, (18L - efficiency * 3L) * i);
		}
	}

	public static void increaseBrokenBlocks(ItemStack tool, Long value) {
		ItemMeta meta = tool.getItemMeta();

		tool.getType().getCreativeCategory().compareTo(CreativeCategory.TOOLS);

		if (meta == null)
			return;

		NamespacedKey key = new NamespacedKey(XPlugin.instance, NUMBER_OF_BROKEN_BLOCKS_KEY);

		if (!meta.getPersistentDataContainer().has(key))
			meta.getPersistentDataContainer().set(key, PersistentDataType.LONG, 0L);

		Long numberOfBrokenBlocks = meta.getPersistentDataContainer().get(key, PersistentDataType.LONG);
		numberOfBrokenBlocks += value;

		List<Component> lore = new ArrayList<>();

		lore.add(Component.text("Сломано блоков: ")
				.color(NamedTextColor.GRAY)
				.append(Component.text(numberOfBrokenBlocks).color(NamedTextColor.DARK_GREEN)));

		meta.lore(lore);

		meta.getPersistentDataContainer().set(key, PersistentDataType.LONG, numberOfBrokenBlocks);

		tool.setItemMeta(meta);
	}
}
