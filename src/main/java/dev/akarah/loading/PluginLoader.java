package dev.akarah.loading;

import dev.akarah.ServerPlugin;
import net.fabricmc.loader.api.FabricLoader;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class PluginLoader {
    public static HashMap<String, ServerPlugin> LOADED_PLUGINS = new HashMap<>();
    public static HashMap<String, PluginClassLoader> CLASS_LOADERS = new HashMap<>();

    public static void reloadAllPlugins() {
        System.out.println("Clearing all loaded plugins...");
        LOADED_PLUGINS.clear();
        CLASS_LOADERS.clear();

        try(var walk = Files.walk(FabricLoader.getInstance().getGameDir().resolve("./plugins/"))) {
            walk.forEach(pluginFile -> {
                try {
                    if(pluginFile.getFileName().toString().endsWith(".jar")) {
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
