package dev.akarah.provider;

import dev.akarah.APIProvider;
import dev.akarah.datatypes.Generator;
import net.minecraft.core.registries.Registries;
import net.minecraft.server.MinecraftServer;

import java.util.stream.Stream;

public class GeneratorProvider implements Generator {
    @Override
    public Stream<String> itemIds() {
        return APIProvider.SERVER_INSTANCE.registryAccess()
            .lookup(Registries.ITEM).get()
            .listElementIds()
            .map(it -> it.location().toString());
    }

    @Override
    public Stream<String> blockIds() {
        return APIProvider.SERVER_INSTANCE.registryAccess()
            .lookup(Registries.BLOCK).get()
            .listElementIds()
            .map(it -> it.location().toString());
    }

    @Override
    public Stream<String> entityIds() {
        return APIProvider.SERVER_INSTANCE.registryAccess()
            .lookup(Registries.ENTITY_TYPE).get()
            .listElementIds()
            .map(it -> it.location().toString());
    }

    @Override
    public Stream<String> attributeIds() {
        return APIProvider.SERVER_INSTANCE.registryAccess()
            .lookup(Registries.ATTRIBUTE).get()
            .listElementIds()
            .map(it -> it.location().toString());
    }
}
