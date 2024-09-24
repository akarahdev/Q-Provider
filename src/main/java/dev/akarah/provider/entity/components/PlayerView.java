package dev.akarah.provider.entity.components;

import dev.akarah.datatypes.server.Identifier;
import dev.akarah.entities.Entity;
import dev.akarah.entities.GUIComponent;
import dev.akarah.entities.InventoryComponent;
import dev.akarah.entities.PlayerComponent;
import net.minecraft.server.level.ServerPlayer;

public class PlayerView implements PlayerComponent {
    ServerPlayer entity;

    public PlayerView(ServerPlayer entity) {
        this.entity = entity;
    }

    @Override
    public GUIComponent gui() {
        return new GUIView(entity);
    }

    @Override
    public InventoryComponent inventory() {
        return new PlayerInvView(entity);
    }

    @Override
    public boolean isPresent(Entity holder) {
        return holder.entityType().resourceKey().equals(Identifier.of("minecraft:player"));
    }
}
