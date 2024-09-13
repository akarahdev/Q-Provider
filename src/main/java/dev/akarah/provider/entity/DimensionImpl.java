package dev.akarah.provider.entity;

import dev.akarah.datatypes.Dimension;
import dev.akarah.datatypes.Location;
import dev.akarah.datatypes.ResourceKey;
import dev.akarah.entities.Entity;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;

import java.util.stream.Stream;

public class DimensionImpl implements Dimension {
    public ServerLevel level;

    public DimensionImpl(ServerLevel level) {
        this.level = level;
    }

    @Override
    public ResourceKey<Dimension> name() {

        return ResourceKey.of(level.toString());
    }

    @Override
    public ResourceKey<?> blockAt(Location location) {
        return null;
    }

    @Override
    public void setBlockAt(Location location, ResourceKey<?> blockType) {

    }

    @Override
    public void breakBlockAt(Location location) {
        level.destroyBlock(new BlockPos(
                (int) location.x(),
                (int) location.y(),
                (int) location.z()
        ), true);
    }

    @Override
    public Stream<Entity> entities() {
        var iter = level.getAllEntities().iterator();
        return Stream.generate(() -> new EntityImpl(iter.next()));
    }

    @Override
    public Entity spawnEntity(Location location, dev.akarah.entities.EntityType entityType) {
        var mcType = EntityType.byString(entityType.resourceKey.toString()).get();
        var outEnt = mcType.spawn(level, new BlockPos(
                (int) location.x(),
                (int) location.y(),
                (int) location.z()
        ), MobSpawnType.COMMAND);
        return new EntityImpl(outEnt);
    }
}
