package dev.akarah.mixin;

import dev.akarah.MinecraftServer;
import dev.akarah.datatypes.Vector;
import dev.akarah.provider.entity.PlayerImpl;
import net.minecraft.core.Direction;
import net.minecraft.network.DisconnectionDetails;
import net.minecraft.network.protocol.game.ServerboundUseItemOnPacket;
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
        for (var listener : MinecraftServer.listeners().playerEventListeners()) {
            listener.event().onConnect(new PlayerImpl(this.player));
        }
    }

    @Inject(method = "handleUseItemOn",
        at = @At(value = "INVOKE",
            target = "Lnet/minecraft/server/network/ServerGamePacketListenerImpl;" +
                "send(Lnet/minecraft/network/protocol/Packet;)V",
            ordinal = 1),
        cancellable = true
    )
    public void handleUseItemOn(ServerboundUseItemOnPacket packet, CallbackInfo ci) {
        Direction direction = packet.getHitResult().getDirection();

        var oldState = player.serverLevel().getBlockState(packet.getHitResult().getBlockPos());
        var pos = packet.getHitResult().getBlockPos().relative(direction);
        var posState = player.serverLevel().getBlockState(pos);

        for (var listener : MinecraftServer.listeners().playerEventListeners()) {
            listener.event().onPlaceBlock(new PlayerImpl(this.player), new Vector(
                (double) pos.getX(),
                (double) pos.getY(),
                (double) pos.getZ()
            ));
        }

        // TODO: make event cancellable
        if (false) {
            player.serverLevel().setBlockAndUpdate(pos, oldState);
            ci.cancel();
        }
    }
}
