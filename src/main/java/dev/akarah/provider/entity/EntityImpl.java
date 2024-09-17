package dev.akarah.provider.entity;

import dev.akarah.APIProvider;
import dev.akarah.datatypes.server.Identifier;
import dev.akarah.datatypes.server.Location;
import dev.akarah.entities.Entity;
import dev.akarah.entities.EntityComponent;
import dev.akarah.entities.EntityType;
import dev.akarah.entities.types.LocationComponent;
import dev.akarah.provider.entity.components.IdentityView;
import dev.akarah.provider.entity.components.PlayerView;
import net.minecraft.server.level.ServerPlayer;

import java.util.Optional;

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
                return (Optional<T>) Optional.of(new LocationComponent(
                    new DimensionImpl(APIProvider.SERVER_INSTANCE.getLevel(entity.level().dimension())),
                    new Location(entity.getX(), entity.getY(), entity.getZ(), 0f, 0f)));
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
