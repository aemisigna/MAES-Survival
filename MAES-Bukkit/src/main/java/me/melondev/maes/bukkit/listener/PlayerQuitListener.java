package me.melondev.maes.bukkit.listener;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

/**
 * @author MelonDev
 * @since 1.0.0
 */
public final class PlayerQuitListener implements Listener {
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onQuit(final PlayerQuitEvent event) {
        final Player player = event.getPlayer();

        event.setQuitMessage(ChatColor.translateAlternateColorCodes('&', (player.isOp() ? "&c" : "&7") + player.getName() + " &eha entrado al servidor."));
    }
}
