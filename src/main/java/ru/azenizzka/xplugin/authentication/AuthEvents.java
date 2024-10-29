package ru.azenizzka.xplugin.authentication;

import io.papermc.paper.event.player.AsyncChatEvent;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityTargetLivingEntityEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.*;
import org.bukkit.scheduler.BukkitRunnable;
import ru.azenizzka.xplugin.XPlugin;
import ru.azenizzka.xplugin.utils.ChatUtils;

public class AuthEvents implements Listener {
	private static final AuthManager authManager = XPlugin.authManager;

	@EventHandler
	public void onJoin(PlayerJoinEvent event) {
		System.out.println("player join epta!!");
		Player player = event.getPlayer();
		authManager.addUnLoggedPlayer(player);

		new BukkitRunnable() {
			int timeToAuth = 60;

			@Override
			public void run() {
				if (authManager.isLogged(player))
					cancel();

				if (timeToAuth-- == 0)
					player.kick(Component.text("Вы не успели авторизоваться!"));
			}
		}.runTaskTimer(XPlugin.instance, 0L, 20L);
	}

	@EventHandler
	public void onMoveItem(InventoryClickEvent event) {
		if (!(event.getInventory().getHolder() instanceof Player))
			return;

		Player player = (Player) event.getInventory().getHolder();

		if (!authManager.isLogged(player))
			event.setCancelled(true);
	}

	@EventHandler
	public void onSendCommand(PlayerCommandPreprocessEvent event) {
		if (!authManager.isLogged(event.getPlayer())) {
			String msg = event.getMessage();
			String[] args = msg.split(" ");
			Player player = event.getPlayer();
			String command = args[0];

			if (!command.equalsIgnoreCase("/reg") && !command.equalsIgnoreCase("/register") && !command.equalsIgnoreCase("/log") && !command.equalsIgnoreCase("/l") && !command.equalsIgnoreCase("/login")) {
				ChatUtils.sendErrorMessage(player, "Вы не авторизованы!");
				event.setCancelled(true);
			}
		}
	}

	@EventHandler
	public void onInteract(PlayerInteractEvent event) {
		if (!authManager.isLogged(event.getPlayer()))
			event.setCancelled(true);
	}

	@EventHandler
	public void onSendChat(AsyncChatEvent event) {
		if (!authManager.isLogged(event.getPlayer()))
			event.setCancelled(true);
	}

	@EventHandler
	public void onMove(PlayerMoveEvent event) {
		if (!authManager.isLogged(event.getPlayer()))
			event.setCancelled(true);
	}

	@EventHandler
	public void onDropItem(PlayerDropItemEvent event) {
		if (!authManager.isLogged(event.getPlayer()))
			event.setCancelled(true);
	}

	@EventHandler
	public void onGetDamage(EntityDamageEvent event) {
		if (event.getEntity() instanceof Player) {
			if (!authManager.isLogged((Player) event.getEntity()))
				event.setCancelled(true);
		}
	}

	@EventHandler
	public void onTargetingToPlayer(EntityTargetLivingEntityEvent event) {
		if (event.getTarget() instanceof Player) {
			Player player = (Player) event.getTarget();
			if (!authManager.isLogged(player))
				event.setCancelled(true);
		}
	}

	@EventHandler
	public void onHunger(FoodLevelChangeEvent event) {
		if (event.getEntity() instanceof Player) {
			Player player = (Player) event.getEntity();
			if (!authManager.isLogged(player))
				event.setCancelled(true);
		}
	}

	@EventHandler
	public void onBite(PlayerInteractAtEntityEvent event) {
		if (!authManager.isLogged(event.getPlayer()))
			event.setCancelled(true);
	}
}