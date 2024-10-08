package dev.akarah.provider.registry;

import dev.akarah.APIProvider;
import dev.akarah.datatypes.server.Identifier;
import dev.akarah.entities.EntityType;
import dev.akarah.provider.entity.EntityTypeImpl;
import dev.akarah.registry.Registry;
import dev.akarah.registry.RegistryFrozenException;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public class EntityTypeRegistry implements Registry<EntityType> {
    @Override
    public EntityType get(Identifier<EntityType> key) {
        var cl = ResourceKey.create(Registries.ENTITY_TYPE, ResourceLocation.parse(key.toString()));

        var reg = APIProvider.SERVER_INSTANCE.registryAccess().lookup(Registries.ENTITY_TYPE).get();

        if (reg.get(cl).isPresent()) {
            return new EntityTypeImpl(Identifier.of(reg.get(cl).get().key().location().toString()));
        }
        return null;
    }

    @Override
    public void register(Identifier<EntityType> resourceKey, EntityType value) throws RegistryFrozenException {
        throw new RegistryFrozenException();
    }

    @Override
    public Stream<Identifier<EntityType>> keys() {
        var reg = APIProvider.SERVER_INSTANCE.registryAccess().lookup(Registries.ENTITY_TYPE).get();
        return reg.listElementIds().map(it -> Identifier.<EntityType>of(it.location().toString()));
    }
}
