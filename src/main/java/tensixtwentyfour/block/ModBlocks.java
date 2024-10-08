package tensixtwentyfour.block;

import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

import static tensixtwentyfour.TenSixTwentyFour.id;

public class ModBlocks {
    @SuppressWarnings("EmptyMethod")
    public static void init() {}
    public static final Block CLONER = register("cloner_base", new ClonerBase(), true);
    @SuppressWarnings("SameParameterValue")
    private static <T extends Block> T register(String name, T block, boolean registerItem) {
        if (registerItem) {
            Registry.register(Registries.ITEM, id(name), new BlockItem(block, new Item.Settings()));
        }
        return Registry.register(Registries.BLOCK, id( name), block);
    }
}
