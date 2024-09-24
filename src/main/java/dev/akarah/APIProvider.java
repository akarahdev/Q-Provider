package dev.akarah;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import dev.akarah.loading.PluginLoader;
import dev.akarah.provider.Scheduler;
import dev.akarah.provider.entity.EntityImpl;
import dev.akarah.provider.entity.components.PlayerView;
import dev.akarah.provider.registry.MasterRegistry;
import dev.akarah.registry.Registries;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.fabricmc.fabric.mixin.command.CommandManagerMixin;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.commands.Commands;
import net.minecraft.server.PlayerAdvancements;
import net.minecraft.server.TickTask;
import net.minecraft.world.level.gameevent.DynamicGameEventListener;
import net.minecraft.world.level.gameevent.GameEventListener;
import net.minecraft.world.level.gameevent.GameEventListenerRegistry;

import java.util.logging.Logger;

public class APIProvider implements ModInitializer {
    public static Logger LOGGER = Logger.getLogger("Provider");
    public static net.minecraft.server.MinecraftServer SERVER_INSTANCE;

    @Override
    public void onInitialize() {
        System.out.println("Hello world!");

        MinecraftServer.setBackingInstance(new ProviderServerInstance());

        PluginLoader.reloadAllPlugins();
        Registries.REGISTRIES = new MasterRegistry();

        ServerPlayConnectionEvents.JOIN.register((packetListener, packetSender, minecraftServer) -> {
            APIProvider.SERVER_INSTANCE = minecraftServer;

            var player = new EntityImpl(packetListener.player);
            player.unsafe().player = new PlayerView(packetListener.player);

            for (var listener : MinecraftServer.listeners().playerEventListeners()) {
                try {
                    listener.event().onConnect(player);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });



        CommandRegistrationCallback.EVENT.register(((dispatcher, registryAccess, environment) -> {
            dispatcher.register(
                Commands.literal("reload")
                    .then(Commands.argument("plugin_id", StringArgumentType.word())
                        .executes(ctx -> {
                            Scheduler.ON_NEXT_TICK.add(() -> {
                                try {
                                    PluginLoader.reloadPlugin(ctx.getArgument("plugin_id", String.class));
                                } catch(Exception e) {
                                    e.printStackTrace();
                                }
                            });

                            return 0;
                        }))
            );
        }));
    }
}
