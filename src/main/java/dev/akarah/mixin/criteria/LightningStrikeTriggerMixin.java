package dev.akarah.mixin.criteria;

import dev.akarah.APIProvider;
import dev.akarah.events.BuiltInEvents;
import dev.akarah.events.components.EventData;
import dev.akarah.provider.entity.EntityImpl;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.advancements.critereon.LightningStrikeTrigger;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LightningBolt;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(LightningStrikeTrigger.class)
public class LightningStrikeTriggerMixin {
    @Inject(
        method = "trigger",
        at = @At("HEAD")
    )
    public void trigger(ServerPlayer player, LightningBolt lightning, List<Entity> nearbyEntities, CallbackInfo ci) {
        if((Object) this == CriteriaTriggers.LIGHTNING_STRIKE) {
            APIProvider.dispatchEvent(
                BuiltInEvents.LIGHTNING_STRIKE,
                EventData.Builder.empty()
                    .mainEntity(new EntityImpl(player))
                    .victim(new EntityImpl(lightning))
            );
        }
    }
}
