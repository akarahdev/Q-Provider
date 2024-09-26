package dev.akarah.provider.registry;

import dev.akarah.APIProvider;
import dev.akarah.datatypes.server.Identifier;
import dev.akarah.dimension.Dimension;
import dev.akarah.provider.entity.DimensionImpl;
import dev.akarah.registry.Registry;
import dev.akarah.registry.RegistryFrozenException;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public class DimensionRegistry implements Registry<Dimension> {
    @Override
    public Dimension get(Identifier<Dimension> key) {
        System.out.println("ts: " + key.toString());
        var cl = ResourceKey.create(Registries.DIMENSION, ResourceLocation.parse(key.toString()));

        // guaqranteed to exist, so Optional#get is safe in this context
        var reg = APIProvider.SERVER_INSTANCE.registryAccess().lookup(Registries.DIMENSION).get();

        if (reg.get(cl).isPresent()) {
            return new DimensionImpl(APIProvider.SERVER_INSTANCE.getLevel(cl));
        }
        return null;
    }

    @Override
    public void register(Identifier<Dimension> resourceKey, Dimension value) throws RegistryFrozenException {
        throw new RegistryFrozenException();
    }

    @Override
    public Stream<Identifier<Dimension>> keys() {
        var reg = APIProvider.SERVER_INSTANCE.registryAccess().lookup(Registries.DIMENSION).get();
        return reg.listElementIds()
            .map(it -> Identifier.<Dimension>of(it.location().toString()));
    }
}
