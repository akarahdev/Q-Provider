package dev.akarah.provider.entity.components;

import dev.akarah.entities.types.InventoryComponent;
import dev.akarah.item.Item;
import dev.akarah.provider.item.ItemImpl;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

public class PlayerInvView implements InventoryComponent {
    ServerPlayer entity;

    public PlayerInvView(ServerPlayer entity) {
        this.entity = entity;
    }

    @Override
    public void addItem(Item... item) {
        for(var sitem : item)
            entity.getInventory().add(ItemImpl.fromItem(sitem));
    }

    @Override
    public void setItemInSlot(Item item, int slot) {
        entity.getInventory().setItem(slot, ItemImpl.fromItem(item));
    }

    @Override
    public boolean hasItems(Item... item) {
        return entity.getInventory().hasAnyOf(
            Arrays.stream(item)
                .map(ItemImpl::fromItem)
                .map(ItemStack::getItem)
                .collect(Collectors.toSet()));
    }
}
