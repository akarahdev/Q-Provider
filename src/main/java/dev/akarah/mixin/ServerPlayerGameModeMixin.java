package dev.akarah.mixin;

import dev.akarah.APIProvider;
import dev.akarah.MinecraftServer;
import dev.akarah.datatypes.server.Identifier;
import dev.akarah.datatypes.server.Location;
import dev.akarah.events.BuiltInEvents;
import dev.akarah.events.components.EventData;
import dev.akarah.provider.entity.EntityImpl;
import dev.akarah.registry.Registries;
import net.minecraft.core.BlockPos;
import net.minecraft.network.protocol.game.ClientboundBlockUpdatePacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.level.ServerPlayerGameMode;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ServerPlayerGameMode.class)
public class ServerPlayerGameModeMixin {
    @Final
    @Shadow
    protected ServerPlayer player;


    @Inject(method = "destroyAndAck", at = @At("HEAD"), cancellable = true)
    public void destroyAndAck(BlockPos pos, int sequence, String message, CallbackInfo ci) {
        // player.serverLevel().getBlockState(pos) to get the block before breaking
        // and attempt to stop the server from doing it if it's cancelled :3

        // placeholding logic
        if (false) {
            this.player.connection.send(new ClientboundBlockUpdatePacket(pos, this.player.level().getBlockState(pos)));
            ci.cancel();
        }
    }

    @Inject(method = "useItemOn", at = @At("HEAD"), cancellable = true)
    public void useItemOn(ServerPlayer player, Level level, ItemStack stack, InteractionHand hand, BlockHitResult hitResult, CallbackInfoReturnable<InteractionResult> cir) {

        // placeholding logic
        if (false) {
            this.player.connection.send(new ClientboundBlockUpdatePacket(hitResult.getBlockPos(), this.player.level().getBlockState(hitResult.getBlockPos())));
            cir.setReturnValue(InteractionResult.FAIL);
            cir.cancel();
        }
    }
}
