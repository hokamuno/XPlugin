package ru.azenizzka.xplugin.vanish;

import com.destroystokyo.paper.event.player.PlayerAdvancementCriterionGrantEvent;
import io.papermc.paper.event.player.AsyncChatEvent;
import io.papermc.paper.event.player.PlayerPickItemEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityTargetLivingEntityEvent;
import org.bukkit.event.player.*;

public class VanishEvents implements Listener {
  @EventHandler
  public void onQuit(PlayerQuitEvent event) {
    if (VanishManager.isPlayerVanished(event.getPlayer()))
      VanishManager.remVanishPlayer(event.getPlayer());
  }

  @EventHandler
  public void onSendChat(AsyncChatEvent event) {

    if (VanishManager.isPlayerVanished(event.getPlayer())) event.setCancelled(true);
  }

  @EventHandler
  public void onTargetingToPlayer(EntityTargetLivingEntityEvent event) {
    if (event.getTarget() instanceof Player player) {
      if (VanishManager.isPlayerVanished(player)) event.setCancelled(true);
    }
  }

  @EventHandler
  public void onDropItem(PlayerDropItemEvent event) {
    if (VanishManager.isPlayerVanished(event.getPlayer())) event.setCancelled(true);
  }

  @EventHandler
  public void onPickItem(PlayerPickItemEvent event) {
    if (VanishManager.isPlayerVanished(event.getPlayer())) event.setCancelled(true);
  }

  @EventHandler
  public void onGetAdvancement(PlayerAdvancementCriterionGrantEvent event) {
    if (VanishManager.isPlayerVanished(event.getPlayer())) event.setCancelled(true);
  }
}
