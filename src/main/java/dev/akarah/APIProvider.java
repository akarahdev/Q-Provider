package dev.akarah;

import dev.akarah.loading.PluginLoader;
import dev.akarah.provider.PlayerImpl;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.minecraft.server.level.ServerPlayer;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.UUID;
import java.util.logging.Logger;

public class APIProvider implements ModInitializer {
    public static Logger LOGGER = Logger.getLogger("Provider");
    public static net.minecraft.server.MinecraftServer SERVER_INSTANCE;

    @Override
    public void onInitialize() {
        System.out.println("Hello world!");

        PluginLoader.reloadAllPlugins();

        ServerPlayConnectionEvents.JOIN.register((packetListener, packetSender, minecraftServer) -> {
            var player = new PlayerImpl(packetListener.player);

            System.out.println(MinecraftServer.listeners().playerEventListeners());
        });

        PlayerBlockBreakEvents.BEFORE.register((level, player, blockPos, blockState, blockEntity) -> {


            return true;
        });
    }
}
