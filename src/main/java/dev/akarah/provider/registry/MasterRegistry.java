package dev.akarah.provider.registry;

import dev.akarah.datatypes.ResourceKey;
import dev.akarah.registry.Registry;
import dev.akarah.registry.RegistryFrozenException;

import java.util.Optional;

public class MasterRegistry implements Registry<Registry<?>> {
    @Override
    public Optional<Registry<?>> lookup(ResourceKey<Registry<?>> key) {
        return switch (key.toString()) {
            case "minecraft:item" -> Optional.of(new ItemRegistry());
            default -> Optional.empty();
        };
    }

    @Override
    public void put(ResourceKey<Registry<?>> resourceKey, Registry<?> value) throws RegistryFrozenException {

    }
}
