package me.melondev.maes.bukkit;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Module;

import me.melondev.maes.api.APIModule;
import me.melondev.maes.bukkit.binder.BinderModule;
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

    @Override
    public void onEnable() {
        this.setupGuice(new APIModule());
        this.registerListeners(playerJoinListener, playerQuitListener);

        this.saveDefaultConfig();
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
