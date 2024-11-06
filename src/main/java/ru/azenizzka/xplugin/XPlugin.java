package ru.azenizzka.xplugin;

import java.util.Objects;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import ru.azenizzka.xplugin.authentication.AuthEvents;
import ru.azenizzka.xplugin.authentication.AuthManager;
import ru.azenizzka.xplugin.authentication.commands.LoginCommand;
import ru.azenizzka.xplugin.authentication.commands.RegisterCommand;
import ru.azenizzka.xplugin.commands.CoordsCommand;
import ru.azenizzka.xplugin.commands.PrivateMessageCommand;
import ru.azenizzka.xplugin.misc.*;
import ru.azenizzka.xplugin.oreExcavation.OreEvents;
import ru.azenizzka.xplugin.security.LoggerFilterManager;
import ru.azenizzka.xplugin.sleeping.SleepingEvents;
import ru.azenizzka.xplugin.sleeping.SleepingManager;
import ru.azenizzka.xplugin.treeCapitator.TreeCapitatorEvents;
import ru.azenizzka.xplugin.vanish.VanishCommand;
import ru.azenizzka.xplugin.vanish.VanishEvents;

public final class XPlugin extends JavaPlugin {
  public static XPlugin instance;
  public static AuthManager authManager;
  public static SleepingManager sleepingManager;

  @Override
  public void onEnable() {
    instance = this;
    authManager = new AuthManager();
    sleepingManager = new SleepingManager();

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
    Bukkit.getPluginManager().registerEvents(new SleepingEvents(), this);
    Bukkit.getPluginManager().registerEvents(new BlockEvents(), this);
    Bukkit.getPluginManager().registerEvents(new PlayerEvents(), this);
    Bukkit.getPluginManager().registerEvents(new VanishEvents(), this);
  }

  private void manageCommands() {
    Objects.requireNonNull(getCommand("register")).setExecutor(new RegisterCommand());
    Objects.requireNonNull(getCommand("login")).setExecutor(new LoginCommand());
    Objects.requireNonNull(getCommand("coords")).setExecutor(new CoordsCommand());
    Objects.requireNonNull(getCommand("pm")).setExecutor(new PrivateMessageCommand());
    Objects.requireNonNull(getCommand("vanish")).setExecutor(new VanishCommand());
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
