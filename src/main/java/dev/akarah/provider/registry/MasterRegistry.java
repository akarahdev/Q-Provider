package dev.akarah.provider.registry;

import dev.akarah.APIProvider;
import dev.akarah.datatypes.server.Identifier;
import dev.akarah.events.EventRegistry;
import dev.akarah.registry.Registries;
import dev.akarah.registry.Registry;
import dev.akarah.registry.RegistryFrozenException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public class MasterRegistry implements Registry<Registry<?>> {
    @Override
    public Registry<?> get(Identifier<Registry<?>> key) {
        if(key.equals(Registries.ITEM))
            return new ItemRegistry();
        if(key.equals(Registries.DIMENSION))
            return new DimensionRegistry();
        if(key.equals(Registries.BLOCK_TYPES))
            return new BlockTypeRegistry();
        if(key.equals(Registries.ENTITY_TYPES))
            return new EntityTypeRegistry();
        if(key.equals(Registries.EVENTS))
            return APIProvider.EVENT_REGISTRY;
        return null;
    }

    @Override
    public void register(Identifier<Registry<?>> resourceKey, Registry<?> value) throws RegistryFrozenException {
        throw new RegistryFrozenException();
    }

    @Override
    public Stream<Identifier<Registry<?>>> keys() {
        return Stream.of(
            Identifier.of("minecraft:item"),
            Identifier.of("minecraft:dimension"),
            Identifier.of("minecraft:block"),
            Identifier.of("q_mc:master")
        );
    }
}
