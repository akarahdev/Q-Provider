package dev.akarah.provider.entity;

import dev.akarah.APIProvider;
import dev.akarah.datatypes.server.Identifier;
import dev.akarah.datatypes.server.Location;
import dev.akarah.dimension.block.Block;
import dev.akarah.dimension.Dimension;
import dev.akarah.entities.Entity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.Property;

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
        var cl = ResourceKey.create(Registries.BLOCK, ResourceLocation.parse(block.blockType().internalName().toString()));

        var reg = APIProvider.SERVER_INSTANCE.registryAccess().lookup(Registries.BLOCK).get();

        var bp = new BlockPos((int) Math.floor(location.x()), (int) Math.floor(location.y()), (int) Math.floor(location.z()));
        var bl = reg.get(cl).get().value().defaultBlockState();

        this.level.setBlockAndUpdate(
            bp,
            bl
        );
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
        var mcType = EntityType.byString(entityType.resourceKey().toString()).get();
        var outEnt = mcType.spawn(level, new BlockPos(
            (int) location.x(),
            (int) location.y(),
            (int) location.z()
        ), MobSpawnType.COMMAND);

        var entity = new EntityImpl(outEnt);
        return entity;
    }
}
