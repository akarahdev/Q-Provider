package dev.akarah.provider.registry;

import dev.akarah.APIProvider;
import dev.akarah.datatypes.Identifier;
import dev.akarah.dimension.Dimension;
import dev.akarah.provider.entity.DimensionImpl;
import dev.akarah.registry.Registry;
import dev.akarah.registry.RegistryFrozenException;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;

import java.util.Optional;

public class DimensionRegistry implements Registry<Dimension> {
    @Override
    public Optional<Dimension> lookup(Identifier<Dimension> key) {
        System.out.println("ts: " + key.toString());
        var cl = ResourceKey.create(Registries.DIMENSION, ResourceLocation.parse(key.toString()));

        // guaqranteed to exist, so Optional#get is safe in this context
        var reg = APIProvider.SERVER_INSTANCE.registryAccess().lookup(Registries.DIMENSION).get();

        if (reg.get(cl).isPresent()) {
            return Optional.of(new DimensionImpl(APIProvider.SERVER_INSTANCE.getLevel(cl)));
        }
        return Optional.empty();
    }

    @Override
    public void put(Identifier<Dimension> resourceKey, Dimension value) throws RegistryFrozenException {
        throw new RegistryFrozenException();
    }
}
