package ru.azenizzka.xplugin.utils;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.title.Title;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.time.Duration;

public class ChatUtils {
	private static final Component tag = getTag(Component.text("❌").color(NamedTextColor.DARK_GREEN).decorate(TextDecoration.BOLD));

	public static Component getTag(Component tagContent) {
		return Component.text("")
				.append(Component.text("[").color(NamedTextColor.DARK_GRAY))
				.append(tagContent)
				.append(Component.text("]").color(NamedTextColor.DARK_GRAY))
				.append(Component.space()).color(NamedTextColor.GRAY);
	}

	public static Component getTag(String tagText, TextColor tagColor) {
		return getTag(Component.text(tagText).color(tagColor));
	}

	public static Component getMessage(String message) {
		return tag.append(Component.text(message));
	}

	public static void sendBroadcast(Component message) {
		for (Player player : Bukkit.getOnlinePlayers())
			player.sendMessage(message);
	}

	public static void sendMessage(Player player, String message) {
		player.sendMessage(tag.append(Component.text(message)));
	}

	public static void errorMessage(Player player, String message) {
		player.sendMessage(getTag("⚠", NamedTextColor.GOLD).append(Component.text(message)));
	}

	public static void sendTitle(Player player, String title, String subtitle, int stay) {
		Title.Times DEFAULT_TIMES = Title.Times.times(Duration.ofSeconds(0), Duration.ofSeconds(stay), Duration.ofSeconds(1));

		Title titleComponent = Title.title(Component.text(title).color(NamedTextColor.GRAY), Component.text(subtitle).color(NamedTextColor.DARK_GREEN), DEFAULT_TIMES);

		player.showTitle(titleComponent);
	}
}
