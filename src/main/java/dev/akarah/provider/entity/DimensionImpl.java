package dev.akarah.provider.entity;

import dev.akarah.datatypes.Identifier;
import dev.akarah.datatypes.Location;
import dev.akarah.dimension.Block;
import dev.akarah.dimension.Dimension;
import dev.akarah.entities.Entity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
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
    public Identifier<Dimension> name() {
        return Identifier.of(level.dimension().location().getNamespace() + ":" + level.dimension().location().getPath());
    }

    @Override
    public Block blockAt(Location location) {
        return Block.of(this.level
            .getBlockState(new BlockPos((int) location.x(), (int) location.y(), (int) location.z()))
            .getBlock().getDescriptionId());
    }

    @Override
    public void setBlockAt(Location location, Block block) {
        throw new RuntimeException("operation is wip!");
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
