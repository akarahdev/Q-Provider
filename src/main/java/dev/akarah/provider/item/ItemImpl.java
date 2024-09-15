package dev.akarah.provider.item;

import dev.akarah.APIProvider;
import dev.akarah.item.Item;
import dev.akarah.item.ItemComponent;
import dev.akarah.provider.parse.FormatParser;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

public class ItemImpl {
    public static ItemStack fromItem(Item item) {
        var reg = BuiltInRegistries.ITEM.get(ResourceLocation.parse(item.getType().toString()));
        var inst = reg.getDefaultInstance().copy();
        if(item.hasComponent(ItemComponent.ITEM_NAME)) {
            inst.set(DataComponents.ITEM_NAME,
                FormatParser.parse(item.component(ItemComponent.ITEM_NAME)));
        }
        return inst;
    }
}
