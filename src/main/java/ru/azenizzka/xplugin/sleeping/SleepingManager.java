package ru.azenizzka.xplugin.sleeping;

import java.util.HashSet;
import java.util.Set;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.Statistic;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import ru.azenizzka.xplugin.XPlugin;
import ru.azenizzka.xplugin.utils.ChatUtils;
import ru.azenizzka.xplugin.utils.WorldUtil;

public class SleepingManager {
  private static final int NEEDED_PERCENT = 25;
  private static final Component tag =
      ChatUtils.getTag(
          Component.text("\uD83C\uDF19")
              .color(NamedTextColor.DARK_AQUA)
              .decorate(TextDecoration.BOLD));
  private boolean isNightSkipping;

  private final Set<Player> sleepingPLayers = new HashSet<>();
  private final World world;

  public SleepingManager() {
    isNightSkipping = false;
    world = Bukkit.getServer().getWorld("world");

    if (world == null || world.getEnvironment() != World.Environment.NORMAL)
      throw new RuntimeException("Cant find overworldworld with name \"world\"");
  }

  public void addSleepingPlayer(Player player) {
    player.setStatistic(Statistic.TIME_SINCE_REST, 0);

    if (isNightSkipping) return;

    sleepingPLayers.add(player);
    player.resetCooldown();
    Component message = tag.append(Component.text(player.getName() + " лег спать."));
    ChatUtils.sendBroadcast(message);

    if (!isNeededPlayersSleeping()) {
      message =
          tag.append(Component.text("Для пропуска ночи нужно еще "))
              .append(
                  Component.text(getNeededSleepingPlayers() - sleepingPLayers.size())
                      .color(NamedTextColor.RED)
                      .decorate(TextDecoration.BOLD));
      ChatUtils.sendBroadcast(message);
    } else {
      skipNight();
    }
  }

  public void removeSleepingPlayer(Player player) {
    sleepingPLayers.remove(player);
  }

  private int getNeededSleepingPlayers() {
    int playersOnline = world.getPlayerCount();

    return (int) Math.ceil(playersOnline * ((double) NEEDED_PERCENT / 100));
  }

  private boolean isNeededPlayersSleeping() {
    return sleepingPLayers.size() >= getNeededSleepingPlayers();
  }

  private void skipNight() {
    assert world != null;

    if (isNightSkipping) return;
    isNightSkipping = true;

    ChatUtils.sendBroadcast(tag.append(Component.text("Ночь будет пропущена!")));

    new BukkitRunnable() {
      @Override
      public void run() {
        if (!WorldUtil.isNight(world)) {
          ChatUtils.sendBroadcast(
              tag.append(Component.text("Ночь прошла, на улице снова безопасно!")));
          isNightSkipping = false;

          world.setClearWeatherDuration(24000);
          world.setWeatherDuration(0);

          cancel();
          return;
        }

        world.setTime(world.getTime() + 50);
      }
    }.runTaskTimer(XPlugin.instance, 0L, 0L);
  }
}
