package dev.akarah.provider.parse;

import com.mojang.brigadier.StringReader;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.TextColor;

public class ComponentSerializer {
    StringReader stringReader;

    public ComponentSerializer(String string) {
        this.stringReader = new StringReader(string);
    }

    public MutableComponent parseTag() {
        if (stringReader.getRemainingLength() == 0)
            return Component.empty();

        var ch = stringReader.read();
        var sb = new StringBuilder();
        return switch (ch) {
            case '{' -> {
                while (stringReader.peek() != '}') {
                    sb.append(stringReader.read());
                }
                stringReader.skip();
                var component = parseTag();

                var flagSet = sb.toString().split(",");

                for (var flag : flagSet) {
                    var splitter = flag.split("=");
                    var flagName = splitter[0];


                    switch (flagName) {
                        case "color" -> {
                            var flagValue = splitter[1];
                            component.withStyle(
                                style -> style.withColor(TextColor.parseColor(flagValue).getOrThrow()));
                        }
                        case "bold" -> component.withStyle(
                            style -> style.withBold(true));
                        case "!bold" -> component.withStyle(
                            style -> style.withBold(false));
                        case "italic" -> component.withStyle(
                            style -> style.withItalic(true));
                        case "!italic" -> component.withStyle(
                            style -> style.withItalic(false));
                    }

                }
                yield component;
            }
            default -> Component.literal(String.valueOf(ch)).append(parseTag());
        };
    }
}
