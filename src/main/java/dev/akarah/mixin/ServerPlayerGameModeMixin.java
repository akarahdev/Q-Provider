package dev.akarah.mixin;

import dev.akarah.MinecraftServer;
import dev.akarah.datatypes.Location;
import dev.akarah.provider.entity.PlayerImpl;
import net.minecraft.core.BlockPos;
import net.minecraft.network.protocol.game.ClientboundBlockUpdatePacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.level.ServerPlayerGameMode;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerPlayerGameMode.class)
public class ServerPlayerGameModeMixin {
    @Final
    @Shadow
    protected ServerPlayer player;


    @Inject(method = "destroyAndAck", at = @At("HEAD"), cancellable = true)
    public void destroyAndAck(BlockPos pos, int sequence, String message, CallbackInfo ci) {
        // player.serverLevel().getBlockState(pos) to get the block before breaking
        // and attempt to stop the server from doing it if it's cancelled :3
        for (var listener : MinecraftServer.listeners().playerEventListeners()) {
            try {
                listener.event().onBreakBlock(new PlayerImpl(this.player), new Location(
                    pos.getX(),
                    pos.getY(),
                    pos.getZ(),
                    0, 0
                ));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        // placeholding logic
        if (false) {
            this.player.connection.send(new ClientboundBlockUpdatePacket(pos, this.player.level().getBlockState(pos)));
            ci.cancel();
        }
    }
}
