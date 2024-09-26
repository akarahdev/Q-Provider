package dev.akarah.mixin.criteria;

import dev.akarah.APIProvider;
import dev.akarah.events.BuiltInEvents;
import dev.akarah.events.components.EventData;
import dev.akarah.provider.entity.EntityImpl;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.advancements.critereon.EnterBlockTrigger;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EnterBlockTrigger.class)
public class EnterBlockTriggerMixin {
    @Inject(
        method = "trigger",
        at = @At("HEAD")
    )
    public void trigger(ServerPlayer player, BlockState state, CallbackInfo ci) {
        if((Object) this == CriteriaTriggers.ENTER_BLOCK) {
            APIProvider.dispatchEvent(
                BuiltInEvents.ENTITY_ENTER_BLOCK,
                EventData.Builder.empty()
                    .mainEntity(new EntityImpl(player))
            );
        }
    }
}
