package dev.akarah.provider.registry;

import dev.akarah.datatypes.ResourceKey;
import dev.akarah.item.Item;
import dev.akarah.registry.Registry;
import dev.akarah.registry.RegistryFrozenException;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;

import java.util.Optional;

public class ItemRegistry implements Registry<Item> {
    private ItemRegistry() {}

    @Override
    public Optional<Item> lookup(ResourceKey<Item> key) {
        var rl = ResourceLocation.parse(key.toString());
        if(BuiltInRegistries.ITEM.containsKey(rl)) {
            return Optional.of(Item.of(key, 1));
        }
        return Optional.empty();
    }

    @Override
    public void put(ResourceKey<Item> resourceKey, Item value) throws RegistryFrozenException {
        throw new RegistryFrozenException();
    }
}
