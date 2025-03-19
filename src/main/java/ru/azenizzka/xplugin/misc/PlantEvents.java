package ru.azenizzka.xplugin.misc;

import java.util.Collection;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import ru.azenizzka.xplugin.utils.PlantUtils;

public class PlantEvents implements Listener {
  @EventHandler
  public void event(PlayerInteractEvent event) {
    if (!event.getAction().isRightClick()) return;

    Block block = event.getClickedBlock();

    if (block == null) return;
    if (!PlantUtils.isPlant(block.getType())) return;

    Collection<ItemStack> drop = block.getDrops(event.getItem());

    if (drop.size() <= 1) return;

    Material seedType = block.getType();

    block.breakNaturally(event.getItem());
    block.setType(seedType);
  }
}
