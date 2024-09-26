package dev.akarah.provider.entity;

import dev.akarah.APIProvider;
import dev.akarah.component.AbstractComponent;
import dev.akarah.component.MutableComponent;
import dev.akarah.datatypes.server.Identifier;
import dev.akarah.datatypes.server.Location;
import dev.akarah.entities.*;
import dev.akarah.provider.entity.components.PlayerView;
import dev.akarah.provider.item.ItemImpl;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;

import java.util.Optional;
import java.util.Set;

@SuppressWarnings("unchecked")
public class EntityImpl extends Entity {
    public net.minecraft.world.entity.Entity entity;

    public EntityImpl(net.minecraft.world.entity.Entity entity) {
        this.entity = entity;
    }

    @Override
    public EntityType entityType() {
        return new EntityTypeImpl(Identifier.of(entity.getType().getDescriptionId()));
    }

    @Override
    public <T> T get(AbstractComponent<T, Entity, EntityComponent> component) {
        return switch (component) {
            case PlayerComponent playerComponent -> {
                if (entity instanceof ServerPlayer player) {
                    yield (T) new PlayerView(player);
                }
                yield null;
            }
            case HealthComponent healthComponent -> {
                if (entity instanceof LivingEntity livingEntity) {
                    yield (T) new HealthComponent(livingEntity.getHealth(), livingEntity.getMaxHealth());
                }
                yield null;
            }
            case LocationComponent locationComponent -> (T)
                new LocationComponent(
                    new DimensionImpl(APIProvider.SERVER_INSTANCE.getLevel(entity.level().dimension())),
                    new Location(
                        entity.getX(),
                        entity.getY(),
                        entity.getZ(),
                        0f,
                        0f
                    )
            );
            case EquipmentComponent equipmentComponent -> {
                if (entity instanceof LivingEntity livingEntity) {
                    yield (T)
                        new EquipmentComponent(
                            ItemImpl.fromItemStack(livingEntity.getItemBySlot(EquipmentSlot.HEAD)),
                            ItemImpl.fromItemStack(livingEntity.getItemBySlot(EquipmentSlot.CHEST)),
                            ItemImpl.fromItemStack(livingEntity.getItemBySlot(EquipmentSlot.LEGS)),
                            ItemImpl.fromItemStack(livingEntity.getItemBySlot(EquipmentSlot.FEET)),
                            ItemImpl.fromItemStack(livingEntity.getItemBySlot(EquipmentSlot.MAINHAND)),
                            ItemImpl.fromItemStack(livingEntity.getItemBySlot(EquipmentSlot.OFFHAND))
                        );
                }
                yield null;
            }
            default -> super.get(component);
        };
    }

    @Override
    public <T> Entity set(MutableComponent<T, Entity, EntityComponent> component, T value) {
        var e = super.set(component, value);
        switch (component) {
            case HealthComponent x -> {
                if (entity instanceof LivingEntity livingEntity) {
                    livingEntity.setHealth((float) ((HealthComponent) value).health());
                    if (livingEntity.getAttribute(Attributes.MAX_HEALTH) != null)
                        livingEntity.getAttribute(Attributes.MAX_HEALTH).setBaseValue(((HealthComponent) value).maxHealth());
                }
            }
            case LocationComponent x -> {
                entity.teleportTo(
                    APIProvider.SERVER_INSTANCE.getLevel(ResourceKey.create(
                        Registries.DIMENSION,
                        ResourceLocation.parse(
                            ((LocationComponent) value).dimension().name().toString()
                        ))),
                    ((LocationComponent) value).location().x(),
                    ((LocationComponent) value).location().y(),
                    ((LocationComponent) value).location().z(),
                    Set.of(),
                    ((LocationComponent) value).location().pitch(),
                    ((LocationComponent) value).location().yaw()
                );
            }
            case EquipmentComponent x -> {
                if (entity instanceof LivingEntity livingEntity) {
                    livingEntity.setItemSlot(EquipmentSlot.HEAD,
                        ItemImpl.fromItem(((EquipmentComponent) value).helmet()));
                    livingEntity.setItemSlot(EquipmentSlot.CHEST,
                        ItemImpl.fromItem(((EquipmentComponent) value).chestplate()));
                    livingEntity.setItemSlot(EquipmentSlot.LEGS,
                        ItemImpl.fromItem(((EquipmentComponent) value).leggings()));
                    livingEntity.setItemSlot(EquipmentSlot.FEET,
                        ItemImpl.fromItem(((EquipmentComponent) value).boots()));
                    livingEntity.setItemSlot(EquipmentSlot.MAINHAND,
                        ItemImpl.fromItem(((EquipmentComponent) value).mainHand()));
                    livingEntity.setItemSlot(EquipmentSlot.OFFHAND,
                        ItemImpl.fromItem(((EquipmentComponent) value).offHand()));
                }
            }
            default -> {
            }
        }
        return e;
    }
}
