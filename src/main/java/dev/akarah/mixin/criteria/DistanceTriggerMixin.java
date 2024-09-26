package dev.akarah.mixin.criteria;

import dev.akarah.APIProvider;
import dev.akarah.datatypes.server.Location;
import dev.akarah.events.BuiltInEvents;
import dev.akarah.events.components.EventData;
import dev.akarah.provider.entity.EntityImpl;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.advancements.critereon.ChangeDimensionTrigger;
import net.minecraft.advancements.critereon.DistanceTrigger;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(DistanceTrigger.class)
public class DistanceTriggerMixin {
    @Inject(
        method = "trigger",
        at = @At("HEAD")
    )
    public void trigger(ServerPlayer player, Vec3 position, CallbackInfo ci) {
        if((Object) this == CriteriaTriggers.FALL_FROM_HEIGHT) {
            APIProvider.dispatchEvent(
                BuiltInEvents.FALL_FROM_HEIGHT,
                EventData.Builder.empty()
                    .mainEntity(new EntityImpl(player))
                    .location(new Location(
                        position.x,
                        position.y,
                        position.z,
                        0, 0
                    ))
            );
        }
    }
}
