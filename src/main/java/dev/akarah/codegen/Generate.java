package dev.akarah.codegen;

import dev.akarah.registry.Registries;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Collectors;

public class Generate {
    public static void generateBuiltinItems() {
        try {
            var contents = (Registries.findRegistry(Registries.ITEM).keys()
                .map(it ->
                    "    public static Identifier<Item> {uname} = Identifier.<Item>of(\"{name}\");"
                        .replace("{uname}",
                            it.toString().replace("minecraft:", "").toUpperCase())
                        .replace("{name}", it.toString().toLowerCase())
                )
                .sorted()
                .collect(Collectors.joining("\n")));
            Files.writeString(
                Path.of("../../api/src/main/java/dev/akarah/item/BuiltInItems.java"),
                "package dev.akarah.item;\n" +
                    "import dev.akarah.datatypes.server.Identifier;\n" +
                    "import dev.akarah.item.Item;\n" +
                    "import dev.akarah.dimension.Dimension;\n" +
                    "import dev.akarah.entities.EntityType;\n" +
                    "import dev.akarah.dimension.BlockType;\n" +
                    "//This code is autogenerated.\n" +
                    "//Do not modify this code.\n" +
                    "public class BuiltInItems {" +
                    contents +
                    "}"
            );
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void generateBuiltinBlocks() {
        try {
            var contents = (Registries.findRegistry(Registries.BLOCK_TYPES).keys()
                .map(it ->
                    "    public static Identifier<BlockType> {uname} = Identifier.<BlockType>of(\"{name}\");"
                        .replace("{uname}",
                            it.toString().replace("minecraft:", "").toUpperCase())
                        .replace("{name}", it.toString().toLowerCase())
                )
                .sorted()
                .collect(Collectors.joining("\n")));
            Files.writeString(
                Path.of("../../api/src/main/java/dev/akarah/dimension/block/BuiltInBlocks.java"),
                "package dev.akarah.dimension.block;\n" +
                    "import dev.akarah.datatypes.server.Identifier;\n" +
                    "import dev.akarah.item.Item;\n" +
                    "import dev.akarah.dimension.Dimension;\n" +
                    "import dev.akarah.entities.EntityType;\n" +
                    "import dev.akarah.dimension.BlockType;\n" +
                    "//This code is autogenerated.\n" +
                    "//Do not modify this code.\n" +
                    "public class BuiltInBlocks {" +
                    contents +
                    "}"
            );
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void generateBuiltinEntities() {
        try {
            var contents = (Registries.findRegistry(Registries.ENTITY_TYPES).keys()
                .map(it ->
                    "    public static Identifier<EntityType> {uname} = Identifier.<EntityType>of(\"{name}\");"
                        .replace("{uname}",
                            it.toString().replace("minecraft:", "").toUpperCase())
                        .replace("{name}", it.toString().toLowerCase())
                )
                .sorted()
                .collect(Collectors.joining("\n")));
            Files.writeString(
                Path.of("../../api/src/main/java/dev/akarah/entities/BuiltInEntities.java"),
                "package dev.akarah.dimension;\n" +
                    "import dev.akarah.datatypes.server.Identifier;\n" +
                    "import dev.akarah.item.Item;\n" +
                    "import dev.akarah.dimension.Dimension;\n" +
                    "import dev.akarah.entities.EntityType;\n" +
                    "import dev.akarah.dimension.BlockType;\n" +
                    "//This code is autogenerated.\n" +
                    "//Do not modify this code.\n" +
                    "public class BuiltInEntities {" +
                    contents +
                    "}"
            );
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
