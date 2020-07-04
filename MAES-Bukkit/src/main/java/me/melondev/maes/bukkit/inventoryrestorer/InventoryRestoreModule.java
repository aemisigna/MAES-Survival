package me.melondev.maes.bukkit.inventoryrestorer;

import com.google.inject.AbstractModule;
import me.melondev.maes.bukkit.inventoryrestorer.grabber.ItemGrabber;
import me.melondev.maes.bukkit.inventoryrestorer.registry.InventoryRegistry;
import me.melondev.maes.bukkit.inventoryrestorer.restorer.CurrencyWithdrawer;
import me.melondev.maes.bukkit.inventoryrestorer.restorer.InventoryManager;

/**
 * @author MelonDev
 * @since 1.0.0
 */
public final class InventoryRestoreModule extends AbstractModule {
    @Override
    public void configure() {
        bind(InventoryRegistry.class).to(InventoryManager.class);
        bind(ItemGrabber.class).to(CurrencyWithdrawer.class);
    }
}
