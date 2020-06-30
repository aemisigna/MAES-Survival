package me.melondev.maes.bukkit.listener;

import com.google.inject.Inject;
import me.melondev.maes.bukkit.MAESBukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

/**
 * @author MelonDev
 * @since 1.0.0
 */
public final class PlayerJoinListener implements Listener {
    @Inject private MAESBukkit maesBukkit;

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onJoin(final PlayerJoinEvent event) {
        final Player player = event.getPlayer();
        player.sendMessage(ChatColor.translateAlternateColorCodes('&', maesBukkit.getConfig().getString("maes.motd").replace("%player%", player.getName())));

        event.setJoinMessage(ChatColor.translateAlternateColorCodes('&', (player.isOp() ? "&c" : "&7") + player.getName() + " &eha entrado al servidor."));
    }
}
