package dev.akarah.mixin;

import dev.akarah.registry.Registries;
import dev.akarah.registry.Registry;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Enchantments.class)
public class EnchantmentsMixin {
    @Inject(
            method = "bootstrap",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/item/enchantment/Enchantments;" +
                            "register(" +
                                "Lnet/minecraft/data/worldgen/BootstrapContext;" +
                                "Lnet/minecraft/resources/ResourceKey;Lnet/minecraft/world/item/enchantment/Enchantment$Builder;)" +
                            "V",
                    ordinal = 1))
    private static void bootstrap(BootstrapContext<Enchantment> context, CallbackInfo ci) {

    }
}
