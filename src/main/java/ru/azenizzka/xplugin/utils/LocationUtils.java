package ru.azenizzka.xplugin.utils;

import org.bukkit.Location;

public class LocationUtils {
	public static int getDistance(Location loc1, Location loc2) {
		return (int) Math.pow(Math.pow(loc1.x() - loc2.x(), 2) + Math.pow(loc1.y() - loc2.y(), 2) + Math.pow(loc1.z() - loc2.z(), 2), 0.5d);
	}
}
