package me.melondev.maes.bukkit.inventoryrestorer.grabber;

import org.bukkit.Material;
import org.bukkit.inventory.Inventory;

/**
 * @author MelonDev
 * @since 1.0.0
 */
public interface ItemGrabber {
    void withdraw(Inventory inventory, Material material, int amount);
}
