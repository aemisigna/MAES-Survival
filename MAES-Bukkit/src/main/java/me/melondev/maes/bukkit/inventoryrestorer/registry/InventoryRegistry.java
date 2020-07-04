package me.melondev.maes.bukkit.inventoryrestorer.registry;

import me.melondev.maes.bukkit.inventoryrestorer.inventory.MAESInventory;

/**
 * @author MelonDev
 * @since 1.0.0
 */
public interface InventoryRegistry {
    void restore(String name);
    void clear(String name);
    void spawnItems(String name);
    void cache(String name, MAESInventory maesInventory);
}
