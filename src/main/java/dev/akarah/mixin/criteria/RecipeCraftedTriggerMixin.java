package dev.akarah.mixin.criteria;

import dev.akarah.APIProvider;
import dev.akarah.events.BuiltInEvents;
import dev.akarah.events.components.EventData;
import dev.akarah.provider.entity.EntityImpl;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.advancements.critereon.RecipeCraftedTrigger;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(RecipeCraftedTrigger.class)
public class RecipeCraftedTriggerMixin {
    @Inject(
        method = "trigger",
        at = @At("HEAD")
    )
    public void trigger(ServerPlayer player, ResourceLocation recipeId, List<ItemStack> items, CallbackInfo ci) {
        if((Object) this == CriteriaTriggers.CRAFTER_RECIPE_CRAFTED) {
            APIProvider.dispatchEvent(
                BuiltInEvents.CRAFTER_RECIPE_CRAFTED,
                EventData.Builder.empty()
                    .mainEntity(new EntityImpl(player))
            );
        }
    }
}
