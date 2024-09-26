package dev.akarah.mixin.criteria;

import dev.akarah.APIProvider;
import dev.akarah.events.BuiltInEvents;
import dev.akarah.events.components.EventData;
import dev.akarah.provider.entity.EntityImpl;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.advancements.critereon.TargetBlockTrigger;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(TargetBlockTrigger.class)
public class TargetBlockTriggerMixin {
    @Inject(
        method = "trigger",
        at = @At("HEAD")
    )
    public void trigger(ServerPlayer player, Entity projectile, Vec3 vector, int signalStrength, CallbackInfo ci) {
        if((Object) this == CriteriaTriggers.TARGET_BLOCK_HIT) {
            APIProvider.dispatchEvent(
                BuiltInEvents.TARGET_BLOCK_HIT,
                EventData.Builder.empty()
                    .mainEntity(new EntityImpl(player))
            );
        }
    }
}
