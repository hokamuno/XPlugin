package ru.azenizzka.xplugin.security;

import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;
import ru.azenizzka.xplugin.utils.ClassUtils;

public class LoggerFilterManager {
  private static final Set<String> SECURED_COMMANDS = new HashSet<>();
  private static final String ISSUED_COMMAND = "issued server command";

  public static void addSecuredCommand(String command) {
    SECURED_COMMANDS.add(command);
  }

  public static boolean isSecuredCommand(String fullMessage) {
    String toLowerCase = fullMessage.toLowerCase();
    return toLowerCase.contains(ISSUED_COMMAND)
        && SECURED_COMMANDS.stream().anyMatch(toLowerCase::contains);
  }

  public static void setup(Logger logger) {
    if (ClassUtils.isExists("org.apache.logging.log4j.core.filter.AbstractFilter")) {
      Log4JFilter.setupFilter();
    } else {
      ConsoleFilter filter = new ConsoleFilter();
      logger.setFilter(filter);
      if (ClassUtils.isExists("org.bukkit.Bukkit")) {
        Logger.getLogger("Minecraft").setFilter(filter);
      }
    }
  }
}
