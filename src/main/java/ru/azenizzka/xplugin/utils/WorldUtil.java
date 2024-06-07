package ru.azenizzka.xplugin.utils;

import org.bukkit.World;

public class WorldUtil {
	public static boolean isNight(World world) {
		if (world.getEnvironment() == World.Environment.NETHER || world.getEnvironment() == World.Environment.THE_END)
			return false;

		long time = world.getTime();

		return time >= 12515 && time <= 23450;
	}
}
