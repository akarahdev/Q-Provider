package dev.akarah.provider.parse;

import net.minecraft.network.chat.Component;

public class FormatParser {
    public static Component parse(String format) {
        return Component.literal(format);
    }
}
