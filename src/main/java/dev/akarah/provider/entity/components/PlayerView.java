package dev.akarah.provider.entity.components;

import dev.akarah.entities.types.GUIComponent;
import dev.akarah.entities.types.IdentityComponent;
import dev.akarah.entities.types.InventoryComponent;
import dev.akarah.entities.types.PlayerComponent;
import dev.akarah.item.Item;
import dev.akarah.provider.item.ItemImpl;
import dev.akarah.provider.parse.FormatParser;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundSetSubtitleTextPacket;
import net.minecraft.network.protocol.game.ClientboundSetTitleTextPacket;
import net.minecraft.network.protocol.game.ClientboundSetTitlesAnimationPacket;
import net.minecraft.network.protocol.game.ClientboundSystemChatPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;

import java.util.Set;
import java.util.UUID;

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
}
