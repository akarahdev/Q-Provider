package dev.akarah.mixin;

import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Commands.class)
public abstract class CommandsMixin {
    @Shadow @Final private CommandDispatcher<CommandSourceStack> dispatcher;

    @Inject(
            method = "<init>",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/server/commands/AdvancementCommands;register(Lcom/mojang/brigadier/CommandDispatcher;)V")
    )
    private void init(Commands.CommandSelection selection, CommandBuildContext context, CallbackInfo ci) {
        // register commands here
    }
}
