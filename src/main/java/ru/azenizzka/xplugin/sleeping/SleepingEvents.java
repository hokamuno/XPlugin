package ru.azenizzka.xplugin.sleeping;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerBedEnterEvent;
import org.bukkit.event.player.PlayerBedLeaveEvent;
import org.bukkit.scheduler.BukkitRunnable;
import ru.azenizzka.xplugin.XPlugin;
import ru.azenizzka.xplugin.utils.ChatUtils;

import java.util.HashMap;
import java.util.Map;

public class SleepingEvents implements Listener {
	private static final SleepingManager sleepingManager = XPlugin.sleepingManager;
	private final Map<Player, Integer> playersAntiSpam = new HashMap<>();
	private static final int SLEEP_DELAY = 25;

	public SleepingEvents() {
		new BukkitRunnable() {
			@Override
			public void run() {
				playersAntiSpam.entrySet().removeIf(entry -> {
					int newValue = entry.getValue() - 1;
					if (newValue <= 0) {
						return true;
					} else {
						entry.setValue(newValue);
						return false;
					}
				});
			}
		}.runTaskTimer(XPlugin.instance, 0L, 20L);
	}

	@EventHandler
	public void onPlayerWentToBed(PlayerBedEnterEvent event) {
		if (event.getBedEnterResult() != PlayerBedEnterEvent.BedEnterResult.OK)
			return;

		if (playersAntiSpam.containsKey(event.getPlayer())) {
			ChatUtils.sendErrorMessage(event.getPlayer(), "Подождите " + playersAntiSpam.get(event.getPlayer()) + " секунд");
			event.setCancelled(true);
			return;
		}

		playersAntiSpam.put(event.getPlayer(), SLEEP_DELAY);
		sleepingManager.addSleepingPlayer(event.getPlayer());
	}

	@EventHandler
	public void onPlayerWokeUp(PlayerBedLeaveEvent event) {
		sleepingManager.removeSleepingPlayer(event.getPlayer());
	}
}
