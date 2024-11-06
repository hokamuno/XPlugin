package ru.azenizzka.xplugin.utils;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.entity.Player;
import ru.azenizzka.xplugin.XPlugin;
import ru.azenizzka.xplugin.authentication.AuthManager;

public class PlayerUtils {
  private static final AuthManager authManager = XPlugin.authManager;

  private static final Component AUTH_ICON =
      Component.text("\uD83D\uDD12").color(NamedTextColor.BLUE);
  private static final Component SLEEPING_ICON =
      Component.text("\uD83C\uDF19").color(NamedTextColor.DARK_AQUA);
  private static final Component OVERWORLD_ICON =
      Component.text("⛏").color(NamedTextColor.DARK_GREEN);
  private static final Component NETHER_ICON =
      Component.text("\uD83D\uDD25").color(NamedTextColor.RED);
  private static final Component END_ICON = Component.text("☄").color(NamedTextColor.DARK_PURPLE);

  public static Component getDimensionEmoji(Player player) {
    if (!authManager.isLogged(player)) return AUTH_ICON;

    if (player.isSleeping()) return SLEEPING_ICON;

    switch (player.getWorld().getName()) {
      case "world":
        {
          return OVERWORLD_ICON;
        }
      case "world_nether":
        {
          return NETHER_ICON;
        }
      case "world_the_end":
        {
          return END_ICON;
        }
    }

    return AUTH_ICON;
  }
}
