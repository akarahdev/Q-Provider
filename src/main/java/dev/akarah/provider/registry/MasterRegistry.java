package dev.akarah.provider.registry;

import dev.akarah.datatypes.server.Identifier;
import dev.akarah.registry.Registry;
import dev.akarah.registry.RegistryFrozenException;

import java.util.List;
import java.util.Optional;

public class MasterRegistry implements Registry<Registry<?>> {
    @Override
    public Optional<Registry<?>> lookup(Identifier<Registry<?>> key) {
        return switch (key.toString()) {
            case "minecraft:item" -> Optional.of(new ItemRegistry());
            case "minecraft:dimension" -> Optional.of(new DimensionRegistry());
            case "minecraft:block" -> Optional.of(new BlockTypeRegistry());
            case "minecraft:entity" -> Optional.of(new EntityTypeRegistry());
            case "q_mc:master" -> Optional.of(new MasterRegistry());
            default -> Optional.empty();
        };
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
