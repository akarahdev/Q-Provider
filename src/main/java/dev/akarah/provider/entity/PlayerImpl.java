package dev.akarah.provider.entity;

import dev.akarah.entities.Player;
import dev.akarah.item.Item;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundSetSubtitleTextPacket;
import net.minecraft.network.protocol.game.ClientboundSetTitleTextPacket;
import net.minecraft.network.protocol.game.ClientboundSetTitlesAnimationPacket;
import net.minecraft.network.protocol.game.ClientboundSystemChatPacket;
import net.minecraft.server.level.ServerPlayer;

public class PlayerImpl extends EntityImpl implements Player {
    ServerPlayer player;

    public PlayerImpl(ServerPlayer player) {
        super(player);
        this.player = player;
    }

    @Override
    public void sendMessage(String message) {
        player.connection.send(new ClientboundSystemChatPacket(
            Component.literal(message),
            false
        ));
    }

    @Override
    public void sendActionBar(String message) {
        player.connection.send(new ClientboundSystemChatPacket(
            Component.literal(message),
            true
        ));
    }

    @Override
    public void sendTitle(String title) {
        player.connection.send(new ClientboundSetTitleTextPacket(
            Component.literal(title)
        ));
    }

    @Override
    public void sendSubtitle(String subtitle) {
        player.connection.send(new ClientboundSetSubtitleTextPacket(
            Component.literal(subtitle)
        ));
    }

    @Override
    public void sendTitleTimes(int duration, int fadeIn, int fadeOut) {
        player.connection.send(new ClientboundSetTitlesAnimationPacket(
            fadeIn,
            duration,
            fadeOut
        ));
    }

    @Override
    public void giveItem(Item item) {

    }

    @Override
    public void giveItems(Item... items) {

    }

    @Override
    public void setItem(Item item, int slot) {

    }

    @Override
    public boolean hasItems(Item item) {
        return false;
    }

    @Override
    public void removeItems(Item item) {

    }

    @Override
    public void setSidebarName(String name) {

    }

    @Override
    public void setSidebarLine(String text, int line) {

    }
}
