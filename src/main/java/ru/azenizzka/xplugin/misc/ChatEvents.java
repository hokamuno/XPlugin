package ru.azenizzka.xplugin.misc;

import io.papermc.paper.chat.ChatRenderer;
import io.papermc.paper.event.player.AsyncChatEvent;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
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
}
