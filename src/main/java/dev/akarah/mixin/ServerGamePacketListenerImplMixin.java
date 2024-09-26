package dev.akarah.mixin;

import dev.akarah.APIProvider;
import dev.akarah.MinecraftServer;
import dev.akarah.datatypes.server.Identifier;
import dev.akarah.events.BuiltInEvents;
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

    }

    @Inject(method = "handleAnimate", at = @At("HEAD"))
    public void handleAnimate(ServerboundSwingPacket packet, CallbackInfo ci) {
        APIProvider.dispatchEvent(
                BuiltInEvents.LEFT_CLICK,
                EventData.Builder.empty()
                        .mainEntity(new EntityImpl(this.player))
        );
    }

    @Inject(method = "handleClientCommand", at = @At(
        value = "INVOKE",
        target = "Lnet/minecraft/server/players/PlayerList;respawn(Lnet/minecraft/server/level/ServerPlayer;ZLnet/minecraft/world/entity/Entity$RemovalReason;)Lnet/minecraft/server/level/ServerPlayer;"
    ))
    public void handleClientCommand(ServerboundClientCommandPacket packet, CallbackInfo ci) {
        APIProvider.dispatchEvent(
                BuiltInEvents.PLAYER_RESPAWN,
                EventData.Builder.empty()
                        .mainEntity(new EntityImpl(this.player))
        );
    }

    @Inject(method = "handlePlayerCommand", at = @At("HEAD"))
    public void handlePlayerCommand(ServerboundPlayerCommandPacket packet, CallbackInfo ci) {
        switch (packet.getAction()) {
            case PRESS_SHIFT_KEY -> {
                APIProvider.dispatchEvent(
                        BuiltInEvents.START_SNEAKING,
                        EventData.Builder.empty()
                                .mainEntity(new EntityImpl(this.player))
                );
            }
            case RELEASE_SHIFT_KEY -> {
                APIProvider.dispatchEvent(
                        BuiltInEvents.STOP_SNEAKING,
                        EventData.Builder.empty()
                                .mainEntity(new EntityImpl(this.player))
                );
            }
            case START_SPRINTING -> {
                APIProvider.dispatchEvent(
                        BuiltInEvents.START_SPRINTING,
                        EventData.Builder.empty()
                                .mainEntity(new EntityImpl(this.player))
                );
            }
            case STOP_SPRINTING -> {
                APIProvider.dispatchEvent(
                        BuiltInEvents.STOP_SPRINTING,
                        EventData.Builder.empty()
                                .mainEntity(new EntityImpl(this.player))
                );
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
