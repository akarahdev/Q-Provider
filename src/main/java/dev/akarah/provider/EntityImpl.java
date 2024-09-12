package dev.akarah.provider;

import dev.akarah.datatypes.Location;
import dev.akarah.datatypes.ResourceKey;
import dev.akarah.entities.Entity;
import dev.akarah.entities.EntityComponent;

import java.util.UUID;

public class EntityImpl implements Entity {
    public net.minecraft.world.entity.Entity entity;

    public EntityImpl(net.minecraft.world.entity.Entity entity) {
        this.entity = entity;
    }

    @Override
    public ResourceKey<Entity> mobId() {
        return ResourceKey.of("minecraft:zombie");
    }

    @Override
    public int temporaryId() {
        return entity.getId();
    }

    @Override
    public UUID uuid() {
        return entity.getUUID();
    }

    @Override
    public Location location() {
        return Location.of(
                entity.getX(),
                entity.getY(),
                entity.getZ()
        );
    }

    @Override
    public <T> T component(EntityComponent<T> component) {
        throw new RuntimeException("TODO");
    }

    @Override
    public <T> void component(EntityComponent<T> component, T value) {
        throw new RuntimeException("TODO");
    }

    @Override
    public <T> boolean hasComponent(EntityComponent<T> component) {
        throw new RuntimeException("TODO");
    }
}
