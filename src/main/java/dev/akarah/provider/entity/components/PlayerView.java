package dev.akarah.provider.entity.components;

import dev.akarah.entities.types.IdentityComponent;
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
    public void giveItem(Item item) {
        entity.getInventory().add(ItemImpl.fromItem(item));
    }

    @Override
    public void giveItems(Item... items) {
        for(var item : items)
            entity.getInventory().add(ItemImpl.fromItem(item));
    }

    @Override
    public void setItem(Item item, int slot) {
        entity.getInventory().setItem(slot, ItemImpl.fromItem(item));
    }

    @Override
    public boolean hasItems(Item item) {
        return entity.getInventory().hasAnyOf(Set.of(ItemImpl.fromItem(item).getItem()));
    }

    @Override
    public void removeItems(Item item) {
        entity.getInventory().removeItem(ItemImpl.fromItem(item));
    }

    @Override
    public void setSidebarName(String name) {

    }

    @Override
    public void setSidebarLine(String text, int line) {

    }
}
