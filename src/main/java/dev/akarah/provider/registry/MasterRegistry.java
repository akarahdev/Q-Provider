package dev.akarah.provider.registry;

import dev.akarah.datatypes.Identifier;
import dev.akarah.registry.Registry;
import dev.akarah.registry.RegistryFrozenException;

import java.util.Optional;

public class MasterRegistry implements Registry<Registry<?>> {
    @Override
    public Optional<Registry<?>> lookup(Identifier<Registry<?>> key) {
        return switch (key.toString()) {
            case "minecraft:item" -> Optional.of(new ItemRegistry());
            case "minecraft:dimension" -> Optional.of(new DimensionRegistry());
            case "api:master" -> Optional.of(new MasterRegistry());
            default -> Optional.empty();
        };
    }

    @Override
    public void put(Identifier<Registry<?>> resourceKey, Registry<?> value) throws RegistryFrozenException {
        throw new RegistryFrozenException();
    }
}
