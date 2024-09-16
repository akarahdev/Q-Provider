package dev.akarah.provider.registry;

import dev.akarah.APIProvider;
import dev.akarah.datatypes.server.Identifier;
import dev.akarah.dimension.BlockType;
import dev.akarah.provider.entity.BlockTypeImpl;
import dev.akarah.registry.Registry;
import dev.akarah.registry.RegistryFrozenException;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;

import java.util.Optional;

public class BlockTypeRegistry implements Registry<BlockType> {
    @Override
    public Optional<BlockType> lookup(Identifier<BlockType> key) {
        var cl = ResourceKey.create(Registries.BLOCK, ResourceLocation.parse(key.toString()));

        var reg = APIProvider.SERVER_INSTANCE.registryAccess().lookup(Registries.BLOCK).get();

        if (reg.get(cl).isPresent()) {
            return Optional.of(new BlockTypeImpl(Identifier.of(reg.get(cl).get().key().location().toString())));
        }
        return Optional.empty();
    }

    @Override
    public void put(Identifier<BlockType> resourceKey, BlockType value) throws RegistryFrozenException {
        throw new RegistryFrozenException();
    }
}
