package dev.akarah.provider.registry;

import dev.akarah.APIProvider;
import dev.akarah.datatypes.server.Identifier;
import dev.akarah.dimension.BlockType;
import dev.akarah.item.Item;
import dev.akarah.registry.Registry;
import dev.akarah.registry.RegistryFrozenException;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;

import java.util.List;
import java.util.Optional;

public class ItemRegistry implements Registry<Item> {
    ItemRegistry() {
    }

    @Override
    public Optional<Item> lookup(Identifier<Item> key) {
        var rl = ResourceLocation.parse(key.toString());
        if (BuiltInRegistries.ITEM.containsKey(rl)) {
            return Optional.of(Item.of(key, 1));
        }
        return Optional.empty();
    }

    @Override
    public void put(Identifier<Item> resourceKey, Item value) throws RegistryFrozenException {
        throw new RegistryFrozenException();
    }

    @Override
    public List<Identifier<Item>> keys() {
        return BuiltInRegistries.ITEM.registryKeySet()
                .stream()
                .map(it -> Identifier.<Item>of(it.location().toString()))
                .toList();
    }
}
