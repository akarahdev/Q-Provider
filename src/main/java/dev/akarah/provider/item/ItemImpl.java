package dev.akarah.provider.item;

import dev.akarah.datatypes.nbt.NbtElement;
import dev.akarah.item.CustomDataComponent;
import dev.akarah.item.Item;
import dev.akarah.item.ItemComponent;
import dev.akarah.provider.parse.ComponentSerializer;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;

import java.util.HashMap;

public class ItemImpl {
    public static ItemStack fromItem(Item item) {
        var reg = BuiltInRegistries.ITEM.get(ResourceLocation.parse(item.getType().toString()));
        var inst = reg.getDefaultInstance().copy();

        item.get(ItemComponent.ITEM_NAME).ifPresent(itemName -> {
            inst.set(DataComponents.ITEM_NAME,
                    new ComponentSerializer(itemName).parseTag());
        });
        item.get(ItemComponent.CUSTOM_DATA).ifPresent(cd -> {
            inst.set(DataComponents.CUSTOM_DATA,
                    CustomData.of((CompoundTag) from(cd.structure())));
        });
        return inst;
    }

    public static Item fromItemStack(ItemStack itemStack) {
        var item = Item.of(itemStack.getItemHolder().getRegisteredName());
        if(itemStack.has(DataComponents.ITEM_NAME)) {
            item = item.set(
                    ItemComponent.ITEM_NAME,
                    "TODO"
            );
        }
        if(itemStack.has(DataComponents.CUSTOM_DATA)) {
            var cd = new CustomDataComponent();
            var nbt = itemStack.get(DataComponents.CUSTOM_DATA);
            for(var entry : nbt.copyTag().getAllKeys()) {
                cd.put(entry, from(nbt.copyTag().get(entry)));
            }
            item = item.set(
                    ItemComponent.CUSTOM_DATA,
                    cd
            );
        }
        return item;
    }

    public static NbtElement from(Tag tag) {
        return switch (tag) {
            case ByteTag byteTag -> new NbtElement.Byte(byteTag.getAsByte());
            case ShortTag shortTag -> new NbtElement.Short(shortTag.getAsShort());
            case IntTag intTag -> new NbtElement.Int(intTag.getAsInt());
            case LongTag longTag -> new NbtElement.Long(longTag.getAsLong());
            case FloatTag floatTag -> new NbtElement.Float(floatTag.getAsFloat());
            case DoubleTag doubleTag -> new NbtElement.Double(doubleTag.getAsDouble());
            case StringTag stringTag -> new NbtElement.StringValue(stringTag.getAsString());
            case CompoundTag compoundTag -> {
                var ne = new NbtElement.CompoundValue(new HashMap<>());
                for(var key : compoundTag.getAllKeys()) {
                    ne.value().put(key, from(compoundTag.get(key)));
                }
                yield ne;
            }
            case ListTag listTag -> {
                var ne = new NbtElement.ListValue(new NbtElement[listTag.size()]);
                int index = 0;
                for(var elem : listTag) {
                    ne.value()[index] = from(elem);
                    index++;
                }
                yield ne;
            }
            default -> new NbtElement.Int(-1);
        };
    }
    public static Tag from(NbtElement value) {
        return switch (value) {
            case NbtElement.Byte v -> ByteTag.valueOf(v.value());
            case NbtElement.Short v -> ShortTag.valueOf(v.value());
            case NbtElement.Int v -> IntTag.valueOf(v.value());
            case NbtElement.Long v -> LongTag.valueOf(v.value());
            case NbtElement.Float v -> FloatTag.valueOf(v.value());
            case NbtElement.Double v -> DoubleTag.valueOf(v.value());
            case NbtElement.StringValue v -> StringTag.valueOf(v.value());
            case NbtElement.CompoundValue v -> {
                var ct = new CompoundTag();
                for(var entry : v.value().entrySet())
                    ct.put(entry.getKey(), from(entry.getValue()));
                yield ct;
            }
            case NbtElement.ListValue v -> {
                var lt = new ListTag();
                for(var elem : v.value())
                    lt.add(from(elem));
                yield lt;
            }

        };
    }
}
