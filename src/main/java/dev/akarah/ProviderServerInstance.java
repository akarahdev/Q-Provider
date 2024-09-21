package dev.akarah;

import dev.akarah.datatypes.Generator;
import dev.akarah.datatypes.server.Identifier;
import dev.akarah.dimension.Dimension;
import dev.akarah.entities.Entity;
import dev.akarah.provider.GeneratorProvider;
import dev.akarah.provider.entity.EntityImpl;

import java.util.stream.Stream;

public class ProviderServerInstance implements ServerInstance {
    @Override
    public Stream<Entity> players() {
        return APIProvider.SERVER_INSTANCE.getPlayerList().getPlayers().stream().map(EntityImpl::new);
    }

    @Override
    public void runSync(Runnable runnable) {

    }

    @Override
    public Generator generator() {
        return new GeneratorProvider();
    }
}
