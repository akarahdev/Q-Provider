package dev.akarah.mixin.criteria;

import dev.akarah.APIProvider;
import dev.akarah.events.BuiltInEvents;
import dev.akarah.events.components.EventData;
import dev.akarah.provider.entity.EntityImpl;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InventoryChangeTrigger.class)
public class InventoryChangedMixin {
    @Inject(
        method = "trigger(" +
            "Lnet/minecraft/server/level/ServerPlayer;" +
            "Lnet/minecraft/world/entity/player/Inventory;" +
            "Lnet/minecraft/world/item/ItemStack;" +
            "I" +
            "I" +
            "I" +
            ")" +
            "V",
        at = @At("HEAD")
    )
    public void trigger(ServerPlayer player, Inventory inventory, ItemStack stack, int full, int empty, int occupied,
                        CallbackInfo ci) {
        if((Object) this == CriteriaTriggers.INVENTORY_CHANGED) {
            APIProvider.dispatchEvent(
                BuiltInEvents.PLAYER_INVENTORY_CHANGED,
                EventData.Builder.empty()
                    .mainEntity(new EntityImpl(player))
            );
        }
    }
}
