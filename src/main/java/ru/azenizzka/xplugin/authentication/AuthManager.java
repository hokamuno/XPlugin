package ru.azenizzka.xplugin.authentication;

import com.google.gson.Gson;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import ru.azenizzka.xplugin.XPlugin;
import ru.azenizzka.xplugin.utils.ChatUtils;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class AuthManager {
	private static final String USER_DATA_DIR = "users";
	private static final String SEPARATOR = File.separator;
	private static final Set<Player> unLoggedPlayers = new HashSet<>();

	public AuthManager() {
		File userDataDir = new File(XPlugin.instance.getDataFolder(), USER_DATA_DIR);
		if (!userDataDir.exists())
			//noinspection ResultOfMethodCallIgnored
			userDataDir.mkdirs();

		new BukkitRunnable() {
			@Override
			public void run() {
				for (Player player : unLoggedPlayers) {
					if (!isRegistered(player)) {
						ChatUtils.sendTitle(player, "Вам необходимо зарегистрироваться!", "/register", 2);
					} else {
						ChatUtils.sendTitle(player, "Вам необходимо авторизоваться!", "/login", 2);
					}
				}
			}
		}.runTaskTimerAsynchronously(XPlugin.instance, 0L, 20L);
	}

	public void addUnLoggedPlayer(Player player) {
		unLoggedPlayers.add(player);
	}

	public boolean isLogged(Player player) {
		return !unLoggedPlayers.contains(player);
	}

	public boolean authUser(Player player, String password) {
		if (isRegistered(player)) {
			return !loginUser(player, password);
		} else {
			return !registerUser(player, password);
		}
	}

	private boolean isRegistered(Player player) {
		File userFile = getPlayerFile(player);
		return userFile.exists();
	}

	private boolean registerUser(Player player, String password) {
		File userFile = getPlayerFile(player);
		UserData userData = new UserData();
		userData.setPassword(password);

		try (FileWriter writer = new FileWriter(userFile)) {
			Gson gson = new Gson();
			gson.toJson(userData, writer);
			ChatUtils.sendMessage(player, "Вы успешно зарегистрировались!");
		} catch (IOException ignored) {
			return false;
		}

		return true;
	}

	private boolean loginUser(Player player, String password) {
		if (isLogged(player))
			return true;

		if (checkPassword(player, password)) {
			unLoggedPlayers.remove(player);
			ChatUtils.sendMessage(player, "Вы успешно авторизовались!");
			return true;
		}

		return false;
	}

	private boolean checkPassword(Player player, String password) {
		File userFile = getPlayerFile(player);

		if (!userFile.exists())
			return false;

		try (FileReader reader = new FileReader(userFile)) {
			Gson gson = new Gson();
			UserData userData = gson.fromJson(reader, UserData.class);
			return userData.checkPassword(password);
		} catch (IOException ignored) {
		}

		return false;
	}

	private File getPlayerFile(Player player) {
		File userDir = new File(XPlugin.instance.getDataFolder(), USER_DATA_DIR + SEPARATOR + player.getName());

		if (!userDir.exists())
			//noinspection ResultOfMethodCallIgnored
			userDir.mkdirs();

		File userIp = new File(userDir.getPath() + SEPARATOR + getPlayerIp(player));
		try {
			//noinspection ResultOfMethodCallIgnored
			userIp.createNewFile();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

		return new File(userDir.getPath() + SEPARATOR + "data.json");
	}

	private String getPlayerIp(Player player) {
		return Objects.requireNonNull(player.getAddress()).getHostName();
	}
}