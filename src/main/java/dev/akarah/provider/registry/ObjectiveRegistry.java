package dev.akarah.provider.registry;

import dev.akarah.APIProvider;
import dev.akarah.datatypes.server.Identifier;
import dev.akarah.dimension.block.BlockType;
import dev.akarah.objective.Objective;
import dev.akarah.provider.entity.BlockTypeImpl;
import dev.akarah.provider.objective.ObjectiveImpl;
import dev.akarah.registry.Registry;
import dev.akarah.registry.RegistryFrozenException;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.numbers.NumberFormat;
import net.minecraft.network.chat.numbers.NumberFormatType;
import net.minecraft.network.chat.numbers.NumberFormatTypes;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.scores.criteria.ObjectiveCriteria;

import java.util.stream.Stream;

public class ObjectiveRegistry implements Registry<Objective> {

    @Override
    public Objective get(Identifier<Objective> key) {
        var sb = APIProvider.SERVER_INSTANCE.getScoreboard();
        if(sb.getObjective(key.toString()) == null) {
            return new ObjectiveImpl(
                    key,
                    sb.addObjective(
                            key.toString().replace(":", "__"),
                            ObjectiveCriteria.DUMMY,
                            Component.literal(key.toString()),
                            ObjectiveCriteria.RenderType.INTEGER,
                            true,
                            null
                    )
            );
        } else {
            return new ObjectiveImpl(
                    key,
                    sb.getObjective(key.toString())
            );
        }
    }

    @Override
    public void register(Identifier<Objective> resourceKey, Objective value) throws RegistryFrozenException {
        throw new RegistryFrozenException();
    }

    @Override
    public Stream<Identifier<Objective>> keys() {
        throw new RuntimeException("WIP");
    }
}
