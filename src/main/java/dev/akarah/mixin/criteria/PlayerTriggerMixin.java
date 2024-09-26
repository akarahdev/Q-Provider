package dev.akarah.mixin.criteria;

import net.minecraft.advancements.critereon.PlayerTrigger;
import net.minecraft.server.level.ServerPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerTrigger.class)
public class PlayerTriggerMixin {
    @Inject(
            method = "trigger",
            at = @At("HEAD")
    )
    public void trigger(ServerPlayer player, CallbackInfo ci) {

    }
}
