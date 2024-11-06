package ru.azenizzka.xplugin.security;

import java.util.logging.Filter;
import java.util.logging.LogRecord;

public class ConsoleFilter implements Filter {
  @Override
  public boolean isLoggable(LogRecord logRecord) {
    return LoggerFilterManager.isSecuredCommand(logRecord.getMessage());
  }
}
