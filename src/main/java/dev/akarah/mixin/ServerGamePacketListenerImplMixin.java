package dev.akarah.mixin;

import dev.akarah.MinecraftServer;
import dev.akarah.provider.entity.EntityImpl;
import net.minecraft.network.DisconnectionDetails;
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
            listener.event().onConnect(new EntityImpl(this.player));
        }
    }
}
