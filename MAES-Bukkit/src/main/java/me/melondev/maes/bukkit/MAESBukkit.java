package me.melondev.maes.bukkit;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Module;

import me.melondev.maes.api.APIModule;
import me.melondev.maes.bukkit.binder.BinderModule;
import me.melondev.maes.bukkit.inventoryrestorer.InventoryRestoreModule;
import me.melondev.maes.bukkit.inventoryrestorer.command.RestoreInventoryCommand;
import me.melondev.maes.bukkit.listener.InventoryRestoreListener;
import me.melondev.maes.bukkit.listener.PlayerJoinListener;
import me.melondev.maes.bukkit.listener.PlayerQuitListener;

import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * @author MelonDev
 * @since 1.0.0
 */
public final class MAESBukkit extends JavaPlugin {
    @Inject private PlayerJoinListener playerJoinListener;
    @Inject private PlayerQuitListener playerQuitListener;
    @Inject private InventoryRestoreListener inventoryRestoreListener;

    @Inject private RestoreInventoryCommand restoreInventoryCommand;

    @Override
    public void onEnable() {
        this.setupGuice(new APIModule(),
                new InventoryRestoreModule()
                );
        this.registerListeners(playerJoinListener, playerQuitListener, inventoryRestoreListener);
        this.saveDefaultConfig();

        getServer().getPluginCommand("inventory-restore").setExecutor(restoreInventoryCommand);
    }

    private void setupGuice(final Module... modules) {
        final BinderModule binderModule = new BinderModule(this, modules);
        final Injector injector = binderModule.createInjector();

        injector.injectMembers(this);
    }

    private void registerListeners(final Listener... listeners) {
        for (final Listener listener : listeners) { getServer().getPluginManager().registerEvents(listener, this); }
    }

}
