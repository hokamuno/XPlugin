package ru.azenizzka.xplugin.vanish;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import ru.azenizzka.xplugin.XPlugin;
import ru.azenizzka.xplugin.utils.ChatUtils;
import ru.azenizzka.xplugin.utils.PlayerUtils;

import java.util.HashMap;
import java.util.Map;

public class VanishManager {
    private static final Map<Player, Location> vanishPlayers = new HashMap<>();
    private static final Component tag = ChatUtils.getTag("\uD83D\uDC42", NamedTextColor.YELLOW);

    public static boolean isPlayerVanished(Player player) {
        return vanishPlayers.containsKey(player);
    }

    private static void hidePlayer(Player player) {
        for (Player other : XPlugin.instance.getServer().getOnlinePlayers()) {
            other.hidePlayer(XPlugin.instance, player);
            ChatUtils.sendMessage(other, ChatUtils.getQuitMessage(player));
        }

        player.setInvisible(true);
        player.setGameMode(GameMode.SPECTATOR);

        ChatUtils.sendActionBar(player, "Теперь вы невидимы");
    }

    private static void showPlayer(Player player) {
        for (Player other : XPlugin.instance.getServer().getOnlinePlayers()) {
            other.showPlayer(XPlugin.instance, player);
            ChatUtils.sendMessage(other, ChatUtils.getJoinMessage(player));
        }

        player.setInvisible(false);
        player.setGameMode(GameMode.SURVIVAL);

        ChatUtils.sendMessage(player, tag, "Вы вышли из ваниша");
    }

    public static void addVanishPlayer(Player player) {
        if (isPlayerVanished(player))
            return;

        vanishPlayers.put(player, player.getLocation());
        hidePlayer(player);
    }

    public static void remVanishPlayer(Player player) {
        if (!isPlayerVanished(player))
            return;

        showPlayer(player);

        player.teleport(vanishPlayers.get(player));

        vanishPlayers.remove(player);
    }
}
