package ru.azenizzka.xplugin.misc;

import com.destroystokyo.paper.event.player.PlayerSetSpawnEvent;
import io.papermc.paper.chat.ChatRenderer;
import io.papermc.paper.event.player.AsyncChatEvent;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerAdvancementDoneEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.jetbrains.annotations.NotNull;
import ru.azenizzka.xplugin.utils.ChatUtils;
import ru.azenizzka.xplugin.utils.PlayerUtils;

import java.util.Objects;

public class ChatEvents implements Listener {
	ChatRenderer renderer = new ChatRenderer() {
		@Override
		public @NotNull Component render(@NotNull Player source, @NotNull Component sourceDisplayName, @NotNull Component message, @NotNull Audience viewer) {
			Component dimensionEmoji = PlayerUtils.getDimensionEmoji(source);

			return ChatUtils.getTag(dimensionEmoji)
					.append(sourceDisplayName)
					.append(Component.text(" > ")
							.color(dimensionEmoji.color())
							.decorate(TextDecoration.BOLD))
					.append(message);
		}
	};

	@EventHandler
	public void onChatMessage(AsyncChatEvent event) {
		event.renderer(renderer);
	}

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		Component message = ChatUtils.getTag(Component.text("+")
				.color(NamedTextColor.DARK_GREEN)
				.decorate(TextDecoration.BOLD))
				.append(Component.text(player.getName()));

		event.joinMessage(message);
	}

	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent event) {
		Player player = event.getPlayer();
		Component message = ChatUtils.getTag(Component.text("-")
				.color(NamedTextColor.RED)
				.decorate(TextDecoration.BOLD))
				.append(Component.text(player.getName()));

		event.quitMessage(message);
	}

	@EventHandler
	public void onGetAdvancement(PlayerAdvancementDoneEvent event) {
		Player player = event.getPlayer();
		Component displayName = event.getAdvancement().displayName().color(NamedTextColor.GOLD);

		Component message = ChatUtils.getTag(Component.text("⚡")
						.color(NamedTextColor.AQUA)
						.decorate(TextDecoration.BOLD))
				.append(Component.text(player.getName() + " выполнил достижение "))
				.append(displayName);

		event.message(message);
	}

	@EventHandler
	public void onPlayerDeath(PlayerDeathEvent event) {
		Component deathMessage = ChatUtils.getTag(Component.text("☠")
						.color(NamedTextColor.RED)
						.decorate(TextDecoration.BOLD))
				.append(Objects.requireNonNull(event.deathMessage()));

		event.deathMessage(deathMessage);
	}

	@EventHandler
	public void onPlayerSetSpawn(PlayerSetSpawnEvent event) {
		event.setNotification(ChatUtils.getMessage("Точка возрождения изменена"));
	}
}
