package dev.akarah.provider.entity;

import dev.akarah.APIProvider;
import dev.akarah.datatypes.Identifier;
import dev.akarah.datatypes.Location;
import dev.akarah.dimension.Dimension;
import dev.akarah.entities.Entity;
import dev.akarah.entities.EntityComponent;
import dev.akarah.entities.EntityType;
import dev.akarah.provider.entity.components.IdentityView;
import dev.akarah.provider.entity.components.LocationView;
import dev.akarah.provider.entity.components.PlayerView;
import net.minecraft.server.level.ServerPlayer;

import java.util.Optional;
import java.util.UUID;

public class EntityImpl implements Entity {
    public net.minecraft.world.entity.Entity entity;

    public EntityImpl(net.minecraft.world.entity.Entity entity) {
        this.entity = entity;
    }

    @Override
    public EntityType entityType() {
        return new EntityTypeImpl(Identifier.of(entity.getType().getDescriptionId()));
    }

    @Override
    public <T> Optional<T> component(EntityComponent<T> component) {
        switch (component.internalName().toString()) {
             case "api:location" -> {
                return (Optional<T>) Optional.of(new LocationView(this.entity));
             }
             case "api:identity" -> {
                 return (Optional<T>) Optional.of(new IdentityView(this.entity));
             }
             case "api:player" -> {
                 if(this.entity instanceof ServerPlayer player) {
                     return (Optional<T>) Optional.of(new PlayerView(player));
                 }
             }
        }
        return Optional.empty();
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
