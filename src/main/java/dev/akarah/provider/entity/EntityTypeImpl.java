package dev.akarah.provider.entity;

import dev.akarah.datatypes.server.Identifier;
import dev.akarah.entities.EntityType;

public class EntityTypeImpl implements EntityType {
    Identifier<?> key;

    public EntityTypeImpl(Identifier<?> key) {
        this.key = key;
    }

    @Override
    public Identifier<?> resourceKey() {
        return this.key;
    }
}
