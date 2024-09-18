package dev.akarah.provider.entity.components;

import dev.akarah.entities.GUIComponent;
import dev.akarah.entities.InventoryComponent;
import dev.akarah.provider.parse.FormatParser;
import net.minecraft.network.protocol.game.ClientboundSetSubtitleTextPacket;
import net.minecraft.network.protocol.game.ClientboundSetTitleTextPacket;
import net.minecraft.network.protocol.game.ClientboundSetTitlesAnimationPacket;
import net.minecraft.network.protocol.game.ClientboundSystemChatPacket;
import net.minecraft.server.level.ServerPlayer;

public class GUIView implements GUIComponent {
    ServerPlayer entity;

    public GUIView(ServerPlayer entity) {
        this.entity = entity;
    }

    @Override
    public void sendMessage(String message) {
        entity.connection.send(new ClientboundSystemChatPacket(
            FormatParser.parse(message),
            false
        ));
    }

    @Override
    public void sendActionBar(String message) {
        entity.connection.send(new ClientboundSystemChatPacket(
            FormatParser.parse(message),
            true
        ));
    }

    @Override
    public void sendTitle(String title) {
        entity.connection.send(new ClientboundSetTitleTextPacket(
            FormatParser.parse(title)
        ));
    }

    @Override
    public void sendSubtitle(String subtitle) {
        entity.connection.send(new ClientboundSetSubtitleTextPacket(
            FormatParser.parse(subtitle)
        ));
    }

    @Override
    public void sendTitleTimes(int duration, int fadeIn, int fadeOut) {
        entity.connection.send(new ClientboundSetTitlesAnimationPacket(
            fadeIn,
            duration,
            fadeOut
        ));
    }

    @Override
    public void setSidebarName(String name) {
        throw new UnsupportedOperationException("TODO");
    }

    @Override
    public void setSidebarLine(String text, int line) {
        throw new UnsupportedOperationException("TODO");
    }

    @Override
    public InventoryComponent currentOpenMenu() {
        throw new UnsupportedOperationException("TODO");
    }
}
