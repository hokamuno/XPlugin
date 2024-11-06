package ru.azenizzka.xplugin.utils;

public class ClassUtils {
  public static boolean isExists(String clazz) {
    try {
      Class.forName(clazz);
      return true;
    } catch (ClassNotFoundException e) {
      return false;
    }
  }
}
