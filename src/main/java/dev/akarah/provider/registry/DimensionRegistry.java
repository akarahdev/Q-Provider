package dev.akarah.provider.registry;

import dev.akarah.APIProvider;
import dev.akarah.datatypes.Dimension;
import dev.akarah.datatypes.ResourceKey;
import dev.akarah.provider.entity.DimensionImpl;
import dev.akarah.registry.Registry;
import dev.akarah.registry.RegistryFrozenException;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;

import java.util.Optional;

public class DimensionRegistry implements Registry<Dimension> {
    @Override
    public Optional<Dimension> lookup(ResourceKey<Dimension> key) {
        var cl = net.minecraft.resources.ResourceKey
                .create(Registries.DIMENSION, ResourceLocation.parse(key.toString()));

        // guaqranteed to exist, so Optional#get is safe in this context
        var reg = APIProvider.SERVER_INSTANCE.registryAccess().lookup(Registries.DIMENSION).get();

        if(reg.get(cl).isPresent()) {
            return Optional.of(new DimensionImpl(APIProvider.SERVER_INSTANCE.getLevel(cl)));
        }
        return Optional.empty();
    }

    @Override
    public void put(ResourceKey<Dimension> resourceKey, Dimension value) throws RegistryFrozenException {
        throw new RegistryFrozenException();
    }
}
