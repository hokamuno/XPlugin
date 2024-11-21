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
    if (block.getRelative(0, -1, 0).getType() != Material.FARMLAND) return;

    Collection<ItemStack> drop = block.getDrops(event.getItem());

    Material seed = PlantUtils.getSeed(block.getType());

    int counter = 0;

    for (ItemStack itemStack : drop) {
      counter++;
    }

    if (counter <= 1) return;

    block.breakNaturally(event.getItem());
    block.setType(seed);
  }
}
