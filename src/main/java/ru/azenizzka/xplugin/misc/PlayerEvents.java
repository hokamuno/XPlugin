package ru.azenizzka.xplugin.misc;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import ru.azenizzka.xplugin.utils.ChatUtils;

public class PlayerEvents implements Listener {
  private static final Component TAG =
      ChatUtils.getTag(Component.text("☠").color(NamedTextColor.RED).decorate(TextDecoration.BOLD));

  @EventHandler
  public void onDeathEvent(PlayerDeathEvent event) {
    Player player = event.getPlayer();

    Component worldMessage = Component.text("Место смерти ").color(NamedTextColor.GRAY);

    switch (player.getWorld().getName()) {
      case "world":
        {
          worldMessage =
              worldMessage.append(Component.text("Верхний мир").color(NamedTextColor.DARK_GREEN));
          break;
        }
      case "world_nether":
        {
          worldMessage = worldMessage.append(Component.text("Ад").color(NamedTextColor.RED));
          break;
        }
      case "world_the_end":
        {
          worldMessage =
              worldMessage.append(Component.text("Эндер мир").color(NamedTextColor.DARK_PURPLE));
          break;
        }
    }

    worldMessage =
        worldMessage.append(Component.text(" на координатах").color(NamedTextColor.GRAY));
    Component coordsMessage =
        Component.text("")
            .append(Component.text("X: "))
            .color(NamedTextColor.GRAY)
            .append(Component.text((int) player.getLocation().x()).color(NamedTextColor.DARK_GREEN))
            .append(Component.text(" | ").color(NamedTextColor.DARK_GRAY))
            .append(Component.text("Y: "))
            .color(NamedTextColor.GRAY)
            .append(Component.text((int) player.getLocation().y()).color(NamedTextColor.DARK_GREEN))
            .append(Component.text(" | ").color(NamedTextColor.DARK_GRAY))
            .append(Component.text("Z: "))
            .color(NamedTextColor.GRAY)
            .append(
                Component.text((int) player.getLocation().z()).color(NamedTextColor.DARK_GREEN));

    ChatUtils.sendMessage(player, TAG, worldMessage);
    ChatUtils.sendMessage(player, TAG, coordsMessage);
  }
}
