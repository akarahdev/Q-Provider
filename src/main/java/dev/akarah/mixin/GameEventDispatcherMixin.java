package dev.akarah.mixin;

import net.minecraft.core.Holder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.gameevent.GameEventDispatcher;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GameEventDispatcher.class)
public class GameEventDispatcherMixin {
    @Shadow
    @Final
    private ServerLevel level;

    @Inject(
        method = "post",
        at = @At("TAIL")
    )
    public void post(Holder<GameEvent> gameEvent, Vec3 pos, GameEvent.Context context, CallbackInfo ci) {
        if (gameEvent.equals(GameEvent.BLOCK_PLACE)) {
            System.out.println("Block placed @ " + pos + " in level " + this.level);
        }
    }
}
