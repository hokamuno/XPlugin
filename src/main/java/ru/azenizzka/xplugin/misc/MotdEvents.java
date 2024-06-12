package ru.azenizzka.xplugin.misc;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerListPingEvent;
import ru.azenizzka.xplugin.XPlugin;

import java.io.File;

public class MotdEvents implements Listener {
	private static final Component motd = Component.space()
			.append(Component.text("XMINE").color(NamedTextColor.DARK_GREEN).decorate(TextDecoration.BOLD))
			.append(Component.newline())
			.append(Component.text("    Discord: ").color(NamedTextColor.GRAY))
			.append(Component.text("https://discord.gg/MZF7988zFY").color(NamedTextColor.DARK_GRAY));

	@EventHandler
	public void onServerListPing(ServerListPingEvent event) {
		File iconPath = new File(XPlugin.instance.getDataFolder() + "/logo.png");

		event.motd(motd);

		if (!iconPath.exists())
			return;

		try {
			event.setServerIcon(Bukkit.loadServerIcon(iconPath));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
