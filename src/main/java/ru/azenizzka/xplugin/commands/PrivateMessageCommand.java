package ru.azenizzka.xplugin.commands;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import ru.azenizzka.xplugin.utils.ChatUtils;

public class PrivateMessageCommand implements CommandExecutor {
	private static final Component tag = ChatUtils.getTag("✉", NamedTextColor.GOLD);
	@Override
	public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
		Player player = (Player) sender;

		if (args.length < 2) {
			ChatUtils.sendErrorMessage(player, "Укажите ник игрока и сообщение");
			return true;
		}

		Player receiver = Bukkit.getPlayer(args[0]);

		if (receiver == null) {
			ChatUtils.sendErrorMessage(player, "Такой игрок не был найден");
			return true;
		}

		StringBuilder message = new StringBuilder();

		for (int i = 1; i < args.length; i++) {
			message.append(args[i]);
			message.append(" ");
		}

		Component receiverMessage = Component.text(player.getName()).color(NamedTextColor.GRAY)
				.append(Component.text(" > ").color(NamedTextColor.GOLD).decorate(TextDecoration.BOLD))
				.append(Component.text(message.toString()).color(NamedTextColor.GRAY));

		ChatUtils.sendActionBar(player, "Вы отправили свое сообщение " + receiver.getName());

		ChatUtils.sendMessage(receiver, tag, receiverMessage);
		return true;
	}
}
