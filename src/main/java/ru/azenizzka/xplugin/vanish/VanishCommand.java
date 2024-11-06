package ru.azenizzka.xplugin.vanish;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class VanishCommand implements CommandExecutor {
  @Override
  public boolean onCommand(
      @NotNull CommandSender sender,
      @NotNull Command command,
      @NotNull String label,
      @NotNull String[] args) {
    Player player = (Player) sender;

    if (!player.isOp()) return false;

    if (VanishManager.isPlayerVanished(player)) {
      VanishManager.remVanishPlayer(player);
    } else {
      VanishManager.addVanishPlayer(player);
    }

    return true;
  }
}
