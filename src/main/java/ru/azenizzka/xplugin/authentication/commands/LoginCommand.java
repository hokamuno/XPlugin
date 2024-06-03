package ru.azenizzka.xplugin.authentication.commands;


import net.kyori.adventure.text.Component;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import ru.azenizzka.xplugin.XPlugin;
import ru.azenizzka.xplugin.authentication.AuthManager;
import ru.azenizzka.xplugin.utils.ChatUtil;

import java.awt.*;

public class LoginCommand implements CommandExecutor {
	private static final AuthManager authManager = XPlugin.authManager;

	@Override
	public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
		if (!(sender instanceof Player))
			return true;

		Player player = (Player) sender;

		if (authManager.isLogged(player)) {
			ChatUtil.errorMessage((Player) sender, "Вы уже авторизованы");
			return true;
		}

		if (args.length == 1) {
			String password = args[0];

			if (authManager.authUser(player, password)) {
				player.kick(Component.text("Неверный пароль!"));
			}
		}

		return true;
	}
}
