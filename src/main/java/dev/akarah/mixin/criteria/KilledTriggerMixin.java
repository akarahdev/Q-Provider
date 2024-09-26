package dev.akarah.mixin.criteria;

import dev.akarah.APIProvider;
import dev.akarah.events.BuiltInEvents;
import dev.akarah.events.components.EventData;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.advancements.critereon.KilledTrigger;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(KilledTrigger.class)
public class KilledTriggerMixin {
    @Inject(
        method = "trigger",
        at = @At("HEAD")
    )
    public void trigger(ServerPlayer player, Entity entity, DamageSource source, CallbackInfo ci) {
        if((Object) this == CriteriaTriggers.PLAYER_KILLED_ENTITY) {
            APIProvider.dispatchEvent(
                BuiltInEvents.PLAYER_KILLED_ENTITY,
                EventData.Builder.empty()
            );
        }
        if((Object) this == CriteriaTriggers.ENTITY_KILLED_PLAYER) {
            APIProvider.dispatchEvent(
                BuiltInEvents.ENTITY_KILLED_PLAYER,
                EventData.Builder.empty()
            );
        }
    }
}
