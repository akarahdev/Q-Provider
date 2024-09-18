package dev.akarah.provider.entity.components;

import dev.akarah.entities.IdentityComponent;
import net.minecraft.world.entity.Entity;

import java.util.UUID;

public class IdentityView implements IdentityComponent {
    Entity entity;

    public IdentityView(Entity entity) {
        this.entity = entity;
    }

    @Override
    public int temporaryId() {
        return this.entity.getId();
    }

    @Override
    public UUID uuid() {
        return this.entity.getUUID();
    }

    @Override
    public boolean isPresent(dev.akarah.entities.Entity holder) {
        return true;
    }
}
