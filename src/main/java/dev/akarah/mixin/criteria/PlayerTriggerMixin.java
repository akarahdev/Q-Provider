package dev.akarah.mixin.criteria;

import dev.akarah.APIProvider;
import dev.akarah.datatypes.server.Identifier;
import dev.akarah.events.components.EventData;
import dev.akarah.provider.entity.EntityImpl;
import net.minecraft.advancements.CriteriaTriggers;
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
        if((Object) this == CriteriaTriggers.LOCATION) {
            APIProvider.dispatchEvent(
                    Identifier.of("minecraft:location"),
                    EventData.Builder.empty()
                            .mainEntity(new EntityImpl(player))
            );
        }
        if((Object) this == CriteriaTriggers.TICK) {
            APIProvider.dispatchEvent(
                    Identifier.of("minecraft:tick"),
                    EventData.Builder.empty()
                            .mainEntity(new EntityImpl(player))
            );
        }
    }
}
