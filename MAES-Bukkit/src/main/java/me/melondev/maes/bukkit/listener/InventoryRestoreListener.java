package me.melondev.maes.bukkit.listener;

import com.google.inject.Inject;
import me.melondev.maes.bukkit.inventoryrestorer.inventory.MAESInventory;
import me.melondev.maes.bukkit.inventoryrestorer.registry.InventoryRegistry;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.PlayerInventory;

/**
 * @author MelonDev
 * @since 1.0.0
 */
public final class InventoryRestoreListener implements Listener {
    @Inject private InventoryRegistry inventoryRegistry;

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onDeath(final PlayerDeathEvent event) {
        Player player = event.getEntity();
        PlayerInventory playerInventory = player.getInventory();
        MAESInventory maesInventory = new MAESInventory(playerInventory.getContents(), playerInventory.getArmorContents(), playerInventory.getItemInOffHand(), player.getLevel(), player.getExp(), player.getLocation());

        inventoryRegistry.cache(player.getName(), maesInventory);

        TextComponent giveUp = new TextComponent("      §c[Soltar inventario]");
        TextComponent restore = new TextComponent("        §a[Restaurar inventario]");
        giveUp.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, TextComponent.fromLegacyText("§7Tus objetos se soltarán en\ntu lugar de muerte.")));
        giveUp.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/inventory-restore giveup"));
        restore.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, TextComponent.fromLegacyText("§7Recupera tus objetos.\n§7Coste: §640 Lingotes de Oro")));
        restore.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/inventory-restore restore"));

        player.sendMessage("§cHas muerto... ¿Qué deseas hacer?\n ");
        player.spigot().sendMessage(restore, giveUp);

        event.getDrops().clear();
        event.setDroppedExp(0);
    }
}
