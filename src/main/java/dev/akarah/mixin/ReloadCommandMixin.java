package dev.akarah.mixin;

import dev.akarah.loading.PluginLoader;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.Component;
import net.minecraft.server.commands.ReloadCommand;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Collection;

@Mixin(ReloadCommand.class)
public abstract class ReloadCommandMixin {
    @Inject(method = "reloadPacks", at = @At("HEAD"))
    private static void reloadPacks(Collection<String> selectedIds, CommandSourceStack source, CallbackInfo ci) {
        try {
            source.sendSystemMessage(Component.literal("Reloading plugins..."));
            PluginLoader.reloadAllPlugins();
            source.sendSuccess(() -> Component.literal("Reloaded all plugins!"), true);
        } catch (Exception e) {
            source.sendFailure(Component.literal("Reloading failed. Check console for errors."));
        }
    }
}
