package dev.akarah;

import dev.akarah.loading.PluginLoader;
import dev.akarah.provider.entity.EntityImpl;
import dev.akarah.provider.entity.components.PlayerView;
import dev.akarah.provider.registry.MasterRegistry;
import dev.akarah.registry.Registries;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.minecraft.core.registries.BuiltInRegistries;

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
            var player = new EntityImpl(packetListener.player);
            player.unsafe().player = new PlayerView(packetListener.player);

            for(var listener : MinecraftServer.listeners().playerEventListeners()) {
                listener.event().onConnect(player);
            }
        });
    }
}
