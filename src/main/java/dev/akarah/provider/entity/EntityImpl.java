package dev.akarah.provider.entity;

import dev.akarah.APIProvider;
import dev.akarah.datatypes.Identifier;
import dev.akarah.datatypes.Location;
import dev.akarah.dimension.Dimension;
import dev.akarah.entities.Entity;
import dev.akarah.entities.EntityComponent;
import dev.akarah.entities.EntityType;

import java.util.UUID;

public class EntityImpl implements Entity {
    public net.minecraft.world.entity.Entity entity;

    public EntityImpl(net.minecraft.world.entity.Entity entity) {
        this.entity = entity;
    }

    @Override
    public EntityType mobId() {
        return EntityType.of(Identifier.of(entity.getType().getDescriptionId()));
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
    public Dimension dimension() {
        return new DimensionImpl(APIProvider.SERVER_INSTANCE.getLevel(entity.level().dimension()));
    }

    @Override
    public void teleport(Location location) {
        entity.teleportTo(location.x(), location.y(), location.z());
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
