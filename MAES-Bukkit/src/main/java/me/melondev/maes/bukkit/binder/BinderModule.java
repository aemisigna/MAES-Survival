package me.melondev.maes.bukkit.binder;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;
import me.melondev.maes.bukkit.MAESBukkit;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author MelonDev
 * @since 1.0.0
 */
public final class BinderModule extends AbstractModule {
    private MAESBukkit maesBukkit;
    private List<Module> moduleList;

    public BinderModule(@NotNull final MAESBukkit maesBukkit, final Module... modules) {
        moduleList = new ArrayList<>();
        moduleList.addAll(Arrays.asList(modules));

        this.maesBukkit = maesBukkit;
    }

    public Injector createInjector() {
        return Guice.createInjector(this);
    }

    @Override
    public void configure() {
        moduleList.forEach(this::install);
        bind(MAESBukkit.class).toInstance(this.maesBukkit);
    }
}
