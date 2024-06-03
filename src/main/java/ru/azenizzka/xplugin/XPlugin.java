package ru.azenizzka.xplugin;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import ru.azenizzka.xplugin.authentication.AuthEvents;
import ru.azenizzka.xplugin.authentication.AuthManager;
import ru.azenizzka.xplugin.authentication.commands.LoginCommand;
import ru.azenizzka.xplugin.authentication.commands.RegisterCommand;
import ru.azenizzka.xplugin.misc.ChatEvents;
import ru.azenizzka.xplugin.misc.MotdEvents;
import ru.azenizzka.xplugin.misc.TablistHandler;
import ru.azenizzka.xplugin.oreExcavation.OreEvents;
import ru.azenizzka.xplugin.security.LoggerFilterManager;
import ru.azenizzka.xplugin.treeCapitator.TreeCapitatorEvents;

import java.util.Objects;

public final class XPlugin extends JavaPlugin {
	public static XPlugin instance;
	public static AuthManager authManager;

	@Override
	public void onEnable() {
		instance = this;
		authManager = new AuthManager();

		setupLogger();
		runThreads();
		manageEvents();
		manageCommands();
	}

	private void runThreads() {
		new TablistHandler().runTaskTimer(this, 0L, 20L);
	}

	private void manageEvents() {
		Bukkit.getPluginManager().registerEvents(new MotdEvents(), this);
		Bukkit.getPluginManager().registerEvents(new OreEvents(), this);
		Bukkit.getPluginManager().registerEvents(new AuthEvents(), this);
		Bukkit.getPluginManager().registerEvents(new TreeCapitatorEvents(), this);

		Bukkit.getPluginManager().registerEvents(new ChatEvents(), this);
	}

	private void manageCommands() {
		Objects.requireNonNull(getCommand("register")).setExecutor(new RegisterCommand());
		Objects.requireNonNull(getCommand("login")).setExecutor(new LoginCommand());
	}

	private void setupLogger() {
		manageSecuredCommands();
		LoggerFilterManager.setup(getLogger());
	}

	private void manageSecuredCommands() {
		LoggerFilterManager.addSecuredCommand("/login");
		LoggerFilterManager.addSecuredCommand("/log");
		LoggerFilterManager.addSecuredCommand("/l");
		LoggerFilterManager.addSecuredCommand("/register");
		LoggerFilterManager.addSecuredCommand("/reg");
	}
}
