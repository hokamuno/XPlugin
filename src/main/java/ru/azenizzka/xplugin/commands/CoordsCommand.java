package ru.azenizzka.xplugin.commands;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import ru.azenizzka.xplugin.utils.ChatUtils;
import ru.azenizzka.xplugin.utils.LocationUtils;

public class CoordsCommand implements CommandExecutor {
  private static final Component tag = ChatUtils.getTag("\uD83D\uDE80", NamedTextColor.GOLD);

  @Override
  public boolean onCommand(
      @NotNull CommandSender sender,
      @NotNull Command command,
      @NotNull String label,
      @NotNull String[] args) {
    Player player = (Player) sender;

    if (args.length != 1) {
      ChatUtils.sendErrorMessage(player, "Необходимо указать ник игрока");
      return true;
    }

    Player receiver = Bukkit.getPlayer(args[0]);

    if (receiver == null) {
      ChatUtils.sendErrorMessage(player, "Игрок с таким ником не был найден");
      return true;
    }

    Component firstMessage =
        Component.text("Игрок ")
            .color(NamedTextColor.GRAY)
            .append(Component.text(player.getName()).color(NamedTextColor.DARK_GRAY))
            .append(Component.text(" поделился с вами своей позицией!").color(NamedTextColor.GRAY));

    Component secondMessage = Component.text("Он находится в ").color(NamedTextColor.GRAY);

    switch (player.getWorld().getName()) {
      case "world":
        {
          secondMessage =
              secondMessage.append(
                  Component.text("Верхнем мире ").color(NamedTextColor.DARK_GREEN));
          break;
        }
      case "world_nether":
        {
          secondMessage = secondMessage.append(Component.text("Аду ").color(NamedTextColor.RED));
          break;
        }
      case "world_the_end":
        {
          secondMessage =
              secondMessage.append(Component.text("Эндер мире ").color(NamedTextColor.DARK_PURPLE));
          break;
        }
    }

    secondMessage =
        secondMessage.append(Component.text("на координатах").color(NamedTextColor.GRAY));
    Component thirdMessage =
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

    if (player.getWorld() == receiver.getWorld()) {
      int distance = LocationUtils.getDistance(player.getLocation(), receiver.getLocation());

      thirdMessage =
          thirdMessage
              .append(Component.text(" | ").color(NamedTextColor.DARK_GRAY))
              .append(Component.text("Расстояние: ").color(NamedTextColor.GRAY))
              .append(Component.text(distance).color(NamedTextColor.DARK_GREEN));
    }

    ChatUtils.sendMessage(
        player,
        tag,
        Component.text("Вы поделились своей позицией с ")
            .color(NamedTextColor.GRAY)
            .append(Component.text(receiver.getName()).color(NamedTextColor.DARK_GRAY)));

    ChatUtils.sendMessage(receiver, tag, firstMessage);
    ChatUtils.sendMessage(receiver, tag, secondMessage);
    ChatUtils.sendMessage(receiver, tag, thirdMessage);

    return true;
  }
}
