package dev.akarah.provider.entity;

import dev.akarah.datatypes.Identifier;
import dev.akarah.dimension.BlockType;

public class BlockTypeImpl implements BlockType {
    Identifier<?> internalName;

    public BlockTypeImpl(Identifier<?> internalName) {
        this.internalName = internalName;
    }

    @Override
    public Identifier<?> internalName() {
        return this.internalName;
    }
}
