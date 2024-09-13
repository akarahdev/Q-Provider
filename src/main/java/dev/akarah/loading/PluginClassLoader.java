package dev.akarah.loading;

import dev.akarah.ServerPlugin;
import net.fabricmc.loader.api.FabricLoader;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.UUID;
import java.util.jar.JarFile;

public class PluginClassLoader extends URLClassLoader {
    public UUID temporaryUuid;

    public PluginClassLoader(
        InputStream jarFileStream,
        UUID temporaryUuid
    ) throws IOException {
        super(new URL[]{
            FabricLoader.getInstance().getGameDir().resolve("./tmp_plugins/" + temporaryUuid + ".jar")
                .toUri().toURL()
        }, Thread.currentThread().getContextClassLoader());
        this.temporaryUuid = temporaryUuid;

        System.out.println("Creating new plugin class loader of " + temporaryUuid);
        Files.write(
            FabricLoader.getInstance().getGameDir().resolve("./tmp_plugins/" + temporaryUuid + ".jar"),
            jarFileStream.readAllBytes()
        );

        try (var jf = new JarFile(new File(
            FabricLoader.getInstance().getGameDir().resolve("./tmp_plugins/" + temporaryUuid + ".jar")
                .toUri()
        ))) {
            jf.entries().asIterator().forEachRemaining(entry -> {
                if (entry.getName().endsWith(".class")) {
                    try {
                        System.out.println("en: " + entry.getName());
                        this.loadClass(
                            entry.getName()
                                .replace(".class", "")
                                .replace("/", ".")
                        );
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        var clazz = super.findClass(name);
        System.out.println("name: " + name);
        if (Arrays.toString(clazz.getInterfaces()).contains("[interface dev.akarah.ServerPlugin]")) {
            System.out.println("it's a plugin!");
            try {
                var plugin = (ServerPlugin) clazz.getConstructor().newInstance();
                plugin.onInitialization();
                var config = plugin.configuration();
                PluginLoader.LOADED_PLUGINS.put(config.id, plugin);
                PluginLoader.CLASS_LOADERS.put(config.id, this);
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
                     NoSuchMethodException e) {
                throw new RuntimeException(e);
            }
        } else {
            System.out.println("it's not a plugin...");
            System.out.println(clazz.getSuperclass().getName());
            System.out.println(Arrays.toString(clazz.getInterfaces()));
        }
        return clazz;
    }

    @Override
    public void close() throws IOException {
        System.out.println("Closing plugin class loader of " + temporaryUuid);
        super.close();

        Files.delete(
            FabricLoader.getInstance().getGameDir().resolve("./tmp_plugins/" + temporaryUuid + ".jar")
        );
    }
}
