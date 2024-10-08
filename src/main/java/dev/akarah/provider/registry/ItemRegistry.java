package dev.akarah.provider.registry;

import dev.akarah.datatypes.server.Identifier;
import dev.akarah.item.Item;
import dev.akarah.registry.Registry;
import dev.akarah.registry.RegistryFrozenException;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public class ItemRegistry implements Registry<Item> {
    ItemRegistry() {
    }

    @Override
    public Item get(Identifier<Item> key) {
        var rl = ResourceLocation.parse(key.toString());
        if (BuiltInRegistries.ITEM.containsKey(rl)) {
            return Item.of(key, 1);
        }
        return null;
    }

    @Override
    public void register(Identifier<Item> resourceKey, Item value) throws RegistryFrozenException {
        throw new RegistryFrozenException();
    }

    @Override
    public Stream<Identifier<Item>> keys() {
        return BuiltInRegistries.ITEM.registryKeySet()
            .stream()
            .map(it -> Identifier.<Item>of(it.location().toString()));
    }
}
