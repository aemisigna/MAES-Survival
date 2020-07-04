package me.melondev.maes.bukkit.inventoryrestorer.restorer;

import com.google.common.base.Preconditions;

import com.google.inject.Inject;
import me.melondev.maes.bukkit.inventoryrestorer.inventory.MAESInventory;
import me.melondev.maes.bukkit.inventoryrestorer.registry.InventoryRegistry;
import me.melondev.maes.bukkit.inventoryrestorer.restorer.response.RestoreResponse;

import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author MelonDev
 * @since 1.0.0
 */
public class InventoryManager implements InventoryRegistry {
    @Inject private CurrencyWithdrawer currencyWithdrawer;

    private static Map<String, MAESInventory> cachedInventoryMap = new ConcurrentHashMap<>();

    private final static int RESTORE_PRICE = 40;
    private final static Material RESTORE_MATERIAL = Material.GOLD_INGOT;

    /**
     * This will withdraw the specified coin amount for
     * restoring {@link Player} old inventory.
     * @param name a non null player name
     */
    @Override
    public void restore(String name) {
        final Player player = Preconditions.checkNotNull(Bukkit.getPlayerExact(name), "[MAES-Bukkit] Error restoring inventory for name: name");
        final RestoreResponse restoreResponse = getResponse(player);
        switch (restoreResponse) {
            case NOT_CACHED: {
                player.sendMessage("§cNo hay un inventario guardado en la base de datos. (¿Ya lo habrás restaurado?)");
                player.playSound(player.getLocation(), Sound.ENTITY_ITEM_BREAK, 2.0F, 2.0F);
                break;
            }
            case NOT_ENOUGH_CURRENCY: {
                player.sendMessage("§cNo tienes suficientes §6lingotes de oro §cpara restaurar tu inventario perdido.");
                player.playSound(player.getLocation(), Sound.ENTITY_ZOMBIE_BREAK_WOODEN_DOOR, 0.5F, 0.5F);
                break;
            }
            case SUCCESS: {
                currencyWithdrawer.withdraw(player.getInventory(), RESTORE_MATERIAL, RESTORE_PRICE);
                MAESInventory maesInventory = cachedInventoryMap.get(name);
                Inventory restoredInventory = Bukkit.createInventory(null, 54, "Último inventario guardado:");
                List<ItemStack> chestContents = new ArrayList<>();
                for (ItemStack itemStack : maesInventory.getArmorItems()) {
                    if (itemStack != null) chestContents.add(itemStack);
                }
                for (ItemStack itemStack : maesInventory.getInventoryItems()) {
                    if (itemStack != null) chestContents.add(itemStack);
                }
                if (maesInventory.getOffHandItem() != null) {
                    if (maesInventory.getOffHandItem().getType() != Material.AIR) chestContents.add(maesInventory.getOffHandItem());
                }
                chestContents.forEach(restoredInventory::addItem);

                player.setLevel(maesInventory.getLevel());
                player.setExp(maesInventory.getExperience());
                player.openInventory(restoredInventory);
                player.sendMessage("§7[Inventario guardado abierto.]");
                player.sendMessage("§c§l¡NO CIERRES EL INVENTARIO HASTA TOMAR TUS OBJETOS!");
                player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 0.5F, 0.5F);

                clear(player.getName());
                break;
            }
        }
    }

    /**
     * Clear a cached inventory.
     * @param name the {@link Player} name.
     */
    @Override
    public void clear(String name) {
        cachedInventoryMap.remove(name);
        System.out.println("[MAES-Bukkit] Cleared cache for name: " + name);
    }

    /**
     * The player gave up and the items will spawn
     * @param name the {@link Player} name.
     */
    @Override
    public void spawnItems(String name) {
        Player player = Preconditions.checkNotNull(Bukkit.getPlayer(name), "Player must be not null");
        if (getResponse(player) == RestoreResponse.NOT_CACHED) {
            player.sendMessage("§cNo hay un inventario guardado en la base de datos. (Ya te habrás dado por vencido?)");
            return;
        }
        MAESInventory maesInventory = cachedInventoryMap.get(name);
        World world = Preconditions.checkNotNull(maesInventory.getLostLocation().getWorld(), "The world is null.");
        Location deathLocation = maesInventory.getLostLocation();
        for (ItemStack item : maesInventory.getInventoryItems()) {
            if (item != null) world.dropItem(deathLocation, item);
        }
        for (ItemStack item : maesInventory.getArmorItems()) {
            if (item != null) world.dropItem(deathLocation, item);
        }
        if (maesInventory.getOffHandItem() != null) {
            if (maesInventory.getOffHandItem().getType() != Material.AIR) world.dropItem(deathLocation, maesInventory.getOffHandItem());
        }
        player.sendMessage("§cTus objetos han aparecido en tu último lugar de muerte.");
        player.playSound(player.getLocation(), Sound.ENTITY_ZOMBIE_VILLAGER_HURT,  0.6F, 0.6F);
        System.out.println("[MAES-Bukkit] Dropped items for " + name);
        clear(name);
    }

    /**
     * Cache a player inventory.
     * @param name a non null player name
     * @param maesInventory a data-filled {@link MAESInventory}
     */
    @Override
    public void cache(String name, MAESInventory maesInventory) {
        cachedInventoryMap.remove(name); // remove old inventory
        cachedInventoryMap.put(name, maesInventory);
        System.out.println("[MAES-Bukkit] Cached inventory (" + name + "): " + cachedInventoryMap.get(name));
    }

    /**
     * Get withdraw response.
     * @param player the player to get inventory.
     * @return {@link RestoreResponse} a enum response.
     */
    private RestoreResponse getResponse(Player player) {
        boolean hasEmerald = player.getInventory().containsAtLeast(new ItemStack(Material.GOLD_INGOT), InventoryManager.RESTORE_PRICE);
        if (!cachedInventoryMap.containsKey(player.getName())) return RestoreResponse.NOT_CACHED;
        if (!hasEmerald) return RestoreResponse.NOT_ENOUGH_CURRENCY;
        return RestoreResponse.SUCCESS;
    }
}
