package dev.akarah;

import com.mojang.brigadier.arguments.StringArgumentType;
import dev.akarah.datatypes.server.Identifier;
import dev.akarah.events.BuiltInEvents;
import dev.akarah.events.Event;
import dev.akarah.events.EventRegistry;
import dev.akarah.events.components.EventData;
import dev.akarah.loading.PluginLoader;
import dev.akarah.provider.Scheduler;
import dev.akarah.provider.entity.EntityImpl;
import dev.akarah.provider.entity.components.PlayerView;
import dev.akarah.provider.registry.MasterRegistry;
import dev.akarah.registry.Registries;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.minecraft.commands.Commands;

import java.util.logging.Logger;

public class APIProvider implements ModInitializer {
    public static Logger LOGGER = Logger.getLogger("Provider");
    public static net.minecraft.server.MinecraftServer SERVER_INSTANCE;

    public static EventRegistry EVENT_REGISTRY = new EventRegistry();
    @Override
    public void onInitialize() {
        System.out.println("Hello world!");
        Registries.REGISTRIES = new MasterRegistry();

        MinecraftServer.setBackingInstance(new ProviderServerInstance());

        PluginLoader.reloadAllPlugins();


        ServerPlayConnectionEvents.JOIN.register((packetListener, packetSender, minecraftServer) -> {
            APIProvider.SERVER_INSTANCE = minecraftServer;

            var player = new EntityImpl(packetListener.player);
            player.unsafe().player = new PlayerView(packetListener.player);

            APIProvider.dispatchEvent(
                    BuiltInEvents.PLAYER_CONNECT_EVENT,
                    EventData.Builder.empty()
                            .mainEntity(new EntityImpl(packetListener.player))
            );
        });
    }

    public static void dispatchEvent(Identifier<Event> event, EventData eventData) {
        Registries.findRegistry(Registries.EVENTS).getOptional(event)
                .ifPresent(it -> {
                    for(var listener : it.eventListeners()) {
                        var ed = eventData;
                        listener.run(ed);
                    }
                });
    }
}
