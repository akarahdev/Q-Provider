package dev.akarah.provider.parse;

import net.minecraft.network.chat.Component;

/**
 * Format specification:
 * <p>
 * this is a raw text
 * -> {"text":"this is a raw text"}
 * <p>
 * [color=blue]this is blue
 * -> {"color": "blue", "text": "this is blue"}
 * <p>
 * [color=blue][style=bold]blue and bold
 * -> {"color": "blue", "bold": true, "text": "blue and bold"}
 * <p>
 */
public class FormatParser {
    public static Component parse(String format) {
        var sb = new StringBuilder();
        for(int index = 0; index < format.length(); index++) {

        }
        return Component.literal(format);
    }

    public static String from(Component component) {
        return component.getString();
    }
}
