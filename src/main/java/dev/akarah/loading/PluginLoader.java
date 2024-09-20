package dev.akarah.loading;

import dev.akarah.MinecraftServer;
import dev.akarah.ServerPlugin;
import dev.akarah.datatypes.event.EventPair;
import dev.akarah.event.DynamicRegistryListener;
import dev.akarah.event.PlayerEventListener;
import dev.akarah.event.StaticRegistryListener;
import net.fabricmc.loader.api.FabricLoader;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class PluginLoader {
    public static HashMap<String, ServerPlugin> LOADED_PLUGINS = new HashMap<>();
    public static HashMap<String, PluginClassLoader> CLASS_LOADERS = new HashMap<>();
    public static HashMap<ServerPlugin, String> PLUGIN_FILE_NAMES = new HashMap<>();

    public static void reloadAllPlugins() {
        System.out.println("Clearing all loaded plugins...");

        for(var plugin : LOADED_PLUGINS.values()) {
            plugin.onFinalization();
        }

        LOADED_PLUGINS.clear();
        CLASS_LOADERS.clear();
        PLUGIN_FILE_NAMES.clear();

        MinecraftServer.listeners().playerEventListeners().clear();
        MinecraftServer.listeners().dynamicRegistryListeners().clear();
        MinecraftServer.listeners().staticRegistryListeners().clear();

        try (var walk = Files.walk(FabricLoader.getInstance().getGameDir().resolve("./plugins/"))) {
            walk.forEach(pluginFile -> {
                try {
                    if (pluginFile.getFileName().toString().endsWith(".jar")) {
                        System.out.println("Loading plugin file " + pluginFile.getFileName());
                        new PluginClassLoader(
                            Files.newInputStream(pluginFile),
                            UUID.nameUUIDFromBytes(pluginFile.getFileName().toString().getBytes(StandardCharsets.UTF_8))
                        );
                        System.out.println("Loaded plugin file!");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void reloadPlugin(String id) {
        var plugin = LOADED_PLUGINS.get(id);
        plugin.onFinalization();

        var removedListeners = new ArrayList<>();
        for(var listener : MinecraftServer.listeners().playerEventListeners()) {
            if(listener.pluginId().equals(id))
                removedListeners.add(listener);
        }
        for(var listener : removedListeners)
            MinecraftServer.listeners().playerEventListeners().remove((EventPair<PlayerEventListener>) listener);

        removedListeners = new ArrayList<>();
        for(var listener : MinecraftServer.listeners().dynamicRegistryListeners()) {
            if(listener.pluginId().equals(id))
                removedListeners.add(listener);
        }
        for(var listener : removedListeners)
            MinecraftServer.listeners().dynamicRegistryListeners().remove((EventPair<DynamicRegistryListener>)listener);

        removedListeners = new ArrayList<>();
        for(var listener : MinecraftServer.listeners().staticRegistryListeners()) {
            if(listener.pluginId().equals(id))
                removedListeners.add(listener);
        }
        for(var listener : removedListeners)
            MinecraftServer.listeners().staticRegistryListeners().remove((EventPair<StaticRegistryListener>) listener);

        var cl = CLASS_LOADERS.get(id);
        var jarFile = cl.getURLs()[0];
        try {
            cl.close();
            new PluginClassLoader(jarFile.openStream(), UUID.randomUUID());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
