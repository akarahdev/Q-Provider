package dev.akarah.provider.entity;

import dev.akarah.datatypes.server.Identifier;
import dev.akarah.dimension.block.BlockType;

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
