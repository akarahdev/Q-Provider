package dev.akarah;

import dev.akarah.loading.PluginLoader;
import dev.akarah.provider.entity.PlayerImpl;
import dev.akarah.provider.registry.MasterRegistry;
import dev.akarah.registry.Registries;
import dev.akarah.registry.Registry;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;

import java.util.logging.Logger;

public class APIProvider implements ModInitializer {
    public static Logger LOGGER = Logger.getLogger("Provider");
    public static net.minecraft.server.MinecraftServer SERVER_INSTANCE;

    @Override
    public void onInitialize() {
        System.out.println("Hello world!");

        PluginLoader.reloadAllPlugins();
        Registries.REGISTRIES = new MasterRegistry();

        ServerPlayConnectionEvents.JOIN.register((packetListener, packetSender, minecraftServer) -> {
            var player = new PlayerImpl(packetListener.player);

            System.out.println(MinecraftServer.listeners().playerEventListeners());
        });
    }
}
