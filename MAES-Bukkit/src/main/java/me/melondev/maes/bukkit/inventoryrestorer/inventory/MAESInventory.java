package me.melondev.maes.bukkit.inventoryrestorer.inventory;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;

/**
 * @author MelonDev
 * @since 1.0.0
 */
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@Getter
public final class MAESInventory {
    private ItemStack[] inventoryItems;
    private ItemStack[] armorItems;
    private ItemStack offHandItem;
    private int level;
    private float experience;

    private Location lostLocation;

    @Override
    public String toString() {
        return "MAESInventory[inventoryItems=" + Arrays.toString(inventoryItems) + ", armorItems=" + Arrays.toString(armorItems) + ", offHandItem=" + offHandItem + ", experience=" + experience + ", level=" + level;
    }
}

