package dev.akarah;

import dev.akarah.entities.Entity;
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
}
