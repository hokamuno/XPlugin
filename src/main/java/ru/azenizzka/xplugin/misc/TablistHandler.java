package ru.azenizzka.xplugin.misc;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import ru.azenizzka.xplugin.utils.ChatUtils;
import ru.azenizzka.xplugin.utils.PlayerUtils;
import ru.azenizzka.xplugin.vanish.VanishManager;

import java.text.DecimalFormat;

public class TablistHandler extends BukkitRunnable {
	@Override
	public void run() {
		for (Player player : Bukkit.getOnlinePlayers()) {
			updatePlayerTablist(player);
		}
	}

	public void updatePlayerTablist(Player player) {
		Component playerListName = ChatUtils.getTag(PlayerUtils.getDimensionEmoji(player)).append(Component.text(player.getName()));

		DecimalFormat format = new DecimalFormat("#.##");
		double tps = Bukkit.getServer().getTPS()[0];
		tps = Double.parseDouble(format.format(tps));
		int onlinePlayers = Bukkit.getServer().getOnlinePlayers().size() - VanishManager.getCountOfVanishedPlayers();

		Component header = Component.empty()
				.append(Component.text("XMine")
						.color(NamedTextColor.DARK_GREEN)
						.decorate(TextDecoration.BOLD))

				.appendNewline()

				.append(Component.text("Online: ")
						.color(NamedTextColor.GRAY))
				.append(Component.text(onlinePlayers)
						.color(NamedTextColor.DARK_GREEN)
						.decorate(TextDecoration.BOLD));

		Component footer = Component.empty()
				.append(Component.text("TPS: ")
						.color(NamedTextColor.GRAY))
				.append(Component.text(tps)
						.color(NamedTextColor.DARK_GREEN)
						.decorate(TextDecoration.BOLD));


		player.sendPlayerListFooter(footer);
		player.sendPlayerListHeader(header);
		player.playerListName(playerListName);
	}
}
