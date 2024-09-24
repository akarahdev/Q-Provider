package dev.akarah.mixin;

import com.mojang.datafixers.DataFixer;
import dev.akarah.APIProvider;
import dev.akarah.codegen.Generate;
import dev.akarah.provider.Scheduler;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.Services;
import net.minecraft.server.WorldStem;
import net.minecraft.server.level.progress.ChunkProgressListenerFactory;
import net.minecraft.server.packs.repository.PackRepository;
import net.minecraft.world.level.storage.LevelStorageSource;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.net.Proxy;
import java.util.function.BooleanSupplier;

@Mixin(MinecraftServer.class)
public abstract class MinecraftServerMixin {
    @Inject(method = "<init>", at = @At("TAIL"))
    private void constructor(
        Thread serverThread,
        LevelStorageSource.LevelStorageAccess storageSource,
        PackRepository packRepository,
        WorldStem worldStem,
        Proxy proxy,
        DataFixer fixerUpper,
        Services services,
        ChunkProgressListenerFactory progressListenerFactory,
        CallbackInfo ci
    ) {
        APIProvider.SERVER_INSTANCE = ((MinecraftServer) (Object) this);
        Generate.generateBuiltinItems();
        Generate.generateBuiltinDimensions();
        Generate.generateBuiltinEntities();
        Generate.generateBuiltinBlocks();

    }

    @Inject(
        method = "tickChildren",
        at = @At("HEAD")
    )
    public void tick(BooleanSupplier hasTimeLeft, CallbackInfo ci) {
        for (var task : Scheduler.ON_NEXT_TICK) {
            task.run();
        }
        Scheduler.ON_NEXT_TICK.clear();
    }
}
