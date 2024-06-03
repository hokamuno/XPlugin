package ru.azenizzka.xplugin.misc;

import io.papermc.paper.chat.ChatRenderer;
import io.papermc.paper.event.player.AsyncChatEvent;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerAdvancementDoneEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.jetbrains.annotations.NotNull;
import ru.azenizzka.xplugin.utils.ChatUtil;
import ru.azenizzka.xplugin.utils.PlayerUtil;

public class ChatEvents implements Listener {
	ChatRenderer renderer = new ChatRenderer() {
		@Override
		public @NotNull Component render(@NotNull Player source, @NotNull Component sourceDisplayName, @NotNull Component message, @NotNull Audience viewer) {
			Component dimensionEmoji = PlayerUtil.getDimensionEmoji(source);

			return ChatUtil.getTag(dimensionEmoji)
					.append(sourceDisplayName)
					.append(Component.text(" > ").color(dimensionEmoji.color()).decorate(TextDecoration.BOLD))
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
		Component message = ChatUtil.getTag(Component.text("+").color(NamedTextColor.DARK_GREEN).decorate(TextDecoration.BOLD)).append(Component.text(player.getName()));
		event.joinMessage(message);
	}

	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent event) {
		Player player = event.getPlayer();
		Component message = ChatUtil.getTag(Component.text("-").color(NamedTextColor.RED).decorate(TextDecoration.BOLD)).append(Component.text(player.getName()));
		event.quitMessage(message);
	}

	@EventHandler
	public void onGetAdvancement(PlayerAdvancementDoneEvent event) {
		Player player = event.getPlayer();
		Component displayName = event.getAdvancement().displayName().color(NamedTextColor.GOLD);

		Component message = ChatUtil.getTag(Component.text("⚡").color(NamedTextColor.AQUA).decorate(TextDecoration.BOLD))
				.append(Component.text(player.getName() + " выполнил достижение "))
				.append(displayName);
		
		event.message(message);
	}

}
