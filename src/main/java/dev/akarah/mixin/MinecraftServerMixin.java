package dev.akarah.mixin;

import com.mojang.datafixers.DataFixer;
import dev.akarah.APIProvider;
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
    }
}
