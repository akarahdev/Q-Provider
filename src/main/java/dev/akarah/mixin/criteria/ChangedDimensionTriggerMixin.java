package dev.akarah.mixin.criteria;

import dev.akarah.APIProvider;
import dev.akarah.events.BuiltInEvents;
import dev.akarah.events.components.EventData;
import dev.akarah.provider.entity.DimensionImpl;
import dev.akarah.provider.entity.EntityImpl;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.advancements.critereon.ChangeDimensionTrigger;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ChangeDimensionTrigger.class)
public class ChangedDimensionTriggerMixin {
    @Inject(
        method = "trigger",
        at = @At("HEAD")
    )
    public void trigger(ServerPlayer player, ResourceKey<Level> fromLevel, ResourceKey<Level> toLevel,
                        CallbackInfo ci) {
        if((Object) this == CriteriaTriggers.CHANGED_DIMENSION) {
            APIProvider.dispatchEvent(
                BuiltInEvents.PLAYER_CHANGED_DIMENSION,
                EventData.Builder.empty()
                    .mainEntity(new EntityImpl(player))
                    .fromDimension(new DimensionImpl(APIProvider.SERVER_INSTANCE.getLevel(fromLevel)))
                    .toDimension(new DimensionImpl(APIProvider.SERVER_INSTANCE.getLevel(toLevel)))
            );
        }
    }
}
