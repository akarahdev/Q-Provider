package dev.akarah.mixin;

import dev.akarah.MinecraftServer;
import dev.akarah.datatypes.server.Identifier;
import dev.akarah.events.components.EventComponents;
import dev.akarah.events.components.EventData;
import dev.akarah.events.components.MainEntityComponent;
import dev.akarah.provider.entity.EntityImpl;
import dev.akarah.registry.Registries;
import net.minecraft.network.DisconnectionDetails;
import net.minecraft.network.protocol.game.*;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerGamePacketListenerImpl.class)
public class ServerGamePacketListenerImplMixin {
    @Shadow
    public ServerPlayer player;

    @Inject(method = "onDisconnect", at = @At("HEAD"))
    public void onDisconnect(DisconnectionDetails details, CallbackInfo ci) {
        Registries.findRegistry(Registries.EVENTS).get().lookup(Identifier.of("minecraft:disconnect"))
                .ifPresent(it -> {
                    for(var listener : it.eventListeners()) {
                        var ed = EventData.Builder.empty()
                                .mainEntity(new EntityImpl(this.player));
                        listener.run(ed);
                    }
                });
    }

    @Inject(method = "handleAnimate", at = @At("HEAD"))
    public void handleAnimate(ServerboundSwingPacket packet, CallbackInfo ci) {
        Registries.findRegistry(Registries.EVENTS).get().lookup(Identifier.of("minecraft:left_click"))
                .ifPresent(it -> {
                    for(var listener : it.eventListeners()) {
                        var ed = EventData.Builder.empty()
                                .mainEntity(new EntityImpl(this.player));
                        listener.run(ed);
                    }
                });
    }

    @Inject(method = "handleClientCommand", at = @At(
        value = "INVOKE",
        target = "Lnet/minecraft/server/players/PlayerList;respawn(Lnet/minecraft/server/level/ServerPlayer;ZLnet/minecraft/world/entity/Entity$RemovalReason;)Lnet/minecraft/server/level/ServerPlayer;"
    ))
    public void handleClientCommand(ServerboundClientCommandPacket packet, CallbackInfo ci) {
        Registries.findRegistry(Registries.EVENTS).get().lookup(Identifier.of("minecraft:respawn"))
                .ifPresent(it -> {
                    for(var listener : it.eventListeners()) {
                        var ed = EventData.Builder.empty()
                                .mainEntity(new EntityImpl(this.player));
                        listener.run(ed);
                    }
                });
    }

    @Inject(method = "handlePlayerCommand", at = @At("HEAD"))
    public void handlePlayerCommand(ServerboundPlayerCommandPacket packet, CallbackInfo ci) {
        switch (packet.getAction()) {
            case PRESS_SHIFT_KEY -> {
                Registries.findRegistry(Registries.EVENTS).get().lookup(Identifier.of("minecraft:sneak"))
                        .ifPresent(it -> {
                            for(var listener : it.eventListeners()) {
                                var ed = EventData.Builder.empty()
                                        .mainEntity(new EntityImpl(this.player));
                                listener.run(ed);
                            }
                        });
            }
            case RELEASE_SHIFT_KEY -> {
                Registries.findRegistry(Registries.EVENTS).get().lookup(Identifier.of("minecraft:unsneak"))
                        .ifPresent(it -> {
                            for(var listener : it.eventListeners()) {
                                var ed = EventData.Builder.empty()
                                        .mainEntity(new EntityImpl(this.player));
                                listener.run(ed);
                            }
                        });
            }
            case START_SPRINTING -> {
                Registries.findRegistry(Registries.EVENTS).get().lookup(Identifier.of("minecraft:start_sprinting"))
                        .ifPresent(it -> {
                            for(var listener : it.eventListeners()) {
                                var ed = EventData.Builder.empty()
                                        .mainEntity(new EntityImpl(this.player));
                                listener.run(ed);
                            }
                        });
            }
            case STOP_SPRINTING -> {
                Registries.findRegistry(Registries.EVENTS).get().lookup(Identifier.of("minecraft:stop_sprinting"))
                        .ifPresent(it -> {
                            for(var listener : it.eventListeners()) {
                                var ed = EventData.Builder.empty()
                                        .mainEntity(new EntityImpl(this.player));
                                listener.run(ed);
                            }
                        });
            }
        }
    }

    @Inject(
        method = "handleContainerClose",
        at = @At("HEAD")
    )
    public void handleContainerClose(ServerboundContainerClosePacket packet, CallbackInfo ci) {

    }

    @Inject(
        method = "handleContainerClick",
        at = @At("HEAD")
    )
    public void handleContainerClick(ServerboundContainerClickPacket packet, CallbackInfo ci) {
        if (packet.getContainerId() == this.player.containerMenu.containerId
            && !this.player.isSpectator()
            && !this.player.containerMenu.stillValid(this.player)
            && this.player.containerMenu.isValidSlotIndex(packet.getSlotNum())) {

        }
    }
}
