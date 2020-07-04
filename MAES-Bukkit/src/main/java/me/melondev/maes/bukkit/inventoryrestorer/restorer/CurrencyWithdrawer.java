package me.melondev.maes.bukkit.inventoryrestorer.restorer;

import me.melondev.maes.bukkit.inventoryrestorer.grabber.ItemGrabber;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

/**
 * @author MelonDev
 * @since 1.0.0
 */
public class CurrencyWithdrawer implements ItemGrabber {
    /**
     * Withdraw the {@link Material} amount from a {@link Player}
     * {@link Inventory}.
     * @param inventory a {@link Player}'s inventory.
     * @param material the {@link Material} type.
     * @param amount the amount to withdraw.
     */
    @Override
    public void withdraw(Inventory inventory, Material material, int amount) {
        inventory.remove(new ItemStack(material, amount));
    }
}
