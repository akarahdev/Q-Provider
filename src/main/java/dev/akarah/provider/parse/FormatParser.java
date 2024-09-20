package dev.akarah.provider.parse;

import net.minecraft.network.chat.Component;

public class FormatParser {
    public static Component parse(String format) {
        return Component.literal(format);
    }
    public static String from(Component component) { return component.getString(); }
}
