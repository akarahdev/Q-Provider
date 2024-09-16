package dev.akarah.provider.entity.components;

import dev.akarah.APIProvider;
import dev.akarah.datatypes.server.Location;
import dev.akarah.dimension.Dimension;
import dev.akarah.entities.types.LocationComponent;
import dev.akarah.provider.entity.DimensionImpl;
import net.minecraft.world.entity.Entity;

public class LocationView implements LocationComponent {
    Entity entity;

    public LocationView(Entity entity) {
        this.entity = entity;
    }

    @Override
    public Dimension dimension() {
        return new DimensionImpl(APIProvider.SERVER_INSTANCE.getLevel(entity.level().dimension()));
    }

    @Override
    public Location location() {
        return new Location(
            entity.getX(),
            entity.getY(),
            entity.getZ(),
            0,
            0
        );
    }

    @Override
    public void teleport(Location location) {
        entity.teleportTo(
            location.x(),
            location.y(),
            location.z()
        );
    }
}
