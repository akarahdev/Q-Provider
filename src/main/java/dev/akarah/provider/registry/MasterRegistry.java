package dev.akarah.provider.registry;

import dev.akarah.APIProvider;
import dev.akarah.datatypes.server.Identifier;
import dev.akarah.events.EventRegistry;
import dev.akarah.registry.Registries;
import dev.akarah.registry.Registry;
import dev.akarah.registry.RegistryFrozenException;

import java.util.List;
import java.util.Optional;

public class MasterRegistry implements Registry<Registry<?>> {
    @Override
    public Optional<Registry<?>> lookup(Identifier<Registry<?>> key) {
        if(key.equals(Registries.ITEM))
            return Optional.of(new ItemRegistry());
        if(key.equals(Registries.DIMENSION))
            return Optional.of(new DimensionRegistry());
        if(key.equals(Registries.BLOCK_TYPES))
            return Optional.of(new BlockTypeRegistry());
        if(key.equals(Registries.ENTITY_TYPES))
            return Optional.of(new EntityTypeRegistry());
        if(key.equals(Registries.EVENTS))
            return Optional.of(APIProvider.EVENT_REGISTRY);
        return Optional.empty();
    }

    @Override
    public void put(Identifier<Registry<?>> resourceKey, Registry<?> value) throws RegistryFrozenException {
        throw new RegistryFrozenException();
    }

    @Override
    public List<Identifier<Registry<?>>> keys() {
        return List.of(
            Identifier.of("minecraft:item"),
            Identifier.of("minecraft:dimension"),
            Identifier.of("minecraft:block"),
            Identifier.of("q_mc:master")
        );
    }
}
