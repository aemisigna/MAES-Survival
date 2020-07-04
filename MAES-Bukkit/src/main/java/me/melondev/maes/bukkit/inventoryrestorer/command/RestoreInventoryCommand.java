package me.melondev.maes.bukkit.inventoryrestorer.command;

import com.google.common.base.Preconditions;
import com.google.inject.Inject;
import me.melondev.maes.bukkit.inventoryrestorer.registry.InventoryRegistry;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

/**
 * @author MelonDev
 * @since 1.0.0
 */
public final class RestoreInventoryCommand implements CommandExecutor {
    @Inject private InventoryRegistry inventoryRegistry;

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if (!(commandSender instanceof Player)) return true;
        if (args.length == 0) return true;
        Player player = Preconditions.checkNotNull(((Player) commandSender).getPlayer(), "CommandSender null..?");
        if (args[0].equalsIgnoreCase("giveup")) {
            inventoryRegistry.spawnItems(player.getName());
            return true;
        }
        if (args[0].equalsIgnoreCase("restore")) {
            inventoryRegistry.restore(player.getName());
            return true;
        }
        return false;
    }
}
